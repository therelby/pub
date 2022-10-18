package framework.wss.pages.productdetail.customizeandaddtocart.purplecustombutton

import above.Execute

Execute.suite([
        environment  : 'dev',
        browser      : 'chrome',
        remoteBrowser: false,
        /*   parallelThreads: 1,
           runType: 'Regular' ,//Debug
           browserVersionOffset: -1   */], [

        new UtPDPPageCustomizeAndAddToCartPurpleButtonNoQuantityDiscount(),
        new UtPDPPageCustomizeAndAddToCartPurpleButtonQuantityDiscount(),

])
