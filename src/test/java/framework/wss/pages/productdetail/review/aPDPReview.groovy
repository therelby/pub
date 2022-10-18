package framework.wss.pages.productdetail.review

import above.Execute

Execute.suite([
        //environment: 'prod',
        browser: 'chrome',
        //    remoteBrowser: true,
        //   parallelThreads: 1,
        //   runType: 'Regular' ,//Debug
        //    browserVersionOffset: -1
], [  // for all classes in the suite],[
      new UtPDPReview(),
      new UtPDPRating(),
      new UtPDPReviewGetData(),
      new UtPDPReviewsSorting(),
      new UtPDPReviewNameConverter(),
      new UtPDPReviewsDateConverter(),
      new UtPDPReviewLeaveReview(),
])