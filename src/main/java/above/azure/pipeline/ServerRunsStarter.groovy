package above.azure.pipeline

import above.ConfigReader
import above.ExecuteThread
import above.Run
import above.report.EmailTools
import java.util.concurrent.Executors
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 *      Server Runs Executor for DevOps Pipelines
 *      -- (!) any changes are impacting to all our server-side testing
 *      -- Web Tool UI https://qatools.dev.clarkinc.biz/ServerRuns
 */

class ServerRunsStarter extends Run {

    final Integer maxThreads = ConfigReader.get('parallelTreads').toInteger()
    int minThreads = 5
    final String webToolUrl = ConfigReader.get('webtoolUrl')

    Long lastApiTasksCallTimeStamp = 0
    int waitForOutput = 0

    long criticalMemorySizeMb = 20
    long freeMemoryForOneClassNeededMb = 40

    final int originalAttemptsBeforeStop = 5
    int attemptsBeforeStop = originalAttemptsBeforeStop

    Date realStartDate = getRealDate()
    String weekDay = realStartDate.format('EEE')
    int startHour = realStartDate.getHours()

    List<Map> taskPool = []
    List<Map> tasks = []

    Map status = [
            version: "2.0.2", // time management
            startTimeStamp: realStartDate.getTime(),
            loopCounter: 0,
            memoryIssues: 0
    ]


