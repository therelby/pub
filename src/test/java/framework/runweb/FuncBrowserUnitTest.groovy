package framework.runweb

import above.RunWeb
import above.web.FuncBrowser

class FuncBrowserUnitTest extends RunWeb {

    def tcId = [265465]
    // debugging testcase
    def homepage = "https://dev.webstaurantstore.com"

    // Test
    def test() {
        setup('kyilmaz', 'RunWeb - FuncWait Unit Test',
              ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test',
               "tfsTcIds:${tcIds.join(',')}", 'logLevel:debug'])
        log 'Testing FuncBrowser'
        start()
        closeBrowser()
        log 'Completed testing for FuncBrowser.'
    }

    def start() {
        testRegularOpen()
        testSwitchTab()
        testCloseTab()
        testBlankOpen()
        testOpenWithResize()
        testOpenWithResizeToDevice()
        testMultipleOpen()
        return this
    }

    def testSwitchTab() {
        log 'Tab switch testing'
        List<String> urls = ['https://www.dev.webstaurantstore.com/',
                             'https://www.test.webstaurantstore.com/',
                             'https://www.dev.webstaurantstore.com/new/']
        openBrowser(urls[0])
        newTab(urls[1])
        newTab(urls[2])
      // Here were check switch to tab by index functionality - removed from Framework
        closeTab()
        closeTab()
    }

    def testCloseTab() {
        log 'Tab close testing'
        openBrowser('https://dev.webstaurantstore.com')
        newTab(getUrl('homepage'))
        newTab('https://dev.webstaurantstore.com')
        newTab('https://dev.webstaurantstore.com')
        for (i in 0..10) {
            closeTab()
        }
        def numTabs = getTabCount()
        check(tcId, "The window did not close, despite using closeTab() more times than tabs.", isBrowserOpen())
        check(tcId, "After using closeTab() many times, the remaining number of tabs is ${numTabs}. Expected 1.",
              numTabs == 1)
        def url = getCurrentUrl()
        check(tcId, "After closing last tab, landed on the page: ${url}. Expected: ${webProject.pages.default}",
              url == webProject.pages.default)
    }

    def testRegularOpen() {
        log 'Regular Open'
        openBrowser(getUrl('homepage'))
        check(tcId, "Browser not opened", isBrowserOpen())
    }

    def testBlankOpen() {
        log 'Blank Open'
        openBrowser()
        check(tcId, "Browser not opened", isBrowserOpen())
    }

    def testOpenWithResize() {
        log 'Open with resize'
        def targetSizes = [[600, 600], [700, 700], [800, 800], [1000, 800]]
        for (targetSize in targetSizes) {
            openAndResize(getUrl('homepage'), targetSize[0], targetSize[1])
            check(tcId, "Browser not opened", isBrowserOpen())
            List<Integer> size = getSizeOfViewport()
            def targetSizeString = targetSize[0] + 'x' + targetSize[1]
            check(tcId,
                  "Browser resized to approximately ${targetSizeString}. Size: ${size[0]}x${size[1]}. Expected: ${targetSizeString}",
                  Math.abs(size[0] - targetSize[0]) < 5 && Math.abs(size[1] - targetSize[1]) < 5)
        }
    }

    def testOpenWithResizeToDevice() {
        log 'Open to device dimension'
        def deviceNames = ["desktop", "tablet", "mobile"]
        for (deviceName in deviceNames) {
            openAndResizeToDevice(getUrl('homepage'), deviceName)
            check(tcId, "Browser not opened", isBrowserOpen())
            List<Integer> size = getSizeOfViewport()
            try {
                List<Integer> targetSize = FuncBrowser.deviceResolutions[deviceName]
                def targetSizeString = targetSize[0] + 'x' + targetSize[1]
                check(tcId,
                      "Browser resized to approximately ${targetSizeString}. Size: ${size[0]}x${size[1]}. Expected: ${targetSizeString}",
                      Math.abs(size[0] - targetSize[0]) < 5 && Math.abs(size[1] - targetSize[1]) < 5)
            } catch (e) {
                log e
                check(tcId, "Could not get window dimensions", false)
            }
        }
    }

    def testMultipleOpen() {
        log 'Multiple open'
        openBrowser()
        openBrowser()
        openBrowser(getUrl('homepage'))
        def logoXpath = '//a[@aria-label="WebstaurantStore"]'
        find(logoXpath)
        waitForElementClickable(logoXpath)
    }

}
