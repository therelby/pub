package framework.wss.pages.productdetail.stickyheader

import above.Execute

Execute.suite([
        environment: 'dev',
        browser: 'chrome',
        remoteBrowser: false,
           parallelThreads: 1,
        /*    runType: 'Regular' ,//Debug
            browserVersionOffset: -1   */],[
        
//        new UtPDPStickyHeader(),
//        new UtPDPStickyHeaderQuantityDiscount(),
//        new UtPDPStickyHeaderMinMustBuy(),
        new UtPDPStickyHeaderGetData(),
//        new UtPDPStickyHeaderInputQuantity(),
//        new UtPDPStickyHeaderPlatPlusOverride(),
//        new UtPDPStickyHeaderSalePrice()
])