    /** Entry point */
    void test() {

        setup([author:'akudin', title:'Automation Server Runs - PARALLEL'])
        log "STARTED with maxThreads = $maxThreads"
        log realStartDate

        // settings
        setLogDelay(60)
        dbUsing('qa')
        if (minThreads > maxThreads) { minThreads = maxThreads }

        // main loop
        while (true) {
            status.loopCounter++ // for stats only

            // removing tasks were done
            removeTasks(); System.gc()

            // too long pipeline execution
            //println "Pipeline running time: ${getRealTimeStamp() - status.startTimeStamp}"
            if (getRealTimeStamp() - status.startTimeStamp > 42900000) { // > 11 hours 55 minutes
                // stopping long pipeline tasks
                log '--'
                log "Stopping pipeline tasks because of it is running > 11:55", console_color_yellow
                for (task in taskPool) {
                    task.test.xTestStopRequest('ServerRunsStarter: the pipeline time is almost over so the test got stopped because of too long execution time' +
                            'https://tfs.clarkinc.biz/DefaultCollection/Automation%20Projects/_wiki/wikis/Automation-Projects.wiki/2299/Common-Requirements')
                }
                sleep(30000)
                break
            }

            // overtime night running?
            int curHour = getRealDate().getHours()
            if (startHour >= 19 && startHour < 7 && curHour >= 7 && curHour < 19) {
                log 'Stopping pipeline tasks AND the pipeline because it was started after 7 pm and it is running after 7 am'
                dbExecute("UPDATE ServerRuns_Queue SET [status] = 99 WHERE [status] = 0 " +
                        "AND date_add >= '${getRealDate().minus(1).format('yyyy-MM-dd 19:00')}'" +
                        "AND date_add < '${getRealDate().format('yyyy-MM-dd 7:00')}'")
                for (task in taskPool) {
                    task.test.xTestStopRequest('ServerRunsStarter: the test stopped with nothing wrong in the test but because we had not enough resources to run it until 7 am')
                }
            }

            // memory handling
            long freeMemory = (long)(Runtime.getRuntime().freeMemory() / 1024 / 1024)
            if (freeMemory <= criticalMemorySizeMb) {
                println "THREADS: ${taskPool.size()}"
                System.gc(); println "(!) CRITICAL - Low memory: ${freeMemory} Mb"
                criticalMemoryIssue(); System.gc()
                if (!taskPool.size()) { break } // stop pipeline
                sleep(10000); System.gc()
                continue
            }

            // requesting tasks if possible
            if (freeMemory <= (freeMemoryForOneClassNeededMb + criticalMemorySizeMb)) {

                // not enough memory for a new thread
                if (!taskPool.size()) { break } // stop pipeline
                println "THREADS: ${taskPool.size()}"
                System.gc(); println "(!) Close to low memory: ${freeMemory} Mb"; System.gc()
                sleep(10000); System.gc()
                continue

            } else {

                // enough memory
                if (taskPool.size() < maxThreads && (getRealTimeStamp() - lastApiTasksCallTimeStamp) > 15000) {

                    // have time for new tasks?
                    if (getRealTimeStamp() - status.startTimeStamp < 2.52e+7) { // < 7 hours
                        if (taskPool.size() > 0) {
                            tasks = tryGetTasks(1) // getting 1 task at a time to avoid memory issues
                        } else {
                            tasks = tryGetTasks(minThreads) // getting the first bunch of tasks
                        }
                        println "THREADS: ${taskPool.size()} - New tasks: ${tasks.size()}"
                    } else {
                        println "THREADS: ${taskPool.size()} - Pipeline is running > 7 hours. Not starting new tasks"
                    }
                    println "Free memory: ${Math.round(Runtime.getRuntime().freeMemory() / 1024 / 1024)} Mb"

                    if (!tasks && taskPool.size() == 0) {
                        attemptsBeforeStop--
                        if (attemptsBeforeStop > 0) {
                            println '----------------------------------------'
                            println 'NO NEW TASKS'
                            println "attemptsBeforeStop = $attemptsBeforeStop"
                            sleep(15000)
                            continue
                        }
                        break // stop pipeline
                    }
                    attemptsBeforeStop = originalAttemptsBeforeStop
                    lastApiTasksCallTimeStamp = getRealTimeStamp()
                } else {
                    sleep(1000)
                    if (waitForOutput >= 20) {
                        println "THREADS: ${taskPool.size()}"
                        println "Free memory: ${Math.round(Runtime.getRuntime().freeMemory() / 1024 / 1024)} Mb"
                        println "> curHour = $curHour (startHour = $startHour)"
                        waitForOutput = 0
                    } else {
                        waitForOutput++
                    }
                    continue
                }

            }

            // starting new tasks
            System.gc()
            for (task in tasks) {
                // creating the test class instance
                try {
                    taskPool << [
                            // creating new executor to guarantee isolated thread
                            pool: (ThreadPoolExecutor) Executors.newFixedThreadPool(1),
                            id: task.id,
                            runId: UUID.randomUUID().toString(),
                            test: Eval.me("return new ${task.class_name}()")
                    ]
                } catch (e) {
                    log(e.getMessage(), console_color_yellow)
                    log("(!) SKIPPED >> Cannot create an instance of: ${task.class_name}", console_color_yellow)
                    dbExecute("UPDATE ServerRuns_Queue SET status = 3 WHERE id = ${task.id}")
                    if (weekDay != 'Sat' && weekDay != 'Sun') {
                        EmailTools.sendEmail("${task.author}@webstaurantstore.com", "${task.author}@webstaurantstore.com",
                                'Bad Class Name in Nightly Scheduled Classes',
                                "<html><body>Can not create an instance of:<br />${task.class_name}<br /><br />" +
                                        "Please sheck: $webToolUrl/ServerRuns?filter=${task.author}</body></html>")
                    }
                    continue
                }
                // applying options
                taskPool.last().test.runId = taskPool.last().runId
                if (taskPool.last().test.xTestConcept() == 'web') {
                    taskPool.last().test.usingEnvironment(task.environment)
                    taskPool.last().test.runParamsMakeUniqueInStats = task.make_unique
                }
                try {
                    taskPool.last().test.runParams = all.Json.jsonToData(task.class_params)
                } catch(ignored) {
                    println "Empty runParams coming for current task"
                }
                Map suiteData = [
                        newServerRun: true,
                        suiteScript: testClass,
                        taskId: task.id,
                        suiteCurrentBrowser: task.browser,
                        suiteCurrentBrowserVersionOffset: task.browser_version_offset as Integer,
                        isRegularRunRequested: true,
                        isServerRun: true,
                        parallel: maxThreads,
                        parallelThreads: taskPool.size() + 1,
                        executorStatus: status,
                        smokeTestId: task.smoke_test_id
                ]
                log '--'
                log("Starting: ${task.class_name} - ${task.environment} - ${task.browser} ${task.browser_version_offset}", console_color_green)
                // running class
                taskPool.last().pool.submit(new ExecuteThread(taskPool.last().test, true, true, suiteData))
                taskStarted(task.id, taskPool.last().test.runId)
            }
            tasks = []
            cleanLog()
            System.gc()

        } // main loop

        log '=='
        log "Normal quit with status:"
        log status

        cleanLog()
        System.gc()
    }


    // QUEUE HANDLING
    //

    /** Updating queue record for task is started */
    void taskStarted(Integer taskId, String runId) {
        dbExecute("UPDATE ServerRuns_Queue SET status = 2, date_start = getdate(), runId='$runId' WHERE id = $taskId")
        System.gc()
    }


