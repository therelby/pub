package above.allrun.helpers

import above.Run

class RunPBIs {

    Run r = run()

    /** Update class BPIs */
    boolean xTryClassPBIsUpdate(List<Integer> PBIs, int classId) {
        int attempts = 3
        boolean result = false
        while (attempts > 0) {
            attempts--
            result = xClassPBIsUpdate(PBIs, classId)
            if (result) return true
            if (attempts > 0) sleep(Math.abs(new Random().nextInt() % 3000) + 1)
        }
        return result
    }
    protected boolean xClassPBIsUpdate(List<Integer> PBIs, int classId) {

        if (r.isRunDebug() || !r.runPbiBased) return true
        r.xSetRunLastIssue('UNKNOWN')

        // getting class PBIs
        r.dbInfoLoggingHide()
        List<Map> existing = r.dbSelectSafe("SELECT * FROM [statistic].[TestPBI] WHERE class_id = $classId", 'qa')
        if (existing == null)
            return r.xSetRunLastIssue('Can not select PBIs for the class')
        r.dbInfoLoggingNormal()

        List<Integer> active = existing.findAll{it.is_active}.collect{it.pbi_id}
        List<Integer> inactive = existing.findAll{!it.is_active}.collect{it.pbi_id}
        String queries = ''

        // disappeared PBIs
        String where = ''
        for (pbi in active)
            if (!PBIs.contains(pbi))
                where += " or pbi_id = $pbi"
        if (where)
            queries += "UPDATE [statistic].[TestPBI] SET is_active = 0, date_updated = getdate() " +
                "WHERE class_id = $classId and (${where.replaceFirst(' or ', '')});\n"

        // appeared after been inactive PBIs
        where = ''
        for (pbi in (inactive))
            if (PBIs.contains(pbi))
                where += " or pbi_id = $pbi"
        if (where)
            queries += "UPDATE [statistic].[TestPBI] SET is_active = 1, date_updated = getdate() " +
                "WHERE class_id = $classId and " +
                "(${where.replaceFirst(' or ', '')});\n"

        // new PBIs
        for (pbi in (PBIs - (existing.collect {it.pbi_id})))
            queries += "INSERT INTO [statistic].[TestPBI] (class_id, pbi_id) VALUES ($classId, $pbi);\n"

        r.dbInfoLoggingHide()
        if (!r.dbExecute(queries, 'qa')) {
            r.dbInfoLoggingNormal()
            return r.xSetRunLastIssue('Can not update PBIs for the class')
        }
        r.dbInfoLoggingNormal()

        // saving PBIs
        r.xSetTestPBIs(PBIs)

        return true
    }

}
