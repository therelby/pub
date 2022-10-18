package framework.wss.pages.productdetail.customizeandaddtocart.nocustombuttons

import above.Execute

Execute.suite([
        environment  : 'dev',
        browser      : 'chrome',
        remoteBrowser: false,
        /*   parallelThreads: 1,
           runType: 'Regular' ,//Debug
           browserVersionOffset: -1   */], [

        new UtPDPPageCustomizeAndAddToCartInactiveNoUrlHasQuantityDiscountHasMAP(),
        new UtPDPPageCustomizeAndAddToCartInactiveNoUrlHasQuantityDiscountNoMAP(),
        new UtPDPPageCustomizeAndAddToCartInactiveNoUrlNoQuantityDiscountNoMap()

])
