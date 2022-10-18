package framework.run.debug

import above.Run

class AHelper {

    void ask() {
        Run r = run()
        r.log("Current test browser version offset is ${r.testBrowserVersionOffset}")
    }

}
