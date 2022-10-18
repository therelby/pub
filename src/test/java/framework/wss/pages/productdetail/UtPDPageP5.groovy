package framework.wss.pages.productdetail

import above.RunWeb
import wss.api.catalog.product.Products
import wss.pages.element.Filter
import wss.pages.productdetail.PDPage
import wss.pages.element.PopoverTemplate
import wss.pages.productdetail.PDPPriceTile

class UtPDPageP5 extends RunWeb {

    def test() {

        setup('mwestacott', 'PDPage Product Detail Page P5 unit test  | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test pdpage pdp product detail page p5 ',
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

        //Lone product
        testingP5Products('https://www.dev.webstaurantstore.com/manitowoc-ar-10000-arctic-pure-single-cartridge-ice-machine-water-filtration-system-with-1-micron-rating-0-75-gpm/499AR10000.html', '$268.49', '$256.29', 'Each')

        //Virtual Grouping product
        testingP5Products('https://www.dev.webstaurantstore.com/choice-2-oz-clear-plastic-souffle-cup-portion-cup-case/127P2C.html', '$39.99', '$31.49', 'Case')

    }

    void testingP5Products(String url, String p1, String p5, String uom){
        assert tryLoad(url)

        assert getText(PDPPriceTile.plusMemberRegularPriceLabelXpath) == 'Regularly'
        assert getText(PDPPriceTile.plusMemberRegularPriceXpath) == "$p1/$uom"

        assert getText(PDPPriceTile.plusMemberPlusPriceLabelXpath) == 'Plus Member Discount'
        assert getText(PDPPriceTile.plusMemberPlusPriceXpath) == "$p5/$uom"

        assert mouseOver(PDPPriceTile.plusMemberPlusPopoverXpath)
        assert waitForElementVisible(PopoverTemplate.popover)

        assert getTextSafe(PopoverTemplate.popoverTitle) == "Plus Member Discount"
        assert getTextSafe(PopoverTemplate.popoverContent) == "Plus members receive this discounted price thanks to their monthly subscription to WebstaurantPlus. To receive this discounted price, and the other benefits of WebstaurantPlus, subscribe today and get the first month free!"

        assert mouseOver(PDPage.pageHeaderXpath)
        sleep(1000)
        assert !elementVisible(PopoverTemplate.popover)

    }

}
