package above.azure

import above.RunWeb

/**
 *      High Level TFS Plans, Suites, Testcases Handling
 *      @author akudin
 */
class AzureDevOpsTools {


    /** Get all tcIds for the suite and all next level suites */
    synchronized static List<Integer> getTcIdsForTfsSuite(Integer planId, Integer suiteId, String project) {

        RunWeb r = run()
        List<Integer> result = [suiteId]

        // getting suites for the plan
        def suites = AzureDevOpsTestPlans.getSuites(planId, project)
        if (suites) {
            List <Integer> sIds = getNextLevelsForSuite(suites, suiteId.toString())
            if (sIds) {
                sIds.each {
                    def tcs = AzureDevOpsTestPlans.getTcs(it, planId, project)
                    if (tcs) {
                        tcs.value.each {
                            result << it.testCase.id.toInteger()
                        }
                    }
                }
            }
        }

        return result.sort()
    }


    // Get next level suites ids
    private synchronized static List<Integer> getNextLevelsForSuite(suites, String parentSuiteId) {
        List<Integer> result = []
        suites.value.each {
            if (it.parent && it.parent.id == parentSuiteId) {
                result << it.id.toInteger()
                result += getNextLevelsForSuite(suites, it.id.toString())
            }
        }
        return result
    }

}
