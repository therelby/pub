package framework.runweb.select

import above.Execute


/*
*   Suite for Select Different Browser testing
*   @vdiachuk
*/

Execute.suite([
        new UtSelectDifferentBrowser().usingBrowser('chrome'),
       new UtSelectDifferentBrowser().usingBrowser('chrome'),
      new UtSelectDifferentBrowser().usingBrowser('chrome'),

])
