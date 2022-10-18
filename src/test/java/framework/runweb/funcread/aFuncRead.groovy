package framework.runweb.funcread

import above.Execute

Execute.suite([
        new UtFuncReadClearUnitTest(),
        new UtFuncReadSetUnitTest(),
        new FuncReadUnitTest(),
        new UtFuncReadGetAllAttributesSafe(),
        new UtFuncReadGetNodeText(),
        new UtFuncReadGetCssValue(),
])
