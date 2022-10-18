package framework.runweb.select


import above.Execute

/*
*   Suite for Select testing
*   @vdiachuk
*/

Execute.suite([
        new UtSelectMulty().usingEnvironment('dev'),
        new UtSelect().usingEnvironment('dev'),

])
