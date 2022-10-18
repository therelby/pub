package framework.wss.pages.productdetail.stickyheader

import above.RunWeb
import wss.pages.productdetail.PDPStickyHeader
import wss.pages.productdetail.PDPage
import org.openqa.selenium.support.ui.Select

class UtPDPStickyHeaderMinMustBuy extends RunWeb{

    def test() {

        setup('mwestacott', 'PDPage Product Detail Page Sticky Header Min Must Buy unit test  | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords: unit test pdpage pdp product detail page sticky header min must buy',
                 "PBI:0",
                 'logLevel:info'])

        //Lone product, no min and no must buy, available
        testingMinAndMustBuyDropdown('https://www.dev.webstaurantstore.com/oneida-r4020000131-fusion-east-8-1-4-bright-white-porcelain-coupe-plate-case/356R402131.html', 'Lone', false, 1, false, 1, false)

        //Lone suffix product, no min and no must buy, available
        testingMinAndMustBuyDropdown('https://www.dev.webstaurantstore.com/elkay-plastics-p12g0452310-plastic-food-bag-4-1-2-x-2-3-4-x-10-3-4-box/130P12G4210%202M.html', 'Lone Suffix', false, 1, false, 1, false)

        //Virtual Grouping product, no min and no must buy, available
        testingMinAndMustBuyDropdown('https://www.dev.webstaurantstore.com/shoes-for-crews-6006w-cambridge-mens-size-13-wide-width-black-water-resistant-soft-toe-non-slip-dress-shoe/756M600613W.html', 'Virtual Grouping', false, 1, false, 1, false)

        //Lone product, no min and no must buy, unavailable
        testingMinAndMustBuyDropdown('https://www.dev.webstaurantstore.com/pasabahce-42584-012-boston-1-5-oz-spirit-shot-glass-case/54342584012.html', 'Lone', false, 1, false, 1, true)

        //Lone suffix product, no min and no must buy, unavailable
        testingMinAndMustBuyDropdown('https://www.dev.webstaurantstore.com/wna-comet-ccw5240-classicware-5-oz-1-piece-clear-plastic-pedestal-wine-cup-case/625CCW5%20%20%20%20%20CL240.html', 'Lone Suffix', false, 1, false, 1, true)

        //Virtual Grouping product, no min and no must buy, unavailable
        testingMinAndMustBuyDropdown('https://www.dev.webstaurantstore.com/genuine-grip-5080-skynight-mens-size-9-medium-width-black-composite-toe-non-slip-full-grain-leather-tactical-boot-with-zipper-lock/755M50809M.html', 'Virtual Grouping', false, 1, false, 1, true)

        //Lone product, min buy only, available
        testingMinAndMustBuyDropdown('https://www.dev.webstaurantstore.com/fortessa-1-5-622-00-002-grand-city-7-15-16-18-10-stainless-steel-extra-heavy-weight-dinner-fork-case/54962200002.html', 'Lone', true, 2, false, 1, false)

        //Lone suffix product, min buy only, available
        testingMinAndMustBuyDropdown('https://www.dev.webstaurantstore.com/rubbermaid-fgsc18ssrb-silhouettes-stainless-steel-designer-waste-receptacle-40-gallon/690SC18RB%20%20%20SS.html', 'Lone Suffix', true, 8, false, 1, false)

        //Virtual Grouping product, min buy only, available
//        testingMinAndMustBuyDropdown('https://www.dev.webstaurantstore.com/matfer-bourgeat-345836-exoglass-11-1-4-x-4-x-4-1-4-non-stick-bread-mold-with-lid/980345836.html', 'Virtual Grouping', true, 3, false, 1, false)

        //Lone product, min buy only, unavailable
        testingMinAndMustBuyDropdown('https://www.dev.webstaurantstore.com/kens-foods-inc-1-gallon-select-parmesan-and-peppercorn-dressing/999991461.html', 'Lone', true, 2, false, 1, true)

        //Lone suffix product, min buy only, unavailable
        testingMinAndMustBuyDropdown('https://www.dev.webstaurantstore.com/pactiv-newspring-oc32b-32-oz-black-9-1-8-x-6-3-4-x-2-versatainer-oval-microwavable-container-with-lid-case/128OC32B%20%20%20%20COMBO150.html', 'Lone Suffix', true, 10, false, 1, true)

        //Lone product, must buy only, available
        testingMinAndMustBuyDropdown('https://www.dev.webstaurantstore.com/avery-98071-gluestic-1-27-oz-large-purple-disappearing-washable-nontoxic-permanent-gluestick-box/15498071.html', 'Lone', false, 1, true, 12, false)

        //Lone suffix product, must buy only, available
        testingMinAndMustBuyDropdown('https://www.dev.webstaurantstore.com/pactiv-newspring-nc8168-16-oz-white-5-x-7-1-4-x-1-1-2-versatainer-rectangular-microwavable-container-with-lid-case/128NC8168%20%20%20COMBO150.html', 'Lone Suffix', false, 1, true, 15, false)

