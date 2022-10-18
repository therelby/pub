package framework.run.paraller

import above.Execute

Execute.suite([
        new WebForParallel(),
        new WebForParallel().usingEnvironment('test'),
        new WebForParallel().usingEnvironment('prod')
])
