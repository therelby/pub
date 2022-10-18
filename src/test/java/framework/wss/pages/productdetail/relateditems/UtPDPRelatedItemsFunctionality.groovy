package framework.wss.pages.productdetail.relateditems

import above.RunWeb
import wss.api.catalog.product.Products
import wss.pages.productdetail.PDPage
import wss.pages.productdetail.PDPFindRelatedProducts

class UtPDPRelatedItemsFunctionality extends RunWeb{

    private def pdpFindRelatedProducts

    protected String nonEverlivingLoneProductWithRelatedProducts = '962WBS1432'
    protected String nonEverlivingLoneProductWithRelatedProductsUrl = 'https://www.dev.webstaurantstore.com/choice-bagel-scoop-wire-skimmer-32-x-14/962WBS1432.html'

    protected String everlivingLoneProductWithRelatedProducts = '195BR60T26CN'
    protected String everlivingLoneProductWithRelatedProductsUrl = 'https://www.dev.webstaurantstore.com/blodgett-br-60gt-2-3636c-nat-natural-gas-2-burner-72-thermostatic-range-with-60-left-griddle-1-convection-oven-and-1-standard-oven-240-000-btu/195BR60T26CN.html'

    protected String nonEverlivingLoneSuffixProductWithRelatedProducts = '130FD0908SP 1M'
    protected String nonEverlivingLoneSuffixProductWithRelatedProductsUrl = 'https://www.dev.webstaurantstore.com/8-x-9-unprinted-plastic-deli-saddle-bag-with-seal-top-case/130FD0908SP%201M.html'

    protected String everlivingLoneSuffixProductWithRelatedProducts = '135DMXS30H  120'
    protected String everlivingLoneSuffixProductWithRelatedProductsUrl = 'https://www.dev.webstaurantstore.com/apw-wyott-racer-dmxs-30h-30-horizontal-countertop-merchandiser-120v/135DMXS30H%20%20120.html'

    protected String nonEverlivingVirtualGroupingProductWithRelatedProducts = '87925596'
    protected String nonEverlivingVirtualGroupingProductWithRelatedProductsUrl = 'https://www.dev.webstaurantstore.com/ergodyne-25596-glowear-8381-lime-type-r-class-3-hi-vis-4-in-1-bomber-jacket-2xl/87925596.html'

    protected String everlivingVirtualGroupingProductWithRelatedProducts = '757M102011W'
    protected String everlivingVirtualGroupingProductWithRelatedProductsUrl = 'https://www.dev.webstaurantstore.com/reebok-srb1020-senexis-maxtrax-mens-size-11-wide-width-black-soft-toe-non-slip-athletic-shoe/757M102011W.html'

    protected String virtualGroupingMasterProductUrl = 'https://www.dev.webstaurantstore.com/g/320/jackson-rackstar-44-single-tank-high-temperature-conveyor-dish-machine-3-phase'

    def test() {

        setup('mwestacott', 'PDPage Related Items Functionality unit test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test pdpage related items functionality ',
                 "PBI: 0",
                 'logLevel:info'])

        testingByUrlAndIsEverliving(nonEverlivingLoneProductWithRelatedProducts, nonEverlivingLoneProductWithRelatedProductsUrl, false)
        testingByUrlAndIsEverliving(everlivingLoneProductWithRelatedProducts, everlivingLoneProductWithRelatedProductsUrl, true)
        testingByUrlAndIsEverliving(nonEverlivingLoneSuffixProductWithRelatedProducts, nonEverlivingLoneSuffixProductWithRelatedProductsUrl, false)
        testingByUrlAndIsEverliving(everlivingLoneSuffixProductWithRelatedProducts, everlivingLoneSuffixProductWithRelatedProductsUrl, true)
        testingByUrlAndIsEverliving(nonEverlivingVirtualGroupingProductWithRelatedProducts, nonEverlivingVirtualGroupingProductWithRelatedProductsUrl, false)
        testingByUrlAndIsEverliving(everlivingVirtualGroupingProductWithRelatedProducts, everlivingVirtualGroupingProductWithRelatedProductsUrl, true)

        testingVGMasterProduct(virtualGroupingMasterProductUrl)

        closeBrowser()
    }

    private void testingRelatedProducts(String itemNumber){

        assert pdpFindRelatedProducts.doesFindRelatedProductAppear()
        assert getTextSafe(PDPFindRelatedProducts.findRelatedProductsSectionHeader) == "Find Related Products"

        def expectedRelatedItems = getRelatedItems(itemNumber)
        def actualRelatedItems = pdpFindRelatedProducts.getData()

        assert expectedRelatedItems != null
        assert actualRelatedItems.size() > 0

        assert actualRelatedItems.size() == expectedRelatedItems.size()

        int numberOfRelatedItems = actualRelatedItems.size()

        for (int i = 0; i < numberOfRelatedItems; i++) {

            def expectedRelatedItem = expectedRelatedItems[i]
            def actualRelatedItem = actualRelatedItems[i]

            String expectedRelatedProductSearchTerm = expectedRelatedItem["relatedProductSearchTerm"]
            String actualRelatedProductSearchTerm = actualRelatedItem["relatedProductSearchTerm"]

            assert actualRelatedProductSearchTerm == expectedRelatedProductSearchTerm

            String expectedRelatedProductSearchTermLink = "${getUrl("homepage")}search/${expectedRelatedItem["relatedProductSearchTermEncoded"]}.html"
            String actualRelatedProductSearchTermLink = actualRelatedItem["relatedProductSearchTermEncoded"]

            assert actualRelatedProductSearchTermLink == expectedRelatedProductSearchTermLink
        }
    }

    void testingByUrlAndIsEverliving(String itemNumber, String url, boolean isEverliving){
        assert openAndTryLoad(url)
        pdpFindRelatedProducts = new PDPFindRelatedProducts()

        if(!isEverliving) {
            testingRelatedProducts(itemNumber)
        }
        else{
            assert !pdpFindRelatedProducts.doesFindRelatedProductAppear()
        }
    }

    void testingVGMasterProduct(String url){
        assert openAndTryLoad(url)
        def pdp = new PDPage()
        String itemNumber = pdp.getItemNumber()
        testingRelatedProducts(itemNumber)
    }

    protected def getRelatedItems(String itemNumber){
        Products api = new Products(itemNumber)
        return api.getValues("relatedSearchTerms")
    }
}
