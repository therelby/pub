package above.web

import above.RunWeb

/**
 *      URL Stuff That Doesn't Need WebDriver
 * @author akudin vdiachuk
 */
class FuncUrlTools {

    RunWeb r = run()


    /**
     * Get URL by page name or path using the correct domain and current environment
     * @param pageNameOrPath -- page name like 'homepage', 'cart', etc.
     *                       -- page path like '/restaurant-equipment.html'
     */
    String getUrl(String pageNameOrPath) {
        r.logDebug "Getting url of page: $pageNameOrPath"
        if (!pageNameOrPath) {
            pageNameOrPath = 'homepage'
        }
        def url
        if (r.webProject.pages.containsKey(pageNameOrPath)) {
            url = r.webProject.pages[pageNameOrPath]
        } else {
            if (pageNameOrPath.startsWith('/')) {
                pageNameOrPath = pageNameOrPath.replaceFirst('/', '')
            }
            url = r.webProject.pages.homepage + pageNameOrPath
        }
        return url
    }

    /** Get URL with no end / */
    String getUrlRaw(String pageNameOrPath) {
        String url = getUrl(pageNameOrPath)
        if (url != null && url.size() > 1 && url.endsWith('/'))
            url = url.substring(0, url.size()-1)
        return url
    }


    /** Check if current URL = url and navigate if needed */
    Boolean goOrStay(String url) {
        r.logDebug "Execution goOrStay on url: $url"
        try {
            if (!r.driverStorage.get() || !(r.getCurrentUrl() == url)) {
                return r.tryLoad(url)
            } else {
                return true
            }
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not goOrStay to url: $url", e)
            return false
        }
    }

}
