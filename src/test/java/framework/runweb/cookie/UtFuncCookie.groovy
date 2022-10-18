package framework.runweb.cookie

import above.RunWeb
import org.openqa.selenium.WebDriver

class UtFuncCookie extends RunWeb {

    def test() {

        setup('vdiachuk', 'Click functionality unit test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test click jsClick ',
                 'logLevel:info'])

        String cookieName = "cook1"
        String cookieValue = "Value of cook1"


        tryLoad("homepage")
        assert !getCookie(cookieName)
        assert setCookie(cookieName, cookieValue)
        assert verifyCookie(cookieName)
        assert !verifyCookie(cookieName + "FAKE")
        assert getCookie(cookieName) == cookieValue
        assert removeCookie(cookieName)
        assert !verifyCookie(cookieName)
        assert !getCookie(cookieName)
        assert setCookie(cookieName, cookieValue)
        assert getCookie(cookieName)
        assert deleteAllCookies()
        assert !getCookie(cookieName)
        assert !verifyCookie(cookieName)


    }
}
