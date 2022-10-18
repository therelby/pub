package framework.wss.item

import above.RunWeb
import wss.cart.Cart
import wss.pages.productdetail.PDPPriceTile
import wsstest.cart.shippinglimitations.HpShippingLimitations
import wss.cart.shipping.ShippingCartDetails

class UtORMDHazmat extends RunWeb{

    String hasOrmdHasHazmatUrl = 'https://www.dev.webstaurantstore.com/noble-chemical-18-oz-nukleen-oven-grill-cleaner-case/148NUKLEEN.html'

    def test() {

        setup('mwestacott', 'Products ORMD and Hazmat unit test  | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test products ormd hazmat ',
                 "tfsTcIds:0",
                 'logLevel:info'])

        assert HpShippingLimitations.getOrmdAndHazmatItemsForCartTesting(5, false, false).size() == 5
        assert HpShippingLimitations.getOrmdAndHazmatItemsForCartTesting(5, false, true).size() == 5
        assert HpShippingLimitations.getOrmdAndHazmatItemsForCartTesting(5, true, false).size() == 0
        assert HpShippingLimitations.getOrmdAndHazmatItemsForCartTesting(5, true, true).size() == 5

        testingByUser('guest') //Guest user
        testingByUser('regular', 8613901) //Regular user
        testingByUser('platinum', 25909953) //Plus user
        testingByUser('webplus', 25035591) //Platinum user
        testingByUser('platinumplus', 241217) //Platinum Plus user

        closeBrowser()
    }

    void testingByUser(String userType, Integer userId = null){
        String initialUrl = (userType=='guest') ? "https://www.dev.webstaurantstore.com/myaccount/?logout=Y" : "https://www.dev.webstaurantstore.com/?login_as_user=$userId"
        assert tryLoad(initialUrl)

        Cart.emptyCart()

        testingORMDHazmatProducts(hasOrmdHasHazmatUrl, (userType == 'guest'))
    }

    void testingORMDHazmatProducts(String url, boolean isUserGuest){
        assert tryLoad(url)

        PDPPriceTile pdpPriceTile = new PDPPriceTile()
        assert pdpPriceTile.addToCart(1)
        assert tryLoad("cart")

        waitForPage()

        if(isUserGuest){
            Cart.setAddressTypeAndZipCode("Business", "34688")
        }

        assert waitForElement(ShippingCartDetails.allShippingOptionsXpath)

        ShippingCartDetails shipDetails = new ShippingCartDetails()
        LinkedHashMap allShippingOptions = shipDetails.getAllShippingOptionsAndPrices()

        assert allShippingOptions.size() == 1
        String shippingOption = allShippingOptions.find { it.key == 'Ground' }?.key
        assert shippingOption == 'Ground'
    }
}
