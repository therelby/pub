package above.jobs.cleanup

import above.Execute

Execute.suite(
        [
                executeMode: 'OnceOnServer'
        ],
        [
                new ScreenshotsOldDelete()
        ]
)
