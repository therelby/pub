package framework.project.wsswebproject

import above.RunWeb
import wss.pages.element.Breadcrumb
import wss.pages.specializedpage.SpecializedPage

class UtWssWebProject extends RunWeb{
    def test() {

        setup('vdiachuk', 'Breadcrumb Element  unit test  | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test breadcrumb element ',
                 "tfsTcIds:265471",
                 'logLevel:info'])

        String url = "https://www.dev.webstaurantstore.com/52705/reach-in-refrigerators.html"
        assert webProject.convertToStorefrontUrl(url)=="https://storefront.dev.webstaurantstore.com/52705/reach-in-refrigerators.html"

        String urlProduction = "https://www.test.webstaurantstore.com/52705/reach-in-refrigerators.html"
        assert webProject.convertToStorefrontUrl(urlProduction) == "https://storefront.test.webstaurantstore.com/52705/reach-in-refrigerators.html"

        String urlWithStorefront = "https://storefront.dev.webstaurantstore.com/52705/reach-in-refrigerators.html"
        assert webProject.convertToStorefrontUrl(urlWithStorefront) == urlWithStorefront
    }
}
