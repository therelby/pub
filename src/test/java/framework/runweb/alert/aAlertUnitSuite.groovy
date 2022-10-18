package framework.runweb.alert

import above.Execute

/*
*   Suite for FindUnitTest
*   @vdiachuk
*/

Execute.suite([
        new UtAlertDismissUnitTest(),
        new UtAlertWithTextUnitTest(),
        new UtAlertUnitTest()

])