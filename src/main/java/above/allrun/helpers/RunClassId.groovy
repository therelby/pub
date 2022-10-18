package above.allrun.helpers

import above.Run

class RunClassId {

    Run r = run()

    /**
     * Get test class id
     */
    int xClassId(String className) {

        if (r.isRunDebug()) return 0

        // checking if the class has registered
        r.dbInfoLoggingHide()
        List<Map> res = r.dbSelect("SELECT TOP 1 id, [status] FROM ServerRuns_Classes " +
                "WHERE package + '.' + class_name = '${className}' ORDER BY id DESC", 'qa', true)
        r.dbInfoLoggingNormal()
        if (res == null) r.xStop('Can not handle QA database for statistics stuff (1)')

        if (!res) {
            r.log '--'
            r.log 'Adding this class to QA database...'

            // UUID present?
            if (!r.testClassUuid)
                throw new Exception('Unique UUID value must be provided in the setup() id option')

            // renamed?
            r.dbInfoLoggingHide()
            res = r.dbSelect("SELECT TOP 1 id, [status] FROM ServerRuns_Classes " +
                    "WHERE uuid = '${r.testClassUuid}' ORDER BY id DESC", 'qa', true)
            r.dbInfoLoggingNormal()
            if (res == null) r.xStop('Can not handle QA database for statistics stuff (2)')

            int classId
            List cList = className.split('\\.')
            if (res) {
                // renaming
                r.log 'This class was renamed. Updating the class name...'
                classId = res[0].id
                r.dbInfoLoggingHide()
                if (!r.dbUpdateAffected("UPDATE ServerRuns_Classes SET " +
                        "package = '${className - ".${cList.last()}"}', " +
                        "class_name = '${cList.last()}' WHERE id = ${classId}", 'qa'))
                    r.xStop('Can not rename the class in the QA database')
                r.dbInfoLoggingNormal()
            } else {
                // adding new class
                r.dbInfoLoggingHide()
                classId = r.dbInsert("INSERT INTO ServerRuns_Classes ([status], author, author_id, package, class_name, uuid) values " +
                        "(100, '-', 0, '${className - ".${cList.last()}"}', '${cList.last()}', '${r.testClassUuid}')", 'qa')
                r.dbInfoLoggingNormal()
                if (!classId)
                    r.xStop('classId: QA Database issue when inserting into ServerRuns_Classes')
            }

            r.log "TEST CLASS ADDED: ${className}"
            r.log '--'
            return classId
        } else if (res[0].status != 100) {
            if (!r.testClassUuid)
                throw new Exception('Unique UUID value must be provided in the setup() id option')
            r.dbInfoLoggingHide()
            if (!r.dbUpdate("UPDATE ServerRuns_Classes SET [status] = 100, uuid = '${r.testClassUuid}' " +
                    "WHERE id = ${res[0].id}", 'qa')) {
                r.dbInfoLoggingNormal()
                r.xStop('startStats: QA Database issue when updating ServerRuns_Classes')
            }
            r.dbInfoLoggingNormal()
            return res[0].id
        } else {
            if (r.testClassUuid && !res[0].uuid) {
                // uuid became for an old class
                r.dbInfoLoggingHide()
                r.dbUpdate("UPDATE ServerRuns_Classes SET uuid = '${r.testClassUuid}' WHERE id = ${res[0].id}", 'qa')
                r.dbInfoLoggingNormal()
            }
            return res[0].id
        }

    }

}
