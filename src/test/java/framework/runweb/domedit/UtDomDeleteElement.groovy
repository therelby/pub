package framework.runweb.domedit

import above.RunWeb
import org.openqa.selenium.WebElement

class UtDomDeleteElement extends RunWeb {


    def test() {

        setup('vdiachuk', 'DOM Edit functionality - Delete Element unit test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test dom edit element node webelement delete remove ',
                 'logLevel:info'])
        tryLoad("https://www.dev.webstaurantstore.com/ask.html?item=596KLF68%20%20%20%20LFT#askAQuestion")
        String xpath = "//div[contains(@class,'help--bottom-border')]"
        WebElement element = find(xpath)
        assert verifyElement(xpath)
        assert deleteElementFromCurrentPage(element)
        assert !verifyElement(xpath)

        tryLoad("https://www.dev.webstaurantstore.com/ask.html?item=596KLF68%20%20%20%20LFT#askAQuestion")
        assert verifyElement(xpath)
        assert deleteElementFromCurrentPage(xpath)
        assert !verifyElement(xpath)

        // deleting not existing elements
        assert deleteElementFromCurrentPage(null) == false
        assert deleteElementFromCurrentPage("//div[contains(@class,'help--bottom-borderFAKE')]") == false


    }
}
