package framework.wss.pages.productdetail.map.wsscreditcardad

import above.Run
import above.RunWeb
import all.Money
import all.util.Addresses
import wss.api.user.UserCreationApi
import wss.api.user.UserLookupApi
import wss.checkout.Checkout
import wss.item.ItemUtil
import wss.pages.productdetail.PDPPriceTile
import wss.user.UserUtil
import wss.user.userurllogin.UserUrlLogin
import wsstest.product.productdetailpage.map.hPDPMapTest

class UtPDPMapWSSCreditCardAdRewardsCustomerTesting extends RunWeb{

    protected String mapProductNoAdNoOverrides = '464117117506'
    protected String mapProductNoAdNoOverridesUrl = 'https://www.dev.webstaurantstore.com/metrovac-ed-500-esd-datavac-anti-static-electric-duster-500-handheld-blower-with-attachment-kit-500w/464117117506.html'

    protected String mapProductNoAdPlatinumPlusOverride = '214DRS48BR'
    protected String mapProductNoAdPlatinumPlusOverrideUrl = 'https://www.dev.webstaurantstore.com/cambro-drs48131-s-series-48-x-21-x-12-brown-solid-top-bow-tie-dunnage-rack-3000-lb-capacity/214DRS48BR.html'

    protected String mapProductNoAdRegularOverride = '690FGQ7600YL'
    protected String mapProductNoAdRegularOverrideUrl = 'https://www.dev.webstaurantstore.com/rubbermaid-fgq760000000-hygen-48-72-yellow-ergonomic-quick-connect-telescoping-handle/690FGQ7600YL.html'

    protected String mapProductNoAdRegularAndPlatinumPlusOverride = '38508086L'
    protected String mapProductNoAdRegularAndPlatinumPlusOverrideUrl = 'https://www.dev.webstaurantstore.com/cecilware-08086l-8-1-2-x-8-1-2-x-6-5-8-full-size-fryer-basket-with-front-hook/38508086L.html'

    protected String mapProductAdNoOverrides = '131500HWD6A'
    protected String mapProductAdNoOverridesUrl = 'https://www.dev.webstaurantstore.com/alto-shaam-500-hw-d6-five-pan-drop-in-hot-food-well-6-deep-pans-120v/131500HWD6A.html'

    protected String mapProductAdPlatinumPlusOverride = '901VCCG36ASN'
    protected String mapProductAdPlatinumPlusOverrideUrl = 'https://www.dev.webstaurantstore.com/vulcan-vccg36-as-natural-gas-36-griddle-with-atmospheric-burner-and-steel-plate-90-000-btu/901VCCG36ASN.html'

    protected String mapProductAdRegularOverride = '76532SAT1W'
    protected String mapProductAdRegularOverrideUrl = 'https://www.dev.webstaurantstore.com/merco-mhg32sat1w-mercomax-3-shelf-6-pan-visual-holding-bin-cabinet-208-240v-1840w/76532SAT1W.html'

    protected String mapProductAdRegularAndPlatinumPlusOverride = '214B650DSCPM'
    protected String mapProductAdRegularAndPlatinumPlusOverrideUrl = 'https://www.dev.webstaurantstore.com/cambro-bar650dscp667-manhattan-designer-series-cambar-67-portable-bar-with-7-bottle-speed-rail-and-cold-plate/214B650DSCPM.html'


    def test() {

        setup('mwestacott', 'PDPage MAP WSS Credit Card Ad Rewards Customer Testing unit test  | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test pdpage map wss credit card ad rewards customer testing ',
                 "tfsTcIds:0",
                 'logLevel:info'])

        testingByUser('regular', 2865871) //Regular Rewards user
        testingByUser('platinum', 28109049) //Platinum Rewards user
        testingByUser('webplus', 28778559) //Plus Rewards user
        testingByUser('platinumplus', 28872573) //Platinum Plus Rewards user
    }

    void testingByUser(String userType, Integer userId = null){
        String initialUrl = (userType=='guest') ? "https://www.dev.webstaurantstore.com/myaccount/?logout=Y" : "https://www.dev.webstaurantstore.com/plus/?login_as_user=$userId"
        assert tryLoad(initialUrl)

        wssCreditCardAdNegativeTesting(mapProductNoAdNoOverridesUrl)
        wssCreditCardAdNegativeTesting(mapProductNoAdPlatinumPlusOverrideUrl)
        wssCreditCardAdNegativeTesting(mapProductNoAdRegularOverrideUrl)
        wssCreditCardAdNegativeTesting(mapProductNoAdRegularAndPlatinumPlusOverrideUrl)
        wssCreditCardAdNegativeTesting(mapProductAdNoOverridesUrl)
        wssCreditCardAdNegativeTesting(mapProductAdPlatinumPlusOverrideUrl)
        wssCreditCardAdNegativeTesting(mapProductAdRegularOverrideUrl)
        wssCreditCardAdNegativeTesting(mapProductAdRegularAndPlatinumPlusOverrideUrl)
    }

    void wssCreditCardAdNegativeTesting(String url){
        assert tryLoad(url)
        assert !verifyElement(PDPPriceTile.webstaurantRewardsVisaCreditCardBlockXpath)
    }
}
