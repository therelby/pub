package framework.wss.pages.productdetail

import above.Execute
import framework.wss.pages.productdetail.customizeandaddtocart.UtPDPageCustomizeAndAddToCart

Execute.suite([
        environment  : 'dev',
        browser      : 'chrome',
        remoteBrowser: false,
        /*   parallelThreads: 1,
           runType: 'Regular' ,//Debug
           browserVersionOffset: -1   */], [

/*        new UtPDPage(),
        new UtPDPageP5().usingRemoteBrowser("chrome"),
        new UtPDPageWebplusMessages().usingRemoteBrowser("chrome"),*/
new UtPDPageCustomizeAndAddToCart().usingRemoteBrowser("chrome"),
/*        new UtPDPNavigate(),
        new UtPDPWarrantyData(),
        new UtPDPDownloadData(),
        new UtPDPResourcesData(),*/


])
