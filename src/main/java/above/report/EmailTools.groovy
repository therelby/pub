package above.report

/**
 *      Email Sending Tools
 */
class EmailTools {

    /** Send a single email */
    synchronized static Boolean sendEmail(String from, String to, String subject, String body) {
        try {
            new EmailSender().send(from, to, subject, body)
            return true
        } catch (e) {
            run().log e
            return false
        }
    }

}
