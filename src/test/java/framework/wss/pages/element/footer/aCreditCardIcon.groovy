package framework.wss.pages.element.footer

import above.Execute
import framework.wss.pages.element.topmenu.UtCartTopElement

Execute.suite([
        //environment: 'prod',
        browser: 'chrome',
        remoteBrowser: false,
        parallelThreads: 1,
        //    runType: 'Regular'
],[
        new UtCreditCardIcon(),
])