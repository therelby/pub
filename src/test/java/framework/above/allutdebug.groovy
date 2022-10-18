package framework.above

import above.Execute


Execute.suite(

        [
                parallelThreads: 1,
                remoteBrowser: true
        ],

        AllInstances.utInstances()
)
