package above.web

import above.RunWeb
import org.openqa.selenium.Cookie
import org.openqa.selenium.WebDriver
/**
 *      Cookies Handling
 *      vdiachuk kyilmaz akudin
 */
class FuncCookie {
    RunWeb r = run()
    // WebDriver driver = r.getWebDriver() (!) getting driver inside method if needed - to avoid null if driver reinstating


/**
 * Set cookie to browser
 *
 * @param name
 * @param value
 */
    boolean setCookie(String name, String value) {
        r.logDebug "Setting Cookie: $name to: $value"
        try {
            WebDriver driver = r.getWebDriver()
            driver.manage().addCookie(new Cookie(name, value))
            return true
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not set Cookie: $name, value: $value", e)
            return false
        }
    }

    /**
     * Confirms the existence of cookie
     *
     * @param name
     * @return
     */
    Boolean verifyCookie(String name) {
        try {
            r.logDebug "Verifying cookie: $name"
            WebDriver driver = r.getWebDriver()
            String cookieName = driver.manage().getCookieNamed(name)
            if (cookieName == null) {
                return false
            } else {
                return true
            }
        }catch(Exception e){
            r.addIssueTrackerEvent("Can not verify Cookie: [$name] - Exception", e)
            return null
        }
    }

    /**
     * Returns value of cookie
     *
     * @param name
     * @return
     */
    String getCookie(String name) {
        r.logDebug "Getting Cookie: $name"
        def value
        try {
            WebDriver driver = r.getWebDriver()
            value = driver.manage().getCookieNamed(name).getValue()
            return value
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not get Cookie: $name", e)
            return null
        }
    }

    /**
     * Removes specified cookie
     *
     * @param name
     */
    boolean removeCookie(String name) {
        r.logDebug "Removing Cookie: $name"
        try {
            WebDriver driver = r.getWebDriver()
            driver.manage().deleteCookieNamed(name)
            return true
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not remove Cookie: $name", e)
            return false
        }
    }

    /**
     * Removes All cookies
     *
     * @param name
     */
    boolean deleteAllCookies() {
        r.logDebug "Deleting All cookies.."
        try {
            WebDriver driver = r.getWebDriver()
            driver.manage().deleteAllCookies()
            //r.refresh()
            return true
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not Delete All Cookies", e)
            return false
        }
    }

}
