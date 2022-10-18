package framework.runweb.verify

import above.RunWeb
import org.openqa.selenium.WebElement

/* Unit test for FuncVerify class
 * @vdiachuk
*/

class UtVerify extends RunWeb {
    def test() {

        setup('vdiachuk', 'FuncVerify elements unit test | Framework Self ' +
                'Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords: unit test verify visible clickable elements',
                 "tfsTcIds:265471", 'logLevel:info'])


        String url = 'https://www.dev.webstaurantstore.com/search/plastic.html?order=xyz'
        String hiddenElementXpath = "//span[contains(text(),'Tabletop Disposables')]"
        String openMoreXpath = "//a[@class = 'moreFewer']"
        String fakeElementXpath = "//a[@class = 'fake-class']"

        tryLoad(url)
        log "verifyElement(hiddenElementXpath): " + verifyElement(hiddenElementXpath)
        log "elementVisible(hiddenElementXpath): " + elementVisible(hiddenElementXpath)
        log "elementClickable(hiddenElementXpath): " + elementClickable(hiddenElementXpath)

        assert verifyElement(hiddenElementXpath)
        assert elementVisible(hiddenElementXpath) == false
        assert elementClickable(hiddenElementXpath) == false

        click(openMoreXpath)
        log "=="
        log "==Visible, present and clickable"
        log "verifyElement(hiddenElementXpath): " + verifyElement(hiddenElementXpath)
        log "elementVisible(hiddenElementXpath): " + elementVisible(hiddenElementXpath)
        log "elementClickable(hiddenElementXpath): " + elementClickable(hiddenElementXpath)
        assert verifyElement(hiddenElementXpath)
        assert elementVisible(hiddenElementXpath) == true
        assert elementClickable(hiddenElementXpath) == true

        log "=="
        log "Checking for element not present"
        log "verifyElement(fakeElementXpath): " + verifyElement(fakeElementXpath)
        log "elementVisible(fakeElementXpath): " + elementVisible(fakeElementXpath)
        log "elementClickable(fakeElementXpath): " + elementClickable(fakeElementXpath)


// Verify element in Element
        tryLoad("https://www.dev.webstaurantstore.com/search/plastic.html?order=xyz&category=13949")
        String divSearchInXpath = "//div[@class='filter-list__item ']"
        WebElement elementIn = find(divSearchInXpath)
        String elementSearchForXpath = "//input[@checked]"
        String elementNotPresent = "//input[@checkedFake]"
       /* log "verifyElementInElement(divSearchInXpath,elementSearchForXpath): " + verifyElementInElement(divSearchInXpath, elementSearchForXpath)
        log "verifyElementInElement(elementIn,elementSearchForXpath): "+ verifyElementInElement(elementIn,elementSearchForXpath)
        log "verifyElementInElement(verifyElementInElement(divSearchInXpath,elementNotPresent)): " + verifyElementInElement(divSearchInXpath, elementNotPresent)
        log "verifyElementInElement(elementNotPresent,elementSearchForXpath): " + verifyElementInElement(elementNotPresent, elementSearchForXpath)

    */
        assert verifyElementInElement(divSearchInXpath, elementSearchForXpath)
        assert verifyElementInElement(elementIn,elementSearchForXpath)
        assert verifyElementInElement(divSearchInXpath, elementNotPresent) == false
        assert verifyElementInElement(elementNotPresent, elementSearchForXpath) == false

    }

}
