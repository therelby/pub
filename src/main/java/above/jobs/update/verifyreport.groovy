package above.jobs.update

import above.Execute

Execute.suite(
        [
                executeMode: 'OnceOnServer'
        ],
        [
                new VerifyReportWeek()
        ]
)
