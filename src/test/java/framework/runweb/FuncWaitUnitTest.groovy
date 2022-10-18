package framework.runweb

import above.RunWeb

class FuncWaitUnitTest extends RunWeb{

    def tcIds = [265465] // debugging testcase

    // Test
    def test() {

        setup('kyilmaz', 'RunWeb - FuncWait Unit Test',
              ['product:wss', 'tfsProject:Webstaurant.StoreFront', "tfsTcIds:${tcIds.join(',')}",
               'keywords:unit test', 'logLevel:debug'])

        log 'Testing FuncWait'
        start()
        closeBrowser()

        log 'Completed testing for FuncWait.'
    }

    def start() {
        existingElement()
        nonExistingElement()
        return this
    }

    def existingElement() {
        openAndResize(getUrl('homepage'), 500, 800)
        def logoXpath = '//a[@aria-label="WebstaurantStore"]'
        check(tcIds, "Testing waitForPage", waitForPage(10))
        check(tcIds, "Testing waitForElementVisible", waitForElementVisible(logoXpath))
        check(tcIds, "Testing waitForElementClickable", waitForElementClickable(logoXpath))
        check(tcIds, "Testing waitForElement", waitForElement(logoXpath))
        // Just testing to see if this doesn't fail
        sleep(100)
    }

    def nonExistingElement() {
        openAndResize(getUrl('homepage'), 500, 800)
        def fakeXpath = '//a[@automation-label="ThisIsAnExtremelyFakeLabelThatWillNeverBeOntheHomepage"]'
        check(tcIds, "Testing waitForPage.", waitForPage(10))
        check(tcIds, "Testing waitForElementVisible for non-existing element.",
              !waitForElementVisible(fakeXpath, 2))
        check(tcIds, "Testing waitForElementClickable for non-existing element.",
              !waitForElementClickable(fakeXpath, 2))
        check(tcIds, "Testing waitForElement for non-existing element.", !waitForElement(fakeXpath,
                                                                                         2))
        // Just testing to see if this doesn't fail
        sleep(100)
    }
}
