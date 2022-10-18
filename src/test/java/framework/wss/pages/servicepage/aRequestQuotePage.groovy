package framework.wss.pages.servicepage


import above.Execute


Execute.suite([

        browser: 'chrome',
        environment: 'dev',
        //remoteBrowser: true,
        //runType: 'Regular'
],[

        new UtRequestQuote(),
])
