package framework.wss.pages.productdetail.userphotovideo

import above.Execute

Execute.suite([
        environment: 'dev',
        browser: 'chrome',
        remoteBrowser: false,
        /*   parallelThreads: 1,
           runType: 'Regular' ,//Debug
           browserVersionOffset: -1   */],[


        new UtPDPUserPhotoVideo(),

])