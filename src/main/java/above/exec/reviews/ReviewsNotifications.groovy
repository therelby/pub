package above.exec.reviews

import above.ConfigReader
import above.Run
import above.report.EmailTools
import all.Db
import all.DbTools

/**
 *      Notifications For Test Results Reviews
 */

class ReviewsNotifications extends Run {

    String msg = ''
    String webToolUrl = ConfigReader.get('webtoolUrl')
    //static List<String> leftAuthors = ConfigReader.get('leftAuthors').split(',')

    static void main(String[] args) {
        new ReviewsNotifications().testExecute()
    }

    void test() {

        setup([author: 'akudin', title: "Notifications About Test Results Reviews", logLevel: 'info'])

        List res = DbTools.selectAll("""
            SELECT rw.*, ms.id msid, ms.notified FROM Stat_Result_Reviews rw
              LEFT JOIN Stat_Result_Review_Messages ms
                ON rw.id = ms.review_id AND rw.test_author <> ms.author
              INNER JOIN Stat_Result_Details de
                ON rw.issue_first_id = de.id
              INNER JOIN Stat_Test_Runs r
                ON r.id = de.run_record_id AND r.test_author <> rw.reviewer
            WHERE rw.email_sent = 0 or ms.notified = 0
            ORDER BY test_author, class_name, rw.id""", 'qa')

        if (!res) { return }
        res << [:]

        Date day = new Date()
        String lastAuthor = ''
        List<String> currentAuthorClassesDone = []
        List<Integer> currentAuthorReviewIds = []
        List<Integer> currentAuthorMessageIds = []

        for (row in res) {

            // left author
//            if (leftAuthors.contains(row.test_author)) {
//                log "${row.test_author} has left. Skipped"
//                new Db('qa').updateQuery("UPDATE Stat_Result_Reviews SET email_sent = 1 WHERE id = ${row.id}")
//                continue
//            }

            // new author
            if (row.test_author != lastAuthor && lastAuthor && msg) {
                // sending emails
                if (isServerRun() && msg) {
                    EmailTools.sendEmail("$lastAuthor@webstaurantstore.com", "$lastAuthor@webstaurantstore.com", "$lastAuthor - Test Results Reviews", "<html><body>$msg</body></html>")
                    EmailTools.sendEmail("$lastAuthor@webstaurantstore.com", "akudin@webstaurantstore.com", "$lastAuthor - Test Results Reviews", "<html><body>$msg</body></html>")
                    // making records marked done
                    String where = ''
                    currentAuthorReviewIds.each {
                        if (where) { where += ' OR ' }
                        where += "id = $it"
                    }
                    new Db('qa').updateQuery("UPDATE Stat_Result_Reviews SET email_sent = 1 WHERE $where")
                    if (currentAuthorMessageIds) {
                        where = ''
                        currentAuthorMessageIds.each {
                            if (where) {
                                where += ' OR '
                            }
                            where += "id = $it"
                        }
                        new Db('qa').updateQuery("UPDATE Stat_Result_Review_Messages SET notified = 1 WHERE $where")
                    }
                } else {
                    log 'mgs ='
                    log msg.replace('<br />', '\n').replace('</p>', '</p>\n')
                    log '--'
                }
                msg = ''
                currentAuthorClassesDone = []
                currentAuthorReviewIds = []
                currentAuthorMessageIds = []
            }

            if (!row) { break }

            // row handling - new review
            if (!row.email_sent) {
                if (!currentAuthorClassesDone.contains(row.class_name)) {
                    currentAuthorClassesDone << row.class_name
                    msg += "<p style=\"font-size:13pt\">New review for <b>${row.class_name}</b><br />"
                    msg += "<a href=\"$webToolUrl/TestResultsReview?day=${row.issue_date.format('MM/dd/yyyy')}&firstid=${row.issue_first_id}\">$webToolUrl/TestResultsReview?day=${row.issue_date.format('MM/dd/yyyy')}&firstid=${row.issue_first_id}</a></p>"
                }
                currentAuthorReviewIds << row.id
            }

            // row handling - new messages
            if (row.msid && !row.notified) {
                msg += "<p style=\"font-size:13pt\">-- new message<br />"
                msg += "<span>-- </span><a href=\"$webToolUrl/TestResultsReviewControl?id=${row.id}\">$webToolUrl/TestResultsReviewControl?id=${row.id}</a></p>"
                currentAuthorMessageIds << row.msid
            }

            // row final
            lastAuthor = row.test_author
        }

    }


    /** Add test to message body */
    private void add(String text, data, String filter = '') {
        msg += "<p style=\"font-size:13pt\"><b>${data.test_class}</b><br />"
        msg += "$text<br />"
        msg += "<a href=\"$webToolUrl/Run?id=${data.run_id}\">$webToolUrl/Run?id=${data.run_id}${ filter ? "&filter=$filter" : "" }</a></p>"
    }

}
