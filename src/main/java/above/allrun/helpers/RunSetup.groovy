package above.allrun.helpers

import above.Run

/**
 *          Run Setup Stuff
 */

class RunSetup {

    final private Run r = run()

    /**
     * Actual setup() based on Map options
     * Doc: https://tfs.clarkinc.biz/DefaultCollection/Automation%20Projects/_wiki/wikis/Automation-Projects.wiki/2449/setup()
     */
    void setup(Map options) {
        if (!r.runId) {
            throw new Exception('Wrong script execution')
        }
        if (r.testAuthor) {
            throw new Exception('Second call of setup() is not allowed')
        }

        // setup options parsing
        new RunSetupProcessor().xSetupProcessor(options)

        // RunWeb direct inheritance requirement
        if (r.xTestConcept() == 'web' &&
                r.testClass.split('\\.').last().startsWith('Tc') &&
                r.getClass().getSuperclass().getName() != 'above.RunWeb')
            throw new Exception('Web test classes must extend RunWeb directly like: class TcMyClassName extends RunWeb {')
    }


    /**
     * Old style compatibility
     */
    void setup(String clarkLogin, String testTitle, List<String> options = []) {

        // creating Map from old style params
        Map ops = [:]
        ops.author = clarkLogin
        ops.title = testTitle
        options.each { String it ->
            if (it.contains(':')) {
                List ls = it.split('\\:')
                if (ops.containsKey(ls[0])) {
                    throw new Exception("Found duplicated setup() option: ${ls[0]}")
                }
                if (ls.size() == 2) {
                    ops[ls[0].trim()] = ls[1]
                } else {
                    ops[ls[0].trim()] = true
                }
            } else {
                ops[it.trim()] = true
            }
        }
        r.runRawSetupOptions = ops

        // calling actual setup()
        setup(ops)
    }

}
