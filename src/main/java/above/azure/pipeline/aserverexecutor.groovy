package above.azure.pipeline

import above.Execute

/**
 *      Automation Framework Server Executor - PARALLEL
 *      -- (!) any changes are impacting to all our testing
 */

Execute.suite(
        [
                executeMode: 'OnceOnServer',
        ],
        [
                new ServerRunsStarter()
        ]
)
