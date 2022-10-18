package above.jobs.classes

import above.Run
import groovy.io.FileType

/**
 *      Check the Project For Disappeared Classes and Handle Them
 */

class JobCheckDeletedClasses extends Run {

    Map prjList = [:]

    static void main(String[] args) {
        new JobCheckDeletedClasses().testExecute()
    }

    void test() {

        setup([ author: 'akudin', title: 'Job: Check the Project For Disappeared Classes and Handle Them' ])

        getProjectClassesList()

        List<Map> dbList = dbSelect('SELECT * FROM ServerRuns_Classes', 'qa')
        log "> ${dbList.size()} classes found in the db table\n"

        int present = 0
        int notfound = 0
        int deleted = 0
        for (cl in dbList) {
            log cl.toString()
            if (prjList.containsKey(cl.package + '.' + cl.class_name)) {
                log 'PRESENT'
                present++
            } else {
                log 'NOT FOUND'
                notfound++
                if (cl.status == 100) {
                    // removing checks for disappeared classes -- treat them as renamed ones so far
                    // TODO: remove this update after class UUID added
                    //dbExecute("UPDATE Stat_Test_Runs SET stats_involved = 0 WHERE class_id = ${cl.id}", 'qa')
                    // verify stats removing?

                    // canceling nightly scheduling for the class
                    //dbExecute("DELETE FROM ServerRuns_Nightly WHERE class_id = ${cl.id}", 'qa')

                    // hiding the class in our lists
                    //dbExecute("UPDATE ServerRuns_Classes SET [status] = 99 WHERE id = ${cl.id}", 'qa')
                    deleted++
                }
            }
            log ''
        }
        log ''

        log '--'
        log "TOTAL classes in db table: ${dbList.size()}"
        log " -- PRESENT classes: $present"
        log " -- NOT FOUND classes: $notfound"
        log "REMOVED from nightly classes: $deleted"
    }


    /** Classes list from the project folders */
    void getProjectClassesList() {
        List<String> list = []
        String baseDir = System.getProperty("user.dir").replace('\\above\\jobs\\classes', '').replace('\\/above\\/jobs\\/classes', '')
        File dir = new File(baseDir)
        dir.eachFileRecurse (FileType.FILES) { file -> list << file.path }
        for (file in list.reverse()) {
            if (file.endsWith('.groovy')) {
                String className = file.replace(baseDir + '/', '').replace(baseDir + '\\', '').replace('.groovy', '').replace('\\', '.').replace('\\/', '.')
                prjList.put(className, null)
            }
        }
        log "> ${prjList.size()} classes found in the project folders"
        log prjList
    }

}
