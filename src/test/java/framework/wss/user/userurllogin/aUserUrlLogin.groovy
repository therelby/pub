package framework.wss.user.userurllogin

import above.Execute
import framework.wss.user.UtUserDetail
import wss.user.userurllogin.UserUrlLogin

//Execute.suite([
Execute.suite([
        environment    : 'dev',
        browser        : 'chrome',
        remoteBrowser  : 'true',//false,
        parallelThreads: 1,
        // runType: 'Regular'
], [
        new UtUserUrlLoginNewUser(),
        new UtUserUrlLoginReusableUser(),
        new UtUserUrlLogin(),

])
