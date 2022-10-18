package framework.runweb.funcread

import above.RunWeb

class UtFuncReadGetAllAttributesSafe extends RunWeb {


    def test() {

        setup('vdiachuk', 'Func Read, RunWeb, getAllAttributesSafe Unit test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 "tfsTcIds:265471",
                 'keywords:unit test all attributes safe funcread',
                 'logLevel:info'])

        tryLoad('/refrigeration-equipment.html')
        String cartXpath = "//a[@href='/viewcart.cfm']"
        String xpathNoElement =  "//a[@href='/FAKE.cfm']"

        // Element Present
        //// By xpath
        Map attributesPresent = getAllAttributesSafe(cartXpath)
        assert attributesPresent.get("class").size()>2
        assert attributesPresent.get("data-testid") == 'cart-nav-link'
        //// By WebElement
        Map attributesPresentByWebElement = getAllAttributesSafe(find(cartXpath))
        log attributesPresentByWebElement
        assert attributesPresentByWebElement.get("class").size()>2
        assert attributesPresentByWebElement.get("data-testid") == 'cart-nav-link'

        //Element NOT Present
        //// By xpath
        Map attributesNotPresent = getAllAttributesSafe(xpathNoElement)
        assert attributesNotPresent== [:]
        assert attributesNotPresent.get("data-testid") == null
        //// By WebElement
        Map attributesNotPresentByWebElement = getAllAttributesSafe(find(xpathNoElement))
        assert attributesNotPresentByWebElement == [:]
        assert attributesNotPresentByWebElement.get("data-testid") == null
    }
}
