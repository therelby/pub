package framework.above

import above.Execute


Execute.suite(

        [
                remoteBrowser: true,
                runType:       'Regular'
        ],

        AllInstances.utInstances()
)
