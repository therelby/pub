package above.allrun.helpers

import above.Run

class RunProjectId {

    Run r = run()

    /**
     * Get project id
     */
    int xProjectId(String project) {

        // checking
        if (!project || !(project instanceof String)) return 0
        project = project.replace(' ', '%20').trim()
        if (project == 'none' || project == '')
            return 0

        // handling the db
        r.dbInfoLoggingHide()
        List<Map> res = r.dbSelectSafe("SELECT id FROM [statistic].[Project] WHERE name = '${project}'", 'qa')
        r.dbInfoLoggingNormal()
        if (res == null) r.xStop('Can not access [statistic].[Project] table in the QA database')
        if (res) {
            return res[0].id
        } else {
            r.dbInfoLoggingHide()
            int id = r.dbInsert("INSERT INTO [statistic].[Project] (name) VALUES ('${project}')", 'qa')
            r.dbInfoLoggingNormal()
            if (!id) r.xStop('Can not add TFS project to [statistic].[Project] table in the QA database')
            return id
        }

    }

}
