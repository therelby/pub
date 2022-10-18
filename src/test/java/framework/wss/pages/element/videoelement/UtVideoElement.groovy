package framework.wss.pages.element.videoelement

import above.RunWeb
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import wss.pages.element.VideoElement


class UtVideoElement extends RunWeb {

    def test() {

        setup('vdiachuk', 'Toppers Element unit test  | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test toppers element',
                 "tfsTcIds:265471",
                 'logLevel:info'])


        tryLoad('https://www.dev.webstaurantstore.com/acopa-8-oz-round-bright-white-fluted-porcelain-souffle-creme-brulee-dish-case/303A8BWSOUF.html')
        log "click: " + click("//a[contains(@class,'loadProductVideo')]")
        WebDriver driver = getWebDriver()
        def iframeElements = driver.findElements(By.tagName("iframe"))
        log "iframes.size: " + iframeElements.size()

        driver.switchTo().frame(findElements("//iframe")[0])
        VideoElement videoElement = new VideoElement()
         log "videoElement.checkVideoError: " + videoElement.checkVideoError('//div[@id="sub-frame-error"]')

        /*    log getPageSource()
            driver.switchTo().defaultContent()
            log "111111111"
            driver.switchTo().frame(driver.findElements(By.tagName("iframe"))[1])
            log getPageSource()
            driver.switchTo().defaultContent()
            log "222222222"
            driver.switchTo().frame(driver.findElements(By.tagName("iframe"))[2])
            driver.switchTo().defaultContent()
            log "33333"
            log getPageSource()
            driver.switchTo().frame(driver.findElements(By.tagName("iframe"))[3])
    */
    }
}