        //Virtual Grouping product, must buy only, available
//        testingMinAndMustBuyDropdown('https://www.dev.webstaurantstore.com/ergodyne-25598-glowear-8381-lime-type-r-class-3-hi-vis-4-in-1-bomber-jacket-4xl/87925598.html', 'Virtual Grouping', false, 1, true, 6)

        //Lone product, must buy only, unavailable
        testingMinAndMustBuyDropdown('https://www.dev.webstaurantstore.com/hookless-hbh49mys01x-white-mystery-shower-curtain-with-matching-flat-flex-on-rings-weighted-corner-magnets-and-poly-voile-translucent-window-71-x-77/32749MYS01X.html', 'Lone', false, 1, true, 12, true)

        //Lone suffix product, must buy only, unavailable
        testingMinAndMustBuyDropdown('https://www.dev.webstaurantstore.com/pactiv-newspring-nc818-12-oz-white-4-1-2-x-5-1-2-x-1-3-4-versatainer-rectangular-microwavable-container-with-lid-case/128NC818%20%20%20%20COMBO150.html', 'Lone Suffix', false, 1, true, 12, true)

        //Lone product, must buy greater than min buy, available
        testingMinAndMustBuyDropdown('https://www.dev.webstaurantstore.com/wire-salt-pepper-shaker-sugar-packet-rack/407188.html', 'Lone', true, 5, true, 9, false)

        //Lone product, min buy greater than must buy, min buy isn't divisible by must buy, available
        testingMinAndMustBuyDropdown('https://www.dev.webstaurantstore.com/lid-for-7-oz-condiment-jar/407SLCJ007C.html', 'Lone', true, 5, true, 3, false)

        //Lone product, min buy greater than must buy, min buy isn't divisible by must buy, unavailable
        testingMinAndMustBuyDropdown('https://www.dev.webstaurantstore.com/tablecraft-599p-chrome-oil-and-vinegar-bottle-pourer/808599P.html', 'Lone', true, 54, true, 12, true)

        //Lone product, min buy greater than must buy, min buy is divisible by must buy, available
        testingMinAndMustBuyDropdown('https://www.dev.webstaurantstore.com/tablecraft-20026w-better-burger-6-oz-white-melamine-fry-cup/80820026W.html', 'Lone', true, 24, true, 6, false)

        //Lone product, min buy greater than must buy, min buy is divisible by must buy, unavailable
        testingMinAndMustBuyDropdown('https://www.dev.webstaurantstore.com/tablecraft-mssklt65-16-oz-black-faux-cast-iron-square-melamine-skillet-with-handles/808MSSKLT65.html', 'Lone', true, 12, true, 6, true)

    }

    private void testingMinAndMustBuyDropdown(String url, String itemType, boolean expectedToHaveMinBuy, int expectedMinBuyQuantity, boolean expectedToHaveMustBuy, int expectedMustBuyQuantity, boolean expectedToBeUnavailable){
        assert tryLoad(url)
        assert new PDPage().verifyItemType(itemType)

        PDPStickyHeader pdpStickyHeader = new PDPStickyHeader()
        log "before: " + pdpStickyHeader.isStickyHeader()
        assert !pdpStickyHeader.isStickyHeader()

        scrollToBottom()

        log "after: " + pdpStickyHeader.isStickyHeader()
        assert pdpStickyHeader.isStickyHeader()
        assert pdpStickyHeader.isShown()
        assert !pdpStickyHeader.isHidden()

        if((!expectedToHaveMinBuy && !expectedToHaveMustBuy) || expectedToBeUnavailable){
            assert !verifyElement(pdpStickyHeader.minMustBuyDropdownXpath)
        }
        else{
            assert verifyElement(pdpStickyHeader.minMustBuyDropdownXpath)

            int minimumDropdownQuantity = getMinMustDropdownQuantity(expectedMinBuyQuantity, expectedMustBuyQuantity)
            Select minMustBuyDropdown = new Select(find(pdpStickyHeader.minMustBuyDropdownXpath))

            for(int i = 0; i < 15; i++){
                minMustBuyDropdown.selectByIndex(i)

                Integer expectedValue = minimumDropdownQuantity + (i * expectedMustBuyQuantity)
                Integer actualValue = minMustBuyDropdown.firstSelectedOption.getAttribute("value").toInteger()

                assert actualValue == expectedValue
            }
        }
    }

    private int getMinMustDropdownQuantity(int minBuyQuantity, int mustBuyQuantity){
        if((minBuyQuantity > mustBuyQuantity) && (minBuyQuantity % mustBuyQuantity) > 0){
            int minBuyDividedByMustBuy = (minBuyQuantity/mustBuyQuantity)
            return mustBuyQuantity * (minBuyDividedByMustBuy + 1)
        }
        else{
            return Math.max(minBuyQuantity, mustBuyQuantity)
        }
    }
}