    /** Updating queue record for task has done */
    void taskDone(Integer taskId, String runId) {
        dbExecute("UPDATE ServerRuns_Queue SET status = 100, date_done = getdate(), runId='$runId' WHERE id = $taskId")
        System.gc()
    }


    /** Try to get new tasks several times */
    private List<Map> tryGetTasks(Integer count) {
        int attempts = 3
        List<Map> tasks
        while (attempts > 0) {
            attempts--
            tasks = getTasks(count)
            if (tasks) break
            if (attempts > 0) sleep(Math.abs(new Random().nextInt() % 3000) + 1)
        }
        return tasks
    }
    /** Get new tasks from the queue */
    List<Map> getTasks(Integer count) {
        String claim = UUID.randomUUID().toString()
        try {
            dbExecute("""
            UPDATE ServerRuns_Queue 
            SET claim_uuid = '$claim', [status] = 1 
            FROM (SELECT TOP $count id 
                  FROM ServerRuns_Queue
                  WHERE [status] = 0 AND sequentially = 0
                  ORDER BY priority DESC, id) b
            WHERE ServerRuns_Queue.id = b.id;""")
            return dbSelect("SELECT * FROM ServerRuns_Queue WHERE claim_uuid = '$claim'")
        } catch(e) {
            println '(!) Can not get new tasks'
            e.printStackTrace()
            return []
        }
    }


    // STARTER FEATURES
    //

    /** Critical Memory Issue */
    void criticalMemoryIssue() {
        status.memoryIssues++
        // emergency stopping the last task
        if (taskPool && taskPool.size() > 1) {
            emergencyTaskStop(taskPool.size() - 1)
        }
        System.gc()
    }

    /** Remove finished tasks */
    void removeTasks() {
        List forDel = []
        for (int i = 0; i < taskPool.size(); i++) {
            if (taskPool[i].pool.getActiveCount() == 0) {
                log '--'
                log("Stopping task ${taskPool[i].id}", console_color_green)
                taskPool[i].pool.shutdownNow()
                taskDone(taskPool[i].id, taskPool[i].runId)
                taskPool[i].pool = null
                taskPool[i].id = null
                taskPool[i].runId = null
                taskPool[i].test = null
                taskPool[i] = null
                System.gc()
                forDel << i
            }
        }
        for (idx in forDel.reverse()) {
            taskPool.removeAt(idx)
        }
    }


    /** Emergency task stop */
    void emergencyTaskStop(int queueIdx) {

        println "(!) Ememrgency stopping ${taskPool[queueIdx].test.class.name}"
        int id = taskPool[queueIdx].id

        // stopping task execution and killing task in the pool
        taskPool[queueIdx].test.xTestStopRequest('Server Starter got low memory issue and stopped the last started test')
        taskPool[queueIdx].test = null
        taskPool[queueIdx].pool.shutdown()
        taskPool[queueIdx].pool = null
        taskPool[queueIdx].runId = null
        taskPool[queueIdx].id = null
        taskPool[queueIdx] = null
        taskPool.removeAt(queueIdx)
        System.gc()

        // updating queue record
        dbExecute("UPDATE ServerRuns_Queue SET [status] = 4 WHERE id = $id")

        // re-queue the emergency stopped task
        int hour = getRealDate().getHours()
        if (hour < 7 || hour > 19) {
            String columns = dbSelect("""
            SELECT COLUMN_NAME
            FROM INFORMATION_SCHEMA.COLUMNS
            WHERE TABLE_NAME = 'ServerRuns_Queue' and ORDINAL_POSITION <> 1
            ORDER BY ORDINAL_POSITION
            """).collect { it.COLUMN_NAME }.join(', ')

            String tmpTableName = UUID.randomUUID().toString().replace('-', '')
            dbExecute("""
            SELECT * INTO ##$tmpTableName FROM ServerRuns_Queue WHERE id = $id;
            UPDATE ##$tmpTableName SET
                date_add = getdate(),
                date_done = null,
                [status] = 0,
                date_start = null,
                runId = '';
            INSERT INTO ServerRuns_Queue SELECT $columns FROM ##$tmpTableName;
            DROP TABLE ##$tmpTableName;
            """)
        }

        System.gc()
        log "Free memory right after the last task been killed: " +
                "${Math.round(Runtime.getRuntime().freeMemory() / 1024 / 1024)} Mb"
    }

}
