package framework.above

import above.Execute


Execute.suite(

        [
                remoteBrowser: true
        ],

        AllInstances.utInstances()
)
