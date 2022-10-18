package framework.above

import framework.above.run.Ut01RunBase
import framework.above.run.Ut01RunBaseCommonClasses
import framework.above.run.Ut01RunBaseDb
import framework.above.run.Ut01RunBaseEmptySetup
import framework.above.run.Ut01RunDesktop
import framework.above.run.Ut01RunWeb
import framework.above.run.Ut01RunWebMaxSetup
import framework.above.run.Ut01RunWebMinSetup
import framework.above.test.Ut01TestChecks
import framework.above.test.Ut02TestReports
import framework.above.test.Ut03TestRandom
import framework.above.test.Ut03TestReportsException
import framework.above.test.Ut03VerifyGoodWrong
import framework.above.test.Ut04TestStats
import framework.above.test.Ut05TestStop
import framework.above.web.Ut01WebDriverTime

/**
 *      Prepare all the instances for various types of running: Debug, Regular, Server, Sequential, Parallel, etc.
 */

class AllInstances {

    static List<Object> utInstances() {

        return [

                // Run
                new Ut01RunBase(),
                new Ut01RunBaseEmptySetup(),
                new Ut01RunBaseDb(),
                new Ut01RunBaseCommonClasses(),
                new Ut01RunWeb(),
                //new Ut01RunWebIssueTracker(),
                new Ut01RunWebMaxSetup(),
                new Ut01RunWebMinSetup(),
                new Ut01RunDesktop(),

                // Testing
                new Ut01TestChecks(),
                new Ut02TestReports(),
                new Ut03TestRandom(),
                new Ut03TestReportsException(),
                new Ut03VerifyGoodWrong(),
                new Ut04TestStats(),
                new Ut05TestStop(),

                // Web
                //new Ut01WebDriverTime()

        ]

    }

}
