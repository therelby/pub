package above.allrun

import above.allrun.helpers.RunSetup

/**
 *      Run Setup
 */
abstract class CaRunSetup extends CbRunStatistics {

    /**
     * Actual setup() based on Map options
     * Doc: https://tfs.clarkinc.biz/DefaultCollection/Automation%20Projects/_wiki/wikis/Automation-Projects.wiki/2449/setup()
     */
    void setup(Map options) {
        new RunSetup().setup(options)
    }


    /**
     * Old style compatibility
     */
    void setup(String clarkLogin, String testTitle, List<String> options = []) {
        new RunSetup().setup(clarkLogin, testTitle, options)
    }

}
