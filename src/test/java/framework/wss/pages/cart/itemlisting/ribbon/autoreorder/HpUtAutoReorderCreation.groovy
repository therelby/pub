package framework.wss.pages.cart.itemlisting.ribbon.autoreorder

import wsstest.checkout.confirmorder.autoreorder.AutoReorderUtils

class HpUtAutoReorderCreation{

    static def loneProductForActiveAutoReorder = '286PP7PP'
    static def loneSuffixProductForActiveAutoReorder = '999CH8DEDCOMBO'
    static def virtualGroupingProductForActiveAutoReorder = '489SC40L'

    private static boolean addingItemToCart(String itemType){
        String itemForTesting = getItemNumberForTesting(itemType)

        if (!AutoReorderUtils.addItemToCart(itemForTesting, 1)) {
            return false
        }

        return true
    }

    static String getItemNumberForTesting(String itemType){
        switch(itemType){
            case "Lone":
                return loneProductForActiveAutoReorder
                break
            case "Lone Suffix":
                return loneSuffixProductForActiveAutoReorder
                break
            case "Virtual Grouping":
                return virtualGroupingProductForActiveAutoReorder
                break
            default:
                return ""
                break
        }
    }

    static boolean settingUpAutoReorder(){
        if(!HpUtAutoReorderCreation.addingItemToCart('Lone')){
            return false
        }
        if(!HpUtAutoReorderCreation.addingItemToCart('Lone Suffix')){
            return false
        }
        if(!HpUtAutoReorderCreation.addingItemToCart('Virtual Grouping')){
            return false
        }
        return true
    }
}
