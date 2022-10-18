package framework.runweb

import above.Execute

/*
*   Suite for Checkbox and radio button testing
*   @micurtis
*/

Execute.suite([
        new FuncCheckElementsUnitTest().usingEnvironment('dev')
])