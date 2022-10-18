package framework.runweb

import above.Execute

Execute.suite([
        new FuncWaitUnitTest2().usingEnvironment("dev"),
//        new FuncWaitUnitTest().usingEnvironment("dev")
],1)
