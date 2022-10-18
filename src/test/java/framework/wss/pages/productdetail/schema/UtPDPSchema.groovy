package framework.wss.pages.productdetail.schema

import above.RunWeb
import wss.pages.element.SchemaElement

class UtPDPSchema extends RunWeb {

    static void main(String[] args) {
        new UtPDPSchema().testExecute([

                browser      : 'chrome',
                remoteBrowser: false,
                //  browserVersionOffset: -1,   // use specific browser version
                environment  : 'dev',        // use specific environment
                runType      : 'Debug',//'Regular'          //Debug
                /*   parallelThreads: 1,
               runType: 'Regular' ,
               browserVersionOffset: -1   */
        ])
    }

    def test() {
        final int PBI = 530257
        setup([
                author  : 'vdiachuk',
                title   : 'PDP Schema unit test | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: ' pdp product detail page schema review breadcrumb video unit test',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])

        tryLoad("/schraf-10-cimeter-knife-with-yellow-tprgrip-handle/220VCIMSMY10.html")
        SchemaElement pdpSchema = new SchemaElement()
        assert pdpSchema.getSchemaTypes().size() == 6

        log "--"
        tryLoad("/elkay-plastics-p12f0912-plastic-food-bag-candy-bag-9-x-12-box/130P12F0912%201M.html")
        assert pdpSchema.getSchemaTypes() == ["BreadcrumbList", "Product", "WebSite", "Organization"]
        assert pdpSchema.getDataBySchemaType('Product')["@type"] == "Product"
        log "--"
        assert pdpSchema.getDataBySchemaType('ProductFAKE') == null

        log "--"
        tryLoad()
        assert pdpSchema.getSchemaTypes() == ['WebSite', 'Organization']

        log "--"
        tryLoad("https://www.dev.webstaurantstore.com/carts.html")
        assert pdpSchema.getSchemaTypes().contains("BreadcrumbList")
        assert pdpSchema.getData().containsKey("BreadcrumbList")

        // no schema
        log "--"
        tryLoad('https://www.selenium.dev/')
        assert !pdpSchema.getData()
        assert pdpSchema.getDataBySchemaType("Product") == null
        assert !pdpSchema.getSchemaTypes()
    }
}