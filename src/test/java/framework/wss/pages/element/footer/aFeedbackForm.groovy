package framework.wss.pages.element.footer

import above.Execute

Execute.suite([
        //environment: 'prod',
        browser: 'chrome',
        remoteBrowser: false,
        parallelThreads: 1,
        //    runType: 'Regular'
],[
        new UtFeedbackForm(),
])