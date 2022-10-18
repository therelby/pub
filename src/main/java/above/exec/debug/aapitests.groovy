package above.exec.debug

import above.Execute

Execute.suite(
        [
                executeMode: 'OnceOnServer'
        ],
        [
                new ApiTests()
        ]
)
