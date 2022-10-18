package above.report

import above.ConfigReader

/**
 *      Critical Alerts Sending
 *      @author akudin
 */
class EmailAlerts {

    static List<String> criticalMailList = ConfigReader.get('frameworkCriticalReportEmailList').split(',')*.trim().toList()


    /** Send alert to configuration.properties/frameworkDebugPerson */
    static sendFrameworkAlert(String subject, String text) {
        if (run().isServerRun()) {
            criticalMailList.each {
                EmailSender.sendToSever(it, subject,
                                 '<html><body><pre style="font-size: 12pt">' + text + '</pre></body></html>')
            }
        } else {
            run().log(text, run().console_color_red)
        }
    }

}
