package above.allrun.helpers

import above.Run
import java.security.Permission

/**
 *      Test Exit Event Handling
 *      @author akudin
 */
class RunSecurityManager extends SecurityManager {

    Run r = run()

    @Override
    void checkExit(int status) {

        // driver closing
        if (r.xTestConcept() == 'web' && r.driverStorage.get() && (!r.isLocalRun() ||
                (r.testBrowser.startsWith('remote') || r.testBrowser.startsWith('headless') ||
                r.testBrowser.startsWith('mobile') || r.testBrowser.startsWith('ios') ||
                r.testBrowser.startsWith('android')))) {
            r.log('RunSecurityManager: Closing browser...', r.console_color_yellow)
            r.closeBrowser()
        }

        /* TODO: framework refactoring
        // test break detection
        if (!r.runNormalExit && status == 0) {
            r.runFailed = true
            throw new Exception('Early test interruption detected')
        }*/
    }

    @Override
    void checkPermission(Permission perm) {
    }

}
