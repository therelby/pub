package above.report

import above.Run
import above.RunWeb
import all.ClarkInc
import all.Db
import all.DbTools

/**
 *      Test Results Auto Reviews Generator
 */

class TestResultsAutoReview {

    /** Test Results Auto Review */
    synchronized static checkAndCreateAutoReviews(Date day) {

        if (!ClarkInc.isWorkday(day)) {
            return
        }

        Run r = run()

        String q = """
            SELECT  count(d.id) cnt, min(d.id) id, test_author, test_class, description
            FROM Stat_Result_Details d
              INNER JOIN Stat_Test_Runs r
                ON d.run_record_id = r.id
                    WHERE date_done >= '${day.minus(1).format('yyyy-MM-dd')} 7:00' AND date_done < '${day.format('yyyy-MM-dd')} 7:00' AND is_check = 1 AND r.stats_involved = 1
                    GROUP by description, test_author, test_class
            ORDER BY cnt DESC;"""

        List issues = DbTools.selectAll(q, 'qa')
        for (issue in issues) {
            r.log "${issue.id} - ${issue.test_author} - ${issue.test_class}"
            checkIssue(issue, day)
        }
    }


    /** Check certain issue */
    synchronized static private checkIssue(issue, Date day) {

        // checking duplicates
        String q = """
            SELECT count(d.id) cnt, r.run_id FROM Stat_Result_Details d
                INNER JOIN Stat_Test_Runs r
                  ON d.run_record_id = r.id
            WHERE 
                d.is_check = 1 AND d.result = 0
                AND description = (SELECT description FROM Stat_Result_Details WHERE id = ${issue.id}) 
                AND date_done >= '${day.minus(1).format('yyyy-MM-dd')} 7:00' AND date_done < '${day.format('yyyy-MM-dd')} 7:00'
                AND r.stats_involved = 1
            group by run_id;"""

        List grouped = DbTools.selectAll(q, 'qa')
        for (row in grouped) {
            if (row.cnt > 1) {
                addReview(
                        issue.id,
                        day,
                        'The same check description appeared more than one time for the same run.\nProbably missed some details in the description like user type, item id, etc.',
                        9,
                        issue.description,
                        issue.test_class,
                        issue.test_author
                )
                break
            }
        }

        // checking nulls outside [ ]
        if (issue.description.replaceAll("\\[.*?\\]|\\{.*?\\}", "").toLowerCase().contains('null')) {
            addReview(
                    issue.id,
                    day,
                    'Probably unhandled null value in the check description.',
                    9,
                    issue.description,
                    issue.test_class,
                    issue.test_author
            )
        }

    }


    /** Add issue review */
    synchronized static private addReview(int issueId,
                                  Date day,
                                  String comment,
                                  int resolution,
                                  String description,
                                  String className,
                                  String testAuthor) {

        List res = DbTools.selectAll("""
            SELECT id FROM Stat_Result_Reviews
            WHERE issue_first_id = $issueId AND reviewer = 'framework' AND resolution_id = $resolution AND comment = '$comment'""", 'qa')

        if (res) {
            return
        }

        new Db('qa').updateQuery("""
            INSERT INTO Stat_Result_Reviews
            (issue_first_id, reviewer, comment, resolution_id, issue_date, issue, class_name, test_author)
            VALUES
            (
                $issueId,
                'framework',
                '${comment}',
                $resolution,
                '${day.format('yyyy-MM-dd')}',
                '${description.replace("'", "''")}',
                '$className',
                '$testAuthor');""")
    }

}
