package above.web.helpers

import above.ConfigReader
import above.RunWeb

/**
 *      Browsers Handling Tools
 *      @author akudin
 */

class Browsers {


    /** Server Browsers and Versions */
    static List<Map> getServerBrowsersVersions() {

        RunWeb r = run()

        List<String> browserVersions = ConfigReader.get('browserVersions').split(',')//*.split(':')
        r.logDebug 'browserVersions:'
        r.logDebug browserVersions

        List<String> browserListForServerRunning = ConfigReader.get('browserListForServerRunning').split(',')*.trim().sort()
        r.logDebug 'browserListForServerRunning:'
        r.logDebug browserListForServerRunning

        List<Map> result = []

        browserListForServerRunning.each { String browser ->
            int minVersion
            String ver = browserVersions.find { it.startsWith(browser.replaceFirst('remote', '') + ':')}
            if (ver) {
                minVersion = ver.split(':')[1].toInteger()
            } else {
                minVersion = 0
            }
            for (int i = 0; i >= minVersion; i--) {
                result << [
                        browser: browser,
                        versionOffset: i
                ]
            }
        }

        r.logDebug 'result:'
        r.logDebug result
        return result
    }


    /** Default Browser */
    static String getDefaultBrowser() {
        return ConfigReader.get('defaultBrowser')
    }

}
