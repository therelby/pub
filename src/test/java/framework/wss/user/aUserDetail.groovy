package framework.wss.user

import above.Execute
//Execute.suite([
Execute.suite([
        environment: 'dev',
        browser: 'chrome',
        remoteBrowser: false,
        parallelThreads: 1,
       // runType: 'Regular'
],[

        new UtUserDetail(),

])

