package framework.run.debug

import above.Execute

Execute.suite(
        [
                //browser: 'iossafari',
                remoteBrowser: true
        ],
        [
            new A()
])
