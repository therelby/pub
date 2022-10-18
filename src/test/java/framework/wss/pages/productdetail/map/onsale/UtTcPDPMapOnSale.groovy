package framework.wss.pages.productdetail.map.onsale

import above.RunWeb
import wss.actions.WssRenderingModeBanner
import wss.pages.productdetail.PDPage
import wss.user.UserQuickLogin
import wss.user.userurllogin.UserUrlLogin
import wsstest.product.productdetailpage.mapRedo.HpPDPMapTest
import wsstest.product.productdetailpage.mapRedo.HpPDPMapTestSalesPrice
import wss.item.ItemUtil

class UtTcPDPMapOnSale extends RunWeb{
    String productOnSaleNoOverride = '39022CGT61N'
    String productOnSaleMapOverride = '5255635'
    String productOnSalePlatinumOverride = '214250LCDCB'

    Integer guestUser = null
    Integer regularUser = 8613901
    Integer platinumUser = 25909953
    Integer plusUser = 25035591
    Integer platinumPlusUser = 241217

    static void main(String[] args) {
        new UtTcPDPMapOnSale().testExecute([

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

        setup('mwestacott', 'MAP PDP On Sale unit test',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test, pdp, map, on sale ',
                 "PBI: 0",
                 'logLevel:info'])

        assert openAndTryLoad(getUrl('hp') + "/plus/")
        assert new WssRenderingModeBanner().setMode("SF")

        assert UserQuickLogin.logOut()

        runningTestsForUser(guestUser, 'guest')
        runningTestsForUser(regularUser, 'regular')
        runningTestsForUser(platinumUser, 'platinum')
        runningTestsForUser(platinumPlusUser, 'platinumPlus')
        runningTestsForUser(plusUser, 'plus')

        closeBrowser()
    }

    void runningTest(String errorMessage, boolean expectedCondition){
        check(expectedCondition, errorMessage)
    }

    void runningTestsForUser(Integer userIndexUnderTest, String userTypeUnderTest){
        UserUrlLogin userUrlLogin = new UserUrlLogin()
        assert (userIndexUnderTest == null) ? userUrlLogin.logOut() : userUrlLogin.loginAs(userIndexUnderTest.toString())

//        testingProduct(productOnSaleNoOverride, userIndexUnderTest, userTypeUnderTest, false)
//        testingProduct(productOnSaleMapOverride, userIndexUnderTest, userTypeUnderTest, false)
        testingProduct(productOnSalePlatinumOverride, userIndexUnderTest, userTypeUnderTest, false)

//        testingProduct(productOnSaleNoOverride, userIndexUnderTest, userTypeUnderTest, true)
//        testingProduct(productOnSaleMapOverride, userIndexUnderTest, userTypeUnderTest, true)
        testingProduct(productOnSalePlatinumOverride, userIndexUnderTest, userTypeUnderTest, true)
    }

    void testingProduct(String itemNumber, Integer userIndexUnderTest, String userTypeUnderTest, boolean isGoogleView){

        def itemNumberUnderTest = ItemUtil.getIndividualMapProduct(itemNumber)
        PDPage pdPage = new PDPage()
        assert pdPage.navigateToPDPWithItemNumber(itemNumber)
        if(isGoogleView){
            String googleUrl = getCurrentUrl() + "?utm_source=google"
            assert tryLoad(googleUrl)
        }
        Boolean shouldCrossedOutSectionAppear = (userIndexUnderTest != null || !isGoogleView)

/*        if(!shouldCrossedOutSectionAppear){
            assert HpPDPMapTestSalesPrice.doesSalePriceMatchForNonCrossedOutSection(itemNumberUnderTest)
            assert HpPDPMapTestSalesPrice.doesCrossedOutSalePriceAppear()
        }
        else{
            assert HpPDPMapTestSalesPrice.doesSalePriceMatchForCrossedOutRegularPriceSection(itemNumberUnderTest)
            assert HpPDPMapTestSalesPrice.doesExpectedCrossedOutPriceAppear(itemNumberUnderTest)
        }*/

//        HpPDPMapTestSalesPrice.testingSalePrice(itemNumberUnderTest, shouldCrossedOutSectionAppear)

        UtTcPDPMap.standardTesting(itemNumberUnderTest, userTypeUnderTest, userIndexUnderTest, false, isGoogleView, true, null)
        //UtTcPDPMap.standardTestingNonUnitTest(itemNumberUnderTest, userTypeUnderTest, userIndexUnderTest, false, isGoogleView, true, null)
    }

}
