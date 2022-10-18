package framework.wss.pages.element.logggedinasmodal

import above.Execute
import framework.wss.pages.element.footer.UtCreditCardIcon

Execute.suite([
        //environment: 'prod',
        browser: 'edge',
        remoteBrowser: true,
        parallelThreads: 1,
        //    runType: 'Regular'
],[
     //  new UtLoggedInAsModal(),
        new UtLoggedInAsModalDelete(),
])