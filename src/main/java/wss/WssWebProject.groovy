package wss

import above.RunWeb
import above.web.helpers.WebProject

/**
 *      WebstaurantStore Web Project Basics
 * @author akudin
 */
class WssWebProject implements WebProject {

    String testEnv = 'dev'

    final private Map envs = [
            dev      : 'www.dev',
            test     : 'www.test',
            test2    : 'www.test2',
            preflight: 'www.preflight',
            prod     : 'www'
    ]

    final private Map environmentPrefixes = [
            dev      : 'dev',
            test     : 'test',
            test2    : 'test2',
            preflight: 'preflight',
            prod     : ''
    ]

    final String productDomain = 'webstaurantstore.com'

    List<String> pageErrors = ['Sorry about that', 'Give us a minute', 'Sorry, but this page doesn\'t exist',
                               'Access Denied', 'HTTP ERROR', '503 Service Unavailable']

    final List redirects = [
            ['/newwebadmin/', '/newwebadmin/main.cfm'],
            ['adminportal', 'AccessDenied'],
            ['/feature/', '/feature/'],
            ['specializedpage.cfm?index=', '/feature/'],
            ['/vendor', '/vendors'],
            ['/specializedpage.cfm', '/specializedpage.cfm'],
            ['/myaccount/?logout=Y', '/'],
            ['//adminportal', '//auth'],
            ['/x.html', '/'],
            ['?login', '/'],
            ['/ourbrands', '/ourbrands'],
            ['/viewcart.cfm', '/cart/'],
            ['/additemtocart.cfm', '/cart/']
    ]

    private Map pages = [
            homepage           : "https://${-> envs[testEnv]}.$productDomain/",
            hp                 : "https://${-> envs[testEnv]}.$productDomain",
            adminportal        : "https://adminportal.${-> environmentPrefixes[testEnv]}.$productDomain",
//            adminportal        : "https://adminportal-upgrade-dot-net.${-> environmentPrefixes[testEnv]}.devdocker.backends.tech",
            catalogapi         : "https://catalog.${-> environmentPrefixes[testEnv]}.$productDomain",
            catalogadminapi    : "https://catalog-admin.${-> environmentPrefixes[testEnv]}.$productDomain",
            catalogauthoringapi: "https://catalog-authoring-api.${-> environmentPrefixes[testEnv]}.$productDomain",
            shoppingcartapi    : "https://shoppingcart.${-> environmentPrefixes[testEnv]}.$productDomain",
            taxserviceapi      : "https://taxservice.${-> environmentPrefixes[testEnv]}.clarkinc.biz",
            sitemetadataapi    : "https://sitemetadata-api.${-> environmentPrefixes[testEnv]}.$productDomain",
            chat               : "https://chat.${-> environmentPrefixes[testEnv]}.$productDomain",
            userapi            : "https://${-> envs[testEnv]}.$productDomain/newwebadmin/qaautomation:userapi",
            partnersapi        : "https://partners-api.${-> environmentPrefixes[testEnv]}.clarkinc.biz",
            partnersadminapi   : "https://partners-admin-api.${-> environmentPrefixes[testEnv]}.clarkinc.biz",
            transactionsapi    : "https://transactions-api.${-> environmentPrefixes[testEnv]}.clarkinc.biz",
            tokenizer          : "https://tokenizer.${-> environmentPrefixes[testEnv]}.clarkinc.biz",
            webadmin           : "https://${-> envs[testEnv]}.$productDomain/newwebadmin/",
            myaccount          : "https://${-> envs[testEnv]}.$productDomain/myaccount/",
            sp                 : "https://${-> envs[testEnv]}.$productDomain/specializedpage.cfm?index=%spId&${wssCacheUpdateParam}",
            cart               : "https://${-> envs[testEnv]}.$productDomain/cart/",
            support            : "https://${-> envs[testEnv]}.$productDomain/ask.html",
            viewinfo           : "https://${-> envs[testEnv]}.$productDomain/viewinfo.cfm",
            shippingAndBilling : "https://${-> envs[testEnv]}.$productDomain/shipping-billinginfo.cfm",
            kitchenDash        : "https://${-> envs[testEnv]}.$productDomain/kitchendash#/kitchendash",
            default            : "about:blank"
    ]


    /*

        OUT OF THE TEMPLATE CUSTOM WSS PRODUCT STUFF
        -- doesn't use by RunWeb stuff
        -- belongs the WSS product only

     */

    public static String wssCacheUpdateParam = 'forcecacheupdate=1'

    RunWeb r = run()


    /**
     * Check Forced Cache Update param in current URL
     */
    def checkFcu(Boolean fcu) {
        def url = r.getCurrentUrl()
        if (!url) {
            r.log("URL found wasL $url. Could not toggle the forcecacheupdate.")
            return
        }
        if (fcu) {
            if (!url.contains(wssCacheUpdateParam)) {
                r.tryLoad(run().addParamToUrl(url, wssCacheUpdateParam))
                r.log("Force cache update added to URL.")
            }
        } else {
            if (url.contains(wssCacheUpdateParam)) {
                r.tryload(url.replaceAll(wssCacheUpdateParam, '').replaceAll('\\&\\&', '\\&'))
                r.log("Force cache update removed from URL.")
            }
        }
    }

    /**
     *  Converting url to url with "storefront" parameter
     *
     * @param url to convert to storefront
     * @return url String with storefront parameter at the beginning of the url
     * ex. https://storefront.dev.webstaurantstore.com/52705/reach-in-refrigerators.html
     */
    String convertToStorefrontUrl(String url) {
        r.logDebug("Converting url: $url to storefront url")
        try {
            if (url.contains("/storefront.")) {
                r.log "Url Already contains storefront parameter: $url"
                return url
            }
            if (!url || !url.contains("www.")) {
                r.log "Can not convert to storefront url: $url, env: ${-> envs[testEnv]}"
                return null
            }
            return url.replace("www", "storefront")
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not convert to storefront url: $url - Exception", e)
            return null
        }
    }

    /**
     * Get specialized page URL
     */
    String getSpUrl(spId) {
        if ((!spId instanceof String)) {
            spId = spId.toString()
        }
        return r.getUrl('sp').replace('%spId', spId)
    }


}
