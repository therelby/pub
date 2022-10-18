package framework.wss.pages.productdetail.vg

import above.Execute

Execute.suite([
        //environment: 'dev',
        browser: 'chrome',
//          remoteBrowser: true,
//          parallelThreads: 1,
//          runType: 'Regular' ,//Debug
//          browserVersionOffset: -1   // for all classes in the suite
], [
      //  new UtPDPVirtualGrouping(),
       // new UtPDPVGData(),
       new UtPDPVirtualGroupingOptionsModal(),
     //   new UtPDPVirtualGroupingNumberFromUrl(),
])