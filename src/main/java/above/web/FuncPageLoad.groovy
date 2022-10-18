package above.web

import above.RunWeb
import org.openqa.selenium.WebDriver
import wss.pages.element.modal.GDPRModal

/**
 *      Web Page Handling
 *      kyilmaz, vdiachuk
 */
class FuncPageLoad {

    RunWeb r = run()
    // WebDriver driver = r.getWebDriver() (!) getting driver inside method if needed - to avoid null if driver reinstating

    Integer tryTimes = 5
    Integer delayMilliseconds = 3500

    /** Get page source code */
    String getPageSource() {
        r.logDebug "Getting Page source.."
        WebDriver driver = r.driverStorage.get()
        if (!driver) {
            return null
        }
        try {
            return driver.getPageSource()
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not Page source", e)
            return null
        }
    }

    /** Get visible page content */
    String getPageText() {
        r.logDebug "Getting Page text.."
        return r.getText('//body')
    }

    /** Load page with wait */
    boolean loadPageAndWait(String url) {
        r.logDebug "Loading and waiting for the url: $url"
        if (loadPage(url)) {
            return r.waitForPage()
        }
        return false
    }

    /** Load page */
    boolean loadPage(String url) {
        r.log "Loading Page: $url"
        WebDriver driver = r.getWebDriver()
        if (!driver) {
            return false
        }
        GDPRModal gdprModal = new GDPRModal()
        try {
            driver.get(url)
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not load Page, url: $url", e)
            return false
        }
        //Temporary solution for Cookie Warning modal
        return gdprModal.acceptModalIfPresent()
        // return true

    }

    /**
     * Check if current URL = url and navigate if needed
     */
    boolean goOrStay(String url) {
        r.logDebug "Go or Stay to url: $url"
        WebDriver driver = r.getWebDriver()
        if (!(driver.currentUrl == url)) {
            return tryLoad(url)
        } else {
            return true
        }
    }

    /**
     * Check for page errors - and return error message
     */
    String getPageError() {
        r.logDebug "Getting Page errors.."
        String body = r.getPageSource() // TODO: IE compatibility
        if (body == null) {
            return 'Page Source code is null'
        }
        if (body) {
            for (msg in r.webProject.pageErrors) {
                if (body.contains(msg)) {
                    return msg
                }
            }
        }
        return null
    }

    /**
     * Check for page errors - returns Boolean
     */
    boolean isPageError() {
        r.logDebug "Checking is Page contains known errors.."
        String body = r.getPageSource() // TODO: IE compatibility
        if (body == null) {
            return true
        }
        return r.webProject.pageErrors.any { it -> body?.contains(it) }
    }

