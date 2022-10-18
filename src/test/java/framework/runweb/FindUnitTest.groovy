package framework.runweb

import above.RunWeb
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

class FindUnitTest extends RunWeb {


    // Test
    def test() {

        setup('vdiachuk', 'Find Element Unit test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test find findElement',
                 "tfsTcIds:265471", 'logLevel:info'])


        RunWeb r = run()
        WebDriver driver = r.getWebDriver()


        String searchBtnXpath = "//button[@value='Search']"
        String menuItemXpath = "//a[contains(@class,'ategory-item')]"
        String parentMenuElementXpath = "//li[@id='product-categories']"


       tryLoad("homepage")



        WebElement element = find(parentMenuElementXpath)
        assert find(searchBtnXpath) == driver.findElement(By.xpath(searchBtnXpath))
        assert findElements(menuItemXpath) == driver.findElements(By.xpath(menuItemXpath))
        assert findElementsInElement(parentMenuElementXpath, menuItemXpath) == element.findElements(By.xpath("." + menuItemXpath))
        assert findInElement(parentMenuElementXpath, menuItemXpath) == element.findElement(By.xpath("." + menuItemXpath))


    }


}
