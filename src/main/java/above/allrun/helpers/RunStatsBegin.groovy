package above.allrun.helpers


import above.Run

/**
 *      Regular Run Statistics - Init
 */

class RunStatsBegin {

    final private Run r = run()

    final private String qInsertRun = """INSERT INTO Stat_Test_Runs (run_id, test_author, test_class, test_title, 
        test_info, search_text, old_approach, pipeline_build_id, class_id, author_id, concept_id, project_id) 
        VALUES ('%runId', '%testAuthor', '%testClass', '%testTitle', '%testInfo', '%searchText', %approach, 
        %buildId, %classId, %authorId, %conceptId, %tfsProjectId);"""

    /**
     * Adding test statistics database record after Run.setup() applied
     */
    void statisticsInit() {

        // local stuff?
        if (!r.isRunDebug() && r.testClass.startsWith('local')) {
            throw new Exception('local.* stuff can not be executing as Regular run')
        }

        // regular run?
        if (r.runDebug)
            return

        // test TFS project id
        r.testTfsProjectId = new RunProjectId().xProjectId(r.testTfsProject)
        r.dbInfoLoggingHide()
        r.dbUpdate("UPDATE ServerRuns_Classes SET project_id = ${r.testTfsProjectId} WHERE id = ${r.testClassId}", 'qa')
        r.dbInfoLoggingNormal()

        // making class run with params unique
        if (r.runParamsMakeUniqueInStats && r.runParams) {
            r.testClass = "${r.testClass}(${r.runParams.toString().md5()})"
        }

        String searchText = (r.testKeywords + '\n' + r.testNotes.join('\n')).trim().replace("'", "''")

        r.log 'STATISTICS: Initial -- creating run record in QA database...'
        String q = qInsertRun
                .replace('%runId', r.runId)
                .replace('%testAuthor', r.testAuthor)
                .replace('%testClass', r.testClass)
                .replace('%testTitle', r.testTitle.replace("'", "''"))
                .replace('%testInfo', r.getInfo().toPrettyString().replace("'", "''"))
                .replace('%searchText', searchText.replace("'", "''"))
                .replace('%approach', r.runPbiBased.toString().replace('true', '0').replace('false', '1'))
                .replace('%buildId', r.runPipelineBuildId.toString())
                .replace('%classId', r.testClassId.toString())
                .replace('%authorId', r.testAuthorId.toString())
                .replace('%conceptId', above.types.ConceptId.conceptId[r.xTestConcept()].toString())
                .replace('%tfsProjectId', r.testTfsProjectId.toString())
        r.dbInfoLoggingHide()
        r.runRecordId = r.dbInsert(q, 'qa')
        r.dbInfoLoggingNormal()
        if (!r.runRecordId) r.xStop('Can not create statistics record for this test run')

        if (r.isServerRun()) { r.dbClose('qa') }
        r.log '=='
        System.gc()
    }

}
