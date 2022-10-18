package framework.wss.pages.productdetail.vg

import above.RunWeb
import wss.pages.productdetail.PDPReview
import wss.pages.productdetail.PDPVirtualGrouping

class UtPDPVirtualGrouping extends RunWeb {
    def test() {

        setup('vdiachuk', 'PDPVirtual Grouping  Page  unit test  | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test pdp product detail page vg virtual grouping',
                 "tfsTcIds:265471",
                 'logLevel:info'])

        tryLoad()

        PDPVirtualGrouping pdpVirtualGrouping = new PDPVirtualGrouping()
//        // Not  VG page
        assert !pdpVirtualGrouping.isVGPage()
        assert getTextSafe(PDPVirtualGrouping.vgHeaderXpath) == ""
        assert pdpVirtualGrouping.getOptionsData() == []

        // VG page
        log "--"
        assert pdpVirtualGrouping.navigateVG('158')
        assert pdpVirtualGrouping.isVGPage()
        assert getTextSafe(PDPVirtualGrouping.vgHeaderXpath) == PDPVirtualGrouping.vgHeaderText
        assert pdpVirtualGrouping.getOptionsData().size() == 17
        assert !pdpVirtualGrouping.setVGOptions(["Width": "36 Inches"])



        // VG member page
        log "--"
        tryLoad("https://www.dev.webstaurantstore.com/henry-segal-medium-white-waiters-gloves-with-snap-close-wrists/167204MDWH.html")
        assert pdpVirtualGrouping.isVGPage()
        assert getTextSafe(PDPVirtualGrouping.vgHeaderXpath) == PDPVirtualGrouping.vgHeaderText
        assert pdpVirtualGrouping.getOptionsData().size() == 4
        Map optionsMap = ["Size": "M"]
        assert pdpVirtualGrouping.setVGOptions(optionsMap)

        Map optionsMapActual = pdpVirtualGrouping.getOptionsData().findAll() { it['selected'] }.collectEntries { [it['name'], it["value"]] }
        assert optionsMap == optionsMapActual

        //
        // Check Selects success for every available option
        //
        pdpVirtualGrouping.navigateVG("158^g")
        optionsMap = ["Width": "Medium", "Size": "10"]
        assert pdpVirtualGrouping.setVGOptions(optionsMap)
        def optionsData = pdpVirtualGrouping.getOptionsData()
        assert optionsMap == optionsData.findAll() { it['selected'] }.collectEntries { [it['name'], it["value"]] }
        def optionsDataAvailable = optionsData.findAll() { it["disabled"] == false }
        def parameters = []
        optionsDataAvailable.each {
            def map = [:]
            map[it["name"]] = it["value"]
            parameters << map
        }
        // print values for debugging
        log parameters
        // adding items to cart
        parameters.each {
            assert pdpVirtualGrouping.setVGOptions(it)
            // sleep(1000)
        }

        //
        // check Fake selecting parameter
        assert !pdpVirtualGrouping.setVGOptions(["Width": "Medium", "Size": "10FAKE"])

    }
}
