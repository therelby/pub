package framework.project

import above.Execute

Execute.suite(
        [
                executeMode: 'OnceOnServer'
        ],
        [
                new ProjectHeath()
        ]
)
