package framework.wss.user.userurllogin

import above.Execute

Execute.suite([
        environment    : 'test',
        browser        : 'chrome',
        remoteBrowser  : false,
        parallelThreads: 1,
        // runType: 'Regular'
], [
        new UtUserUrlLoginAllTypes(),

])
