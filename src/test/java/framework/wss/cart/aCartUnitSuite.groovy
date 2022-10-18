package framework.wss.cart

import above.Execute

Execute.suite([
        environment: 'dev',
        browser: 'safari',
        remoteBrowser: true],[
        //   new UtCartUnitTest(),
        //new UtCartAddAlltemTypes(),
        //new UtCartQuantityZero(),
        new UtMapCartTesting()
],1)