package framework.wss.pages.productdetail.internalnotesmodal

import above.Execute

Execute.suite([
        //environment: 'prod',
        browser: 'chrome',
        remoteBrowser: false,
      //  parallelThreads: 1,
        //    runType: 'Regular'
],[
      new UtInternalNotes(),
     //   new UtInternalNotesVendorInstruction(),

        //new UtInternalNotesStockCountTable(),
])