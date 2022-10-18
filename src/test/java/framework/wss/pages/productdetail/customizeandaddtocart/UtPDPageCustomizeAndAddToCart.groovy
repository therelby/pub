package framework.wss.pages.productdetail.customizeandaddtocart

import above.RunWeb
import wss.pages.productdetail.PDPPriceTile
import wss.item.ItemUtil
import wss.pages.productdetail.PDPMap

class UtPDPageCustomizeAndAddToCart extends RunWeb{

    def test() {

        setup('mwestacott', 'PDPage Product Detail Page Customize and Add to Cart unit test  | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test pdpage pdp customize and add to cart ',
                 "tfsTcIds:0",
                 'logLevel:info'])

        assert ItemUtil.getProductsForTestingCustomizeAndAddToCart("Lone", 5, false, false, false, false, false).size() == 5
        assert ItemUtil.getProductsForTestingCustomizeAndAddToCart("Virtual Grouping", 5, false, false, false, false, false).size() == 3

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
    }

    void testingCustomizeAndAddToCart(String url, String userType, String customUrl, boolean isCurrentlyActive, boolean isCustomizationRequired, boolean isUrlLengthGreaterThanZero, boolean hasQuantityDiscount, boolean hasMapStyle, boolean hasMapOverride = false, boolean hasPlatinumPlusOverride = false, String p1 = "", String quantityDiscountPrice = "", String minimumCustomizationPrice = "", String uom = ""){

        Boolean isProductCustomizable = (isCurrentlyActive || isUrlLengthGreaterThanZero)
        Boolean isPurpleCustomizeAndAddToCartButtonExpectedToAppear = isProductCustomizable && !isCustomizationRequired
        Boolean isRedCustomizeAndAddToCartButtonExpectedToAppear = isProductCustomizable && isCustomizationRequired
        Boolean isMapActive = isMapActive(userType, hasMapStyle, hasMapOverride, hasPlatinumPlusOverride)

        assert tryLoad(url)

        assert verifyElement(PDPPriceTile.customPricesStartingAtXpath) == (isRedCustomizeAndAddToCartButtonExpectedToAppear && !isMapActive)
        assert verifyElement(PDPPriceTile.quantityDiscountRibbonXpath) == (hasQuantityDiscount && !isMapActive)

        assert verifyElement(PDPMap.baseMapXpath) == isMapActive
        
        if(!isMapActive){
            String price = getExpectedPrice(isRedCustomizeAndAddToCartButtonExpectedToAppear, hasQuantityDiscount, p1, quantityDiscountPrice, minimumCustomizationPrice)
            assert getTextSafe(PDPPriceTile.standardPriceXpath) == "$price/$uom"
        }

        assert verifyElement(PDPPriceTile.addToCartButtonXpath) == !isRedCustomizeAndAddToCartButtonExpectedToAppear

        assert verifyElement(PDPPriceTile.purpleCustomizeAndAddToCartXpath) == isPurpleCustomizeAndAddToCartButtonExpectedToAppear
        if(isPurpleCustomizeAndAddToCartButtonExpectedToAppear){
            assert getAttribute(PDPPriceTile.purpleCustomizeAndAddToCartXpath, 'href') == customUrl
        }

        assert verifyElement(PDPPriceTile.redCustomizeAndAddToCartXpath) == isRedCustomizeAndAddToCartButtonExpectedToAppear
        if(isRedCustomizeAndAddToCartButtonExpectedToAppear){
            assert getAttribute(PDPPriceTile.redCustomizeAndAddToCartXpath, 'href') == customUrl
        }

        assert verifyElement(PDPPriceTile.quantityDiscountTableXpath) == (hasQuantityDiscount && !(userType=='guest' && hasMapStyle) && !isRedCustomizeAndAddToCartButtonExpectedToAppear)
    }

    String getExpectedPrice(boolean isRedCustomizeAndAddToCartButtonExpectedToAppear, boolean hasQuantityDiscount, String p1, String quantityDiscountPrice, String minimumCustomizationPrice){
        if(isRedCustomizeAndAddToCartButtonExpectedToAppear){
            return minimumCustomizationPrice
        }
        else{
            if(hasQuantityDiscount){
                return quantityDiscountPrice
            }
            else{
                return p1
            }
        }
    }


    boolean isMapActive(String userType, boolean hasMapStyle, boolean hasMapOverride, boolean hasPlatinumPlusOverride){
        if(userType == 'guest'){
            return hasMapStyle
        }
        else{
            if(!hasMapStyle){
                return false
            }
            else {
                if (userType == 'regular') {
                    return !hasMapOverride
                } else {
                    return !hasMapOverride && !hasPlatinumPlusOverride
                }
            }
        }
    }
}
