package above.exec.reviews

import above.Execute
import above.exec.report.ReportDaily

/**
 *      Notifications For Test Results Reviews
 */

Execute.suite(
        [
                executeMode: 'OnceOnServer'
        ],
        [
                new ReviewsNotifications()
        ]
)
