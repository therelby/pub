package framework.all.util

import above.RunWeb
import above.Execute
import all.util.StateInfoUtil

class UtStateInfoUtilUnitTest extends RunWeb {
    static void main(String[] args) {
        new UtStateInfoUtilUnitTest().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    void test() {
        setup([
                author  : 'jreumont',
                title   : 'UtStateInfoUtilUnitTest',
                PBI     : 0,
                product : 'wss',
                project : 'Automation.Framework',
                keywords: 'unit test',
                logLevel: 'info'
        ])
        StateInfoUtil stateInfoUtil = new StateInfoUtil()
        assert stateInfoUtil.getStateCode('Maryland') == 'MD'
        assert stateInfoUtil.getStateCode('Florida') == 'FL'
        assert stateInfoUtil.getStateName('MD') == 'Maryland'
        assert stateInfoUtil.getStateName('FL') == 'Florida'
    }
}
