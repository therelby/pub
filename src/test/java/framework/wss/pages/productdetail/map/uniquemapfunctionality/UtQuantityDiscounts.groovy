package framework.wss.pages.productdetail.map.uniquemapfunctionality

import all.Money
import framework.wss.pages.productdetail.map.hUtPDPMap
import wss.actions.WssRenderingModeBanner
import wss.api.catalog.product.Products
import wss.pages.productdetail.PDPMap
import wss.user.UserQuickLogin
import wss.user.UserUtil
import wsstest.product.productdetailpage.map.hPDPMapTest

class UtQuantityDiscounts extends hUtPDPMap{


    def items = [
            ["Q", "936EZ4010", '$50.43', "Each"],
            ["Q", "9224790930", '$8.74', "Each"],

//Add MAP to these two before testing.
//            ["Q", "6902108900CP", '$8.19', "Each"],
//            ["Q", "196107360", '$669.00', "Each"],
    ]

    boolean doLotDiscountsEverOccur
    Integer maxQuantityDiscountBoundary

    def test() {
        setup('mwestacott', 'UtQuantityDiscounts',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test map pdp', 'logLevel:debug'])

        assert openAndTryLoad(getUrl('hp') + "/plus/")
        assert new WssRenderingModeBanner().setMode("SF")

        assert UserQuickLogin.logOut()

        for(item in items) {
            String itemNumber = item[1]
            tryLoad("https://www.dev.webstaurantstore.com/x/$itemNumber" + ".html?login_as_user=8613901")
            testingQuantityDiscounts(item[0], item[1], item[2], item[3], true)
        }

        closeBrowser()
    }

    private void testingQuantityDiscounts(String mapStyle, String itemNumberUnderTest, String p4, String uom, Boolean hasCompany){

        def defaultShippingAddress = UserUtil.getShippingInfoByUserIndex("8613901").get(0)
        doLotDiscountsEverOccur = false
        maxQuantityDiscountBoundary = Integer.MAX_VALUE

        int count = 1

        String expectedUserNameOrCompany = "Custom Quote for " + (hasCompany ? defaultShippingAddress.get('shipcompany') : defaultShippingAddress.get('shipname'))
        String actualUserNameOrCompanyXpath = PDPMap.getXpathForMAPAspect(mapStyle, "Custom Quote Override", "customQuoteLabel")

        assert (getText(actualUserNameOrCompanyXpath) == expectedUserNameOrCompany)

        def quantityDiscounts = getQuantityDiscounts(itemNumberUnderTest)
        int quantityDiscountSize = quantityDiscounts.size()

        testingEverythingButLastRowOfQuantityDiscountTable(mapStyle, uom, quantityDiscounts[quantityDiscountSize-1], true)
        if(quantityDiscountSize>1){
            int quantityDiscountRows = quantityDiscountSize-1

            for(int i = quantityDiscountRows; i >= 1; i--){
                testingEverythingButLastRowOfQuantityDiscountTable(mapStyle, uom, quantityDiscounts[i-1], false, count)
                count++
            }
        }

        String expectedQuantityDiscountLastRowXpath = PDPMap.customQuoteQuantityDiscount + "/tr[$count]"
        String expectedQuantityDiscountLastRowLabelXpath = expectedQuantityDiscountLastRowXpath + "/th"
        String expectedQuantityDiscountLastRowPriceXpath = expectedQuantityDiscountLastRowXpath + "/td"

        String expectedQuantityTableDiscountLabel = doLotDiscountsEverOccur ? "Regularly:" : "Buy 1 - " + (maxQuantityDiscountBoundary-1)

        assert (getText(expectedQuantityDiscountLastRowLabelXpath) == expectedQuantityTableDiscountLabel)

        assert (getText(expectedQuantityDiscountLastRowPriceXpath) == "$p4/$uom")
    }

    private def getPriceInfo(String itemNumber){
        Products api = new Products(itemNumber)
        return api.getValues("price")[1]
    }

    private def getQuantityDiscounts(String itemNumber){
        def priceInfo = getPriceInfo(itemNumber)
        return priceInfo["quantityDiscounts"]
    }

    private void testingEverythingButLastRowOfQuantityDiscountTable(String mapStyle, String uom, def quantityDiscountSection, boolean isFirstRow, int count = 0){
        String actualQuantityDiscountRowLabelXpath = isFirstRow ? PDPMap.getXpathForMAPAspect(mapStyle, "Custom Quote Override", "customQuoteQuantityDiscountLabel") : PDPMap.customQuoteQuantityDiscount + "/tr[$count]/th"
        String actualQuantityDiscountRowPriceXpath = isFirstRow ? PDPMap.getXpathForMAPAspect(mapStyle, "Custom Quote Override", "customQuotePrice") : PDPMap.customQuoteQuantityDiscount + "/tr[$count]/td"

        def defaultPrice = quantityDiscountSection["price"]
        boolean doesQuantityDiscountHaveLotDiscount = quantityDiscountSection["isLotDiscount"]
        int quantityDiscountStartQuantity = quantityDiscountSection["startQuantity"]

        if(doesQuantityDiscountHaveLotDiscount){
            doLotDiscountsEverOccur = true
        }
        else{
            maxQuantityDiscountBoundary = quantityDiscountStartQuantity
        }

        String quantityDiscountMessage = "$quantityDiscountStartQuantity" + (isFirstRow ? " or more": (" - " + quantityDiscountSection["endQuantity"]))
        String expectedQuantityDiscountLabel = "Buy " + (doesQuantityDiscountHaveLotDiscount ? "in lots of $quantityDiscountStartQuantity:" : quantityDiscountMessage)

        assert (getText(actualQuantityDiscountRowLabelXpath) == expectedQuantityDiscountLabel)

        String expectedPrice = new Money(defaultPrice)
        assert (getText(actualQuantityDiscountRowPriceXpath) == "$expectedPrice/$uom")
    }

}
