package framework.runweb.domedit

import above.RunWeb
import wss.pages.element.MetaElement

class UtDomEdit extends RunWeb {

    def test() {

        setup('vdiachuk', 'DOM Edit functionality unit test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test dom edit element add node ',
                 'logLevel:info'])
        tryLoad("homepage")
        assert !verifyElement("//p[contains(text(),'EXTERNAL added element')]")
        assert addElementToCurrentPage("p", "EXTERNAL added element")
        assert verifyElement("//p[contains(text(),'EXTERNAL added element')]")

        //adding to element
        assert !verifyElement("//span[contains(text(),'EXTERNAL added element')]")
        assert addElementToCurrentPage("span", "EXTERNAL added element", find("//head"))
        assert verifyElement("//span[contains(text(),'EXTERNAL added element')]")

        //adding attribute
        assert !getAttribute(find("//footer"), "sampleattribute")
        assert addAttributeToElement(find("//footer"), "sampleattribute", "samplevalue")
        assert getAttribute(find("//footer"), "sampleattribute") == "samplevalue"

        //mark product page

        assert tryLoad("https://www.dev.webstaurantstore.com/regency-24-x-54-14-gauge-stainless-steel-floor-drain-grate/600FT2454SS.html")
        assert isPageMarked() == false
        assert markPage()
        assert isPageMarked() == true

        //mark category page
        assert tryLoad("https://www.dev.webstaurantstore.com/3425/bus-tubs-and-bus-boxes.html")
        assert isPageMarked() == false
        assert markPage()
        assert isPageMarked() == true
        getWebDriver().navigate().refresh()
        assert isPageMarked() == false

    }
}
