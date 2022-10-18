package framework.runweb.funcread

import above.RunWeb
import wss.actions.WssRenderingMode
import wss.actions.WssRenderingModeBanner

class UtFuncReadGetCssValue extends RunWeb {
    static void main(String[] args) {
        new UtFuncReadGetCssValue().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }
    def test() {

        setup('vdiachuk', 'Unit test for Func Read Get Css Value functionality| Framework Self ' +
                'Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords: unit test mode  rendering sf cf coldfusion storefront update cookie',
                 "PBI: 0",
                 'logLevel: info'])

        String headerDivXpath = "//div[@data-testid='hero-image']"

        String cssProperty = "background-image"
        String expectedBackGroundImage = "url(\"https://www.dev.webstaurantstore.com/uploads/seo_category/2020/12/specialbannerimagestenzo.jpg\")"
        tryLoad('https://www.dev.webstaurantstore.com/feature/20015/tenzo-matcha-sale-12-14-20/')
        assert getCssValue(find(headerDivXpath), cssProperty) == expectedBackGroundImage
        assert getCssValue(headerDivXpath, cssProperty) == expectedBackGroundImage

        assert getCssValue( "//div[@data-testid='hero-imageFAKE']", cssProperty) == null
        assert getCssValue( null, cssProperty) == null
        assert getCssValue( headerDivXpath, null) == null

        assert getCssValue(headerDivXpath, 'background-color') == "rgba(0, 0, 0, 0)"
        assert getCssValue(headerDivXpath, 'color') == "rgba(59, 61, 59, 1)"

    }
}
