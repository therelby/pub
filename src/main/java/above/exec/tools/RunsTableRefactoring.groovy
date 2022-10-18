package above.exec.tools

import above.Run
import above.allrun.helpers.RunAuthorId
import above.allrun.helpers.RunClassId
import above.allrun.helpers.RunProjectId

/**
 *      Stat_Test_Runs Refactoring Tool
 *      -- it normalises the *_id columns based on the text columns values
 */

class RunsTableRefactoring extends Run {

    static void main(String[] args) {
        new RunsTableRefactoring().testExecute()
    }

    void test() {

        setup([ author: 'akudin', title: 'Runs Table Refactoring', logLevel: 'info' ])
        dbUsing('qa')

        log 'TOTAL RECORDS:'
        log dbSelect('SELECT COUNT(id) cnt FROM Stat_Test_Runs')[0].cnt
        log '--'

        log 'Empty Class Names Updating to class_id = 0'
        log dbUpdateAffected("UPDATE Stat_Test_Runs SET class_id = 0 WHERE test_class = '' AND class_id <> 0")
        log '--'

        log 'TOTAL class_id = 0:'
        log dbSelect('SELECT COUNT(id) cnt FROM Stat_Test_Runs WHERE class_id = 0')[0].cnt
        log '--'

        log 'AUTHORS'
        List<String> authors = dbSelect('SELECT DISTINCT test_author FROM Stat_Test_Runs ORDER BY test_author')
                .collect {it.test_author }
        log "Count: ${authors.size()}"
        authors.each {
            log "-- $it"
            int authorId = new RunAuthorId().xAuthorId(it, true)
            log "-- -- $authorId"
            log "Stat_Test_Runs - " + dbUpdateAffected("UPDATE Stat_Test_Runs SET author_id = $authorId " +
                    "WHERE test_author = '$it' AND author_id <> $authorId")
            log "ServerRuns_Classes - " + dbUpdateAffected("UPDATE ServerRuns_Classes SET author_id = $authorId " +
                    "WHERE author = '$it' AND author_id <> $authorId")
        }
        log '--'

        log 'PROJECTS'
        List<String> projects = dbSelect('SELECT DISTINCT test_tfs_project FROM Stat_Test_Runs ORDER BY test_tfs_project')
                .collect {it.test_tfs_project }
        log "Count: ${projects.size()}"
        projects.each {
            log "-- $it"
            int projectId = new RunProjectId().xProjectId(it)
            log "-- -- $projectId"
            log dbUpdateAffected("UPDATE Stat_Test_Runs SET project_id = $projectId " +
                    "WHERE test_tfs_project = '$it' AND project_id <> $projectId")
        }
        log '--'

        log 'CLASSES'
        List<Map> classes = dbSelect("SELECT * FROM (SELECT " +
                "CASE WHEN test_class like '%(%' THEN SUBSTRING(test_class, 0, CHARINDEX('(', test_class)) " +
                "ELSE test_class END test_class, test_author FROM Stat_Test_Runs WHERE test_class <> '') a " +
                "GROUP BY a.test_class, a.test_author " +
                "ORDER BY a.test_class DESC")
        log "Count: ${classes.size()}"
        int totalClasses = 0
        for (it in classes) {
            log it.test_class
            int tclId = new RunClassId().xClassId(it.test_class, it.test_author)
            if (!tclId) {
                log '!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!'
                log '                       CANNOT GET THE CLASS ID'
                log '!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!'
                continue
            }
            int aff = dbUpdateAffected("UPDATE Stat_Test_Runs SET class_id = ${tclId} " +
                    "WHERE ((CHARINDEX('(', test_class) = 0 AND test_class = '${it.test_class}') " +
                       "OR ((CHARINDEX('(', test_class) > 0 AND SUBSTRING(test_class, 0, CHARINDEX('(', test_class)) = '${it.test_class}'))) " +
                    "AND test_author = '${it.test_author}' AND class_id <> $tclId")
            totalClasses += aff
            log "$aff (total: $totalClasses)"
        }

    }

}
