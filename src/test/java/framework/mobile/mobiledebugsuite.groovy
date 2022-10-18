package framework.mobile

import above.Execute

Execute.suite([browser: 'mobilechrome'], [
        new MobileDebug(),
        new MobileDebug()
], 2)
