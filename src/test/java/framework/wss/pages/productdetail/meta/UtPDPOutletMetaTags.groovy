package framework.wss.pages.productdetail.meta

import above.RunWeb
import wss.pages.element.MetaElement

class UtPDPOutletMetaTags extends RunWeb {
    static void main(String[] args) {
        new UtPDPOutletMetaTags().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    void test() {
        setup([
                author  : 'evdovina',
                title   : 'Unit Test PDP Outlet Meta Tags',
                PBI     : 709399,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'unit test twitter card meta tag open graph meta tag | outlet PDP',
                logLevel: 'info'
        ])

        testPositiveCondition()
        testNegativeCondition()
    }

    void testPositiveCondition() {
        tryLoad("/outlet/109HF3EA/54427.html")

        MetaElement metaElement = new MetaElement()
        def twitterCardMetaData = metaElement.getTwitterCardMetaTag()
        assert twitterCardMetaData.size() == 3

        def openGraphMetaTags = metaElement.getOpenGraphMetaTag()
        assert openGraphMetaTags.size() == 12
    }

    void testNegativeCondition() {
        tryLoad("/outlet/178UDD60HC/55967.html")

        MetaElement metaElement = new MetaElement()
        def twitterCardMetaData = metaElement.getTwitterCardMetaTag()
        assert twitterCardMetaData.size() != 1

        def openGraphMetaTags = metaElement.getOpenGraphMetaTag()
        assert openGraphMetaTags.size() != 1
    }
}