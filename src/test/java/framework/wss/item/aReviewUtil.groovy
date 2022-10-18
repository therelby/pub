package framework.wss.item

import above.Execute
import framework.wss.user.userurllogin.UtUserUrlLoginAllTypes

Execute.suite([
        environment    : 'dev',
        browser        : 'chrome',
        remoteBrowser  : false,
        parallelThreads: 1,
        // runType: 'Regular'
], [
        new UtReviewUtil(),

])