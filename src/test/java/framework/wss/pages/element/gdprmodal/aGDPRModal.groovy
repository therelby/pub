package framework.wss.pages.element.gdprmodal

import above.Execute


Execute.suite([
        //environment: 'prod',
        browser: 'safari',
        remoteBrowser: true,
        parallelThreads: 1,
    //    runType: 'Regular'
],[
new UtGDPRModal(),
])