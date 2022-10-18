package above.smokecloud

import above.Execute

/**
 *      LOCAL DEBUG ONLY -- Smoke Cloud Test Executor
 */

Execute.suite(
        [
                remoteBrowser: true,
                environment: 'prod'
        ],
        [
                new ASmokeCloudTest()
        ]
)
