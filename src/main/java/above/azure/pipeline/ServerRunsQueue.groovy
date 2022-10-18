package above.azure.pipeline

import above.RunWeb
import above.web.helpers.Browsers
import all.Db
import all.Json

/**
 *      Running Tests in Parallel
 *      -- Azure DevOps pipelines stuff involved
 *      -- WebTool APIs involved
 *      (!) Make sure you are clearly understand all the Server Run system
 */

class ServerRunsQueue {

    /**
     * Add a Test to The Server Runs Queue
     *   @param className - full class name with package like wsstest.topic.subtopic.TestClassName
     *   @param params - any data you want to pass to the test
     *   @param makeUnique - useful when you are adding the same class name several times with different params
     *                          it makes different runs of the same class unique for our statistics
     *                              that is allowing to get all the checks coming from 'the same' test as unique
     * @return boolean success
     *
     * @author akudin
     */
    static boolean runTestOnServer(String className,
                                   String environment,
                                   boolean onceOnServer = false,
                                   Map params = [:],
                                   boolean makeUnique = false,
                                   boolean randomBrowser = false) {

        RunWeb r = run()

        if (!r.testAuthor)
            throw new Exception('setup() should be called before')

        if (randomBrowser && !onceOnServer)
            throw new Exception('randomBrowser = true must be using only with onceOnServer = true')

        // Checking if the class is existing in our system
        List<Map> inClasses = r.dbSelect("SELECT TOP 1 id FROM ServerRuns_Classes WHERE " +
                "package + '.' + class_name = '$className' ORDER BY id DESC", 'qa')
        if (inClasses == null)
            throw new Exception('Database issue -- cannot handle database')
        if (!inClasses)
            throw new Exception("The class $className must be present in https://qatools.dev.clarkinc.biz/ServerRuns")

        // creating browsers and versions list
        List<Map> brosVers = []
        if (onceOnServer) {
            if (!randomBrowser) {
                brosVers << [browser: Browsers.getDefaultBrowser(), versionOffset: 0]
            } else {
                List<Map> bros = Browsers.getServerBrowsersVersions()
                brosVers << bros[new Random().nextInt(bros.size())]
            }
        } else {
            brosVers = Browsers.getServerBrowsersVersions()
        }

        // adding o the queue
        r.log "Adding to Server Runs queue: $className"
        brosVers.each {
            new Db('qa').updateQuery("""
                INSERT INTO ServerRuns_Queue
                    (
                        priority,
                        sequentially,
                        class_name,
                        author,
                        environment,
                        browser,
                        browser_version_offset,
                        class_params,
                        make_unique
                    )
                    VALUES
                    (
                        -1,
                        0,
                        '$className',
                        '${r.testAuthor}',
                        '$environment',
                        '${it.browser}',
                        '${it.versionOffset}',
                        '${Json.dataToJson(params)}',
                        ${makeUnique.toString().replace('true', '1').replace('false', '0')}
                    );""")
        }

        return true
    }

}
