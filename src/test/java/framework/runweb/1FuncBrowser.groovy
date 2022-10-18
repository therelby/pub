package framework.runweb

import above.Execute

Execute.suite([
        new FuncBrowserUnitTest().usingEnvironment("dev")
])
