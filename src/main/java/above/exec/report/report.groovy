package above.exec.report

import above.Execute

/**
 *      Daily Report Creation Executor
 */

Execute.suite(
        [
                executeMode: 'OnceOnServer'
        ],
        [
                new ReportDaily()
        ]
)
