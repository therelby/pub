package framework.runweb.funcwindow

import above.Execute

/*
*   Suite for FuncWidow Unit test
*   @vdiachuk
*/

Execute.suite([
        new UtFuncWindowOriginalWindow(),
        new UtFuncWidow(),
        new UtWindowTitles(),
        new UtFuncWindowPartUrl(),
])