package above.exec.browsers

import above.Execute

Execute.suite(
        [
                executeMode: 'OnceOnServer',
                remoteBrowser: true
        ],
        [
                new BrowserVersionsUpdate()
        ]
)
