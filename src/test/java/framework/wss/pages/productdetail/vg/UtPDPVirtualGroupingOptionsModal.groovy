package framework.wss.pages.productdetail.vg

import above.RunWeb
import wss.pages.productdetail.PDPReview
import wss.pages.productdetail.PDPVirtualGrouping
import wss.user.userurllogin.UserUrlLogin

class UtPDPVirtualGroupingOptionsModal extends RunWeb {

    def test() {

        setup('vdiachuk', 'PDPVirtual Grouping Page Options Modal unit test  | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test pdp product detail page vg virtual grouping options modal leave review',
                 "PBI:504124",
                 'logLevel:info'])



        PDPVirtualGrouping pdpVirtualGrouping = new PDPVirtualGrouping()
        tryLoad("https://www.dev.webstaurantstore.com/g/517/mercer-culinary-millennia-m60200-black-unisex-customizable-air-short-sleeve-cook-shirt-with-full-mesh-back")
        assert !pdpVirtualGrouping.openSelectOptionsModal()

        tryLoad()
        UserUrlLogin userUrlLogin = new UserUrlLogin()
        userUrlLogin.loginNewUser("Regular User", 0)

        tryLoad("https://www.dev.webstaurantstore.com/g/517/mercer-culinary-millennia-m60200-black-unisex-customizable-air-short-sleeve-cook-shirt-with-full-mesh-back")
        assert pdpVirtualGrouping.openSelectOptionsModal()

        tryLoad("https://www.dev.webstaurantstore.com/henry-segal-medium-white-waiters-gloves-with-snap-close-wrists/167204MDWH.html")
        assert !pdpVirtualGrouping.openSelectOptionsModal()

        pdpVirtualGrouping.navigateVG("158^g")
        // not present Modal
        assert !pdpVirtualGrouping.isSelectOptionsModalForReviewPresent()
        assert !pdpVirtualGrouping.isSelectOptionsModalForReviewPresent()
        click(PDPReview.leaveReviewButtonXpath)
        // present Modal
        sleep(500)
        assert pdpVirtualGrouping.isSelectOptionsModalForReviewPresent()
        def optionsMap = ["Width": "Medium", "Size": "8.5"]
        assert pdpVirtualGrouping.setVGOptionsInModal(optionsMap)

        pdpVirtualGrouping.navigateVG("158^g")
        // check to set wrong parameters
        click(PDPReview.leaveReviewButtonXpath)
        optionsMap = ["Width": "Medium", "Size": "FAKE"]
        assert pdpVirtualGrouping.isSelectOptionsModalForReviewPresent()
        assert !pdpVirtualGrouping.setVGOptionsInModal(optionsMap)


        //Options Modal get Data Ut
        pdpVirtualGrouping.navigateVG("158^g")
        click(PDPReview.leaveReviewButtonXpath)
        def pdpOptionsData = pdpVirtualGrouping.getOptionsData()
        def modalOptionsData = pdpVirtualGrouping.getModalOptionsData()
        assert pdpOptionsData == modalOptionsData

        pdpVirtualGrouping.navigateVG("561^g")
        click(PDPReview.leaveReviewButtonXpath)
        pdpOptionsData = pdpVirtualGrouping.getOptionsData()
        modalOptionsData = pdpVirtualGrouping.getModalOptionsData()
        assert pdpOptionsData == modalOptionsData


        tryLoad()
        assert pdpVirtualGrouping.getModalOptionsData() == []
        assert pdpVirtualGrouping.getOptionsData() == []

    }
}
