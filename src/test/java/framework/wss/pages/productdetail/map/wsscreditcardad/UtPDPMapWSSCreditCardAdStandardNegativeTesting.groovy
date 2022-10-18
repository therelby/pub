package framework.wss.pages.productdetail.map.wsscreditcardad

import above.RunWeb
import wss.pages.productdetail.PDPPriceTile

class UtPDPMapWSSCreditCardAdStandardNegativeTesting extends RunWeb{
    protected String mapProductOnlyNoOverrides = '464117117506'
    protected String mapProductOnlyNoOverridesUrl = 'https://www.dev.webstaurantstore.com/metrovac-ed-500-esd-datavac-anti-static-electric-duster-500-handheld-blower-with-attachment-kit-500w/464117117506.html'

    protected String mapProductOnlyPlatinumPlusOverride = '214DRS48BR'
    protected String mapProductOnlyPlatinumPlusOverrideUrl = 'https://www.dev.webstaurantstore.com/cambro-drs48131-s-series-48-x-21-x-12-brown-solid-top-bow-tie-dunnage-rack-3000-lb-capacity/214DRS48BR.html'

    protected String mapProductOnlyRegularOverride = '690FGQ7600YL'
    protected String mapProductOnlyRegularOverrideUrl = 'https://www.dev.webstaurantstore.com/rubbermaid-fgq760000000-hygen-48-72-yellow-ergonomic-quick-connect-telescoping-handle/690FGQ7600YL.html'

    protected String mapProductRegularAndPlatinumPlusOverride = '38508086L'
    protected String mapProductRegularAndPlatinumPlusOverrideUrl = 'https://www.dev.webstaurantstore.com/cecilware-08086l-8-1-2-x-8-1-2-x-6-5-8-full-size-fryer-basket-with-front-hook/38508086L.html'

    def test() {

        setup('mwestacott', 'PDPage MAP WSS Credit Card Ad Standard Negative Testing unit test  | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test pdpage map wss credit card ad standard negative testing ',
                 "tfsTcIds:0",
                 'logLevel:info'])

        testingByUser('guest') //Guest user
        testingByUser('regular', 8613901) //Regular user
        testingByUser('platinum', 25909953) //Plus user
        testingByUser('webplus', 25035591) //Platinum user
        testingByUser('platinumplus', 241217) //Platinum Plus user
    }

    void testingByUser(String userType, Integer userId = null){
        String initialUrl = (userType=='guest') ? "https://www.dev.webstaurantstore.com/myaccount/?logout=Y" : "https://www.dev.webstaurantstore.com/?login_as_user=$userId"
        assert tryLoad(initialUrl)

        wssCreditCardAdNegativeTesting(mapProductOnlyNoOverridesUrl)
        wssCreditCardAdNegativeTesting(mapProductOnlyRegularOverrideUrl)
        wssCreditCardAdNegativeTesting(mapProductOnlyPlatinumPlusOverrideUrl)
        wssCreditCardAdNegativeTesting(mapProductRegularAndPlatinumPlusOverrideUrl)
    }

    void wssCreditCardAdNegativeTesting(String url){
        assert tryLoad(url)
        assert !verifyElement(PDPPriceTile.webstaurantRewardsVisaCreditCardBlockXpath)
    }
}
