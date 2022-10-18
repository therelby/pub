package above.exec.tfsstats

import above.Execute

Execute.suite(
        [
                executeMode: 'OnceOnServer'
        ],
        [
                new TfsStatistics()
        ]
)