    /**
     * Try to load page with known issues handling
     * (!) Waits until the page be fully loaded
     * @param targetUrl - page name like 'homepage', 'cart', etc.
     *            -- page path like '/restaurant-equipment.html'
     *            -- full URL like 'https://test.webstaurantstore.com/restaurant-equipment.html'
     *               (!) be careful providing full URL make sure you create it based on getUrl() because of
     *               protocol, domain, and environment handling
     * @param xpathOrElement - click element
     *            -- targetUrl can be '*' for any target page expected
     *            -- going to be only one try to click
     * @return false on any fail and sets lastIssue description before the returning false
     *
     * @author akudin
     */
    boolean tryLoad(String targetUrl = null, xpathOrElement = null) {
        // checking targetUrl
        if (targetUrl == '*' && !xpathOrElement) {
            r.lastIssue = "targetUrl = '*' is allowed only with xpathOrElement parameter"
            r.log(r.lastIssue, r.console_color_red)
            return false
        }
        if (!targetUrl || (!targetUrl.startsWith('http') && !targetUrl.startsWith('file:///') &&
                !targetUrl.contains(':/') && !targetUrl.contains(':\\') && targetUrl != '*')) {
            targetUrl = r.getUrl(targetUrl)
        }
        if (targetUrl.endsWith('.com')) {
            targetUrl += '/'
        }
        targetUrl = targetUrl.replace('\\', '/')

        r.lastIssue = 'Page loading issue'
        boolean result = false

        // try to reach the page tryTimes times
        String currentUrl
        for (i in (1..tryTimes)) {
            // loading page
            if (xpathOrElement) {
                if (i == 1) {
                    r.log "Clicking:     $xpathOrElement"
                    if (!clickForPageLoad(xpathOrElement)) {
                        break
                    }
                    r.log "Waiting for:  $targetUrl"
                } else {
                    r.lastIssue = "Page was not loaded by one click on: $xpathOrElement"
                    break
                }
            } else if (!loadPageAndWait(targetUrl)) {
                r.log '(!) Page not loaded'
                if (i == tryTimes) {
                    r.lastIssue = "Can't load: $targetUrl"
                    break
                } else {
                    r.log("-- next try after: $delayMilliseconds seconds...")
                    sleep(delayMilliseconds)
                    continue
                }
            }

            // getting loaded URL
            String currentUrlNotDecoded = r.getCurrentUrl()
            try {
                currentUrl = URLDecoder.decode(currentUrlNotDecoded, "UTF-8").toString()
            } catch (Exception e) {
                r.addIssueTrackerEvent("Can not load url: [$currentUrlNotDecoded] - decoding url issue", e)
                return false
            }
            r.log "Got loaded:   $currentUrl"

            // correcting params with http://
            if (targetUrl.startsWith('https://')) {
                currentUrl = currentUrl.replace('=http://', '=https://')
                currentUrlNotDecoded = currentUrlNotDecoded.replace('=http://', '=https://')
            }
            // handling loaded page issues and possible redirects
            if (targetUrl == '*' || (currentUrl == targetUrl) || (currentUrlNotDecoded == targetUrl) || (currentUrl == targetUrl + '/') ||
                    currentUrl.startsWith(targetUrl) || justMinorRedirect(targetUrl, currentUrl)) {

                // checking page error
                String err = getPageError()
                if (err) {
                    r.log "Got [$err] error message on $currentUrl"
                    if (i == tryTimes) {
                        r.lastIssue = "$err on $currentUrl"
                        break
                    } else {
                        sleep(delayMilliseconds)
                        continue
                    }
                }
                // success load
                r.lastIssue = ''
                result = true
                break
            } else {
                // wrong redirect
                r.lastIssue = "Wrong redirect from $targetUrl to $currentUrl"
                break

            }
        }
        // checking result
        if (!result) {
            r.log r.lastIssue
            if (r.autoTcReady) {
                r.report(r.currentTcId, r.lastIssue, false, true)
            }
        }
        return result
    }

    /** Check for minor redirects */
    private boolean justMinorRedirect(String url1, String url2) {
        r.logDebug "Checking is Minor redirect.."
        url1 = url1.toLowerCase()
        url2 = url2.toLowerCase()
        def result = false
        for (it in r.webProject.redirects) {
            if (url1.contains(it[0].toLowerCase()) && url2.contains(it[1].toLowerCase())) {
                result = true
                break
            }
        }
        return result
    }

    private boolean clickForPageLoad(xpathOrElement) {
        if (!r.markPage()) {
            r.addIssueTrackerEvent("Can not mark page")
        }

        // waiting for the element
        if (!r.waitForElementClickable(xpathOrElement)) {
            r.lastIssue = "Failed: waitForElementClickable() returned [false] for: $xpathOrElement"
            return false
        }

        // clicking the element
        if (!r.click(xpathOrElement)) {
            r.lastIssue = "Failed: click() returned [false] for: $xpathOrElement"
            return false
        }

        // dummy page js/click/etc slows waiting
        sleep(1500)

        if (r.isPageMarked()) {
            r.lastIssue = "Page is marked(not expected) after clicking on: $xpathOrElement"
            return false
        }

        // success
        r.waitForPage()
        return true
    }
}
