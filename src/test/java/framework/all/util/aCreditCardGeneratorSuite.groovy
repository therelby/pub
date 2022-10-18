package framework.all.util

import above.Execute

/*
*   Suite for Credit Card generator tests
*   @vdiachuk
*/

Execute.suite([
        new UtCreditCardUnitTest().usingEnvironment("dev"),
])