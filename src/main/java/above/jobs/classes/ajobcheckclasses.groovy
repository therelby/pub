package above.jobs.classes

import above.Execute
import above.jobs.cleanup.ScreenshotsOldDelete

Execute.suite(
        [
                executeMode: 'OnceOnServer'
        ],
        [
                new JobCheckDeletedClasses()
        ]
)
