package above.web.helpers

import above.ConfigReader
import above.RunWeb
import all.Json
import all.Numbers
import all.VariableStorage
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

/**
 *      Grid Server Handling
 */

class WebDriverGrid {

    static String xpathBrowsers = "//div[@type='browsers' and @class='content_detail']"


    /** getFreeIosDeviceCapabilities() handling */
    /* CANCELED */
    synchronized static List tryToGetIosCaps(int tries = 10, int pause = 5000) {
        while (tries > 0) {
            tries--
            def caps = getFreeIosDeviceCapabilities()
            if (caps) {
                return caps
            }
            sleep(pause)
        }
        return null
    }


    /** Handling mobile browsers states */
    synchronized static List getFreeIosDeviceCapabilities() {

        List result = []
        List resultSet = []
        RunWeb r = run()

        r.log 'Getting free mobile browser capabilities from Grid Console by temporary Chrome driver...'
        WebDriver webDriver = openBrowserAndGetDriver(true)

        List<WebElement> icons = webDriver.findElements(By.xpath('//img[contains(@title, "iOS")]'))
        if (!icons) {
            return result
        }

        r.logDebug 'Got "title" attributes:'
        for (i in icons) {
            String title = r.getAttribute(i, 'title')
            r.logDebug title
            if (title) {
                List<String> tl = title.split(',')*.trim()
                if (tl.size() > 2) {
                    for (param in tl) {
                        List<String> par = param.split('=')*.trim()
                        if (par && par.size() == 2 && par[0] && par[1] &&
                                !par[0].startsWith('{server:') &&
                                par[0] != 'seleniumProtocol' &&
                                par[0] != 'maxInstances' &&
                                par[0] != 'platform' /*&& par[0] != 'udid'*/) {
                            if (par[1].endsWith('}')) { par[1] = par[1].replace('}', '') }
                            if (par[1] == 'true') { par[1] = true }
                            result << par
                        }
                    }
                }
            }
            if (result) {
                resultSet.add(result)
                result = []
            }
        }

        webDriver.close()
        webDriver.quit()
        webDriver = null

        return resultSet[Numbers.random(1, resultSet.size()) - 1]
    }


    /** Update browser versions list **/
    synchronized static updateBrowserVersions() {

        RunWeb r = run()
        r.log "WebDriverFactory: Updating browser versions list..."

        // grabbing versions from the console
        try {

            WebDriver webDriver = openBrowserAndGetDriver()

            Map result = [
                    timeStamp: new Date().getTime(),
                    readableTime: new Date().format('MM/dd/yyyy HH:mm:ss'),
                    runId: r.runId,
                    chromeVersions: [],
                    safariVersions: [],
                    edgeVersions: []
            ]

            // parsing
            def nodes = webDriver.findElements(By.xpath(xpathBrowsers))
            nodes.each {
                String img = it.findElement(By.xpath(".//p[2]/img[1]")).getAttribute('src').trim()
                String ver = it.findElement(By.xpath(".//p[2]")).getText().replaceFirst('v:', '').trim()
                if (ver) {
                    if (img.endsWith('chrome.png')) {
                        result.chromeVersions << ver
                    }
                    if (img.endsWith('safari.png')) {
                        result.safariVersions << ver
                    }
                    if (img.endsWith('MicrosoftEdge.png')) {
                        result.edgeVersions << ver
                    }
                }
            }
            result.chromeVersions = result.chromeVersions.unique().sort()
            result.safariVersions = result.safariVersions.unique().sort()
            result.edgeVersions = result.edgeVersions.unique().sort()

            r.log 'Got versions:'
            r.log result

            // killing temporary driver
            webDriver.quit()
            webDriver = null

            // checking results
            if (result.chromeVersions.size() > 0) { // } && result.safariVersions) {
                r.log 'Got the correct result for Chrome' // TODO: update for other browsers
                r.log 'Saving new data...'
                VariableStorage.setData('frameworkBrowserVersions-do-no-touch-please', result, true)
                return result
            } else {
                // TODO: inform the framework persons
                r.log 'Returning null because of incorrect result'
                return null
            }

        } catch (Exception e) {
            r.log "WebDriverFactory: Can not read browser versions on the Grid console page ${ConfigReader.get('gridConsole')}"
            r.log e.getStackTrace()
            r.log "WebDriverFactory: Continuing to use the old browsers list"
            // TODO: inform the framework persons
            return null
        }
    }


    // Get WebDriver
    synchronized static private WebDriver openBrowserAndGetDriver(remote = false) {
        String browserName = null
        if (run().isServerRun() || remote) {
            browserName = 'remotechrome'
        } else {
            browserName = 'chrome'
        }
        WebDriver webDriver = run().getDriver(browserName, 0)
        webDriver.get(ConfigReader.get('gridConsole'))
        return webDriver
    }

}
