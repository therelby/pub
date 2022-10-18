package framework.wss.pages.cart.cartcustomitembox

import above.RunWeb
import all.Money
import wss.item.custom.CustomCuttingBoard
import wss.pages.cart.CartCouponCode
import wss.pages.cart.CartCustomItemBox
import wss.pages.cart.ViewCartPage
import wss.pages.element.modal.LoggedInAsModal
import wss.user.UserType
import wss.user.userurllogin.UserUrlLogin

class UtCartCustomItemBox extends RunWeb {

    static void main(String[] args) {
        new UtCartCustomItemBox().testExecute([

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
        final int PBI = 627334

        setup([
                author  : 'vdiachuk',
                title   : 'Cart Customizable  | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'cart custom customizable item box unit test',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])

        UserUrlLogin userUrlLogin = new UserUrlLogin()
        userUrlLogin.loginNewUser(UserType.REGULAR_USER, PBI)

        CustomCuttingBoard customCuttingBoard = new CustomCuttingBoard()
        customCuttingBoard.navigate()
        Map boardData = customCuttingBoard.setRandomBoard(1)
        assert customCuttingBoard.addCustomBoardToCart()

        ViewCartPage viewCartPage = new ViewCartPage()
        assert viewCartPage.getItemsInCart().size() == 1

        CartCustomItemBox cartCustomItemBox = new CartCustomItemBox(0)

        // log cartCustomItemBox.itemBoxXpath
        assert verifyElement(cartCustomItemBox.itemBoxXpath)
        assert cartCustomItemBox.verifyItemBox()

        assert cartCustomItemBox.getItemNumberFromPage() == 'SAN1T'
        assert cartCustomItemBox.getPrice().size() > 2
        assert cartCustomItemBox.getTotal().size() > 2
        assert cartCustomItemBox.getQuantity() == "1"
        assert cartCustomItemBox.getTitle().contains( "Custom Cutting Board ")
        assert cartCustomItemBox.removeItemXpath == "(//div[contains(@class, 'cartItem') and contains(@class, 'ag-item')])[1]//*[contains(@class,'deleteCartItemButton')]"

        assert cartCustomItemBox.deleteItemAndCheckNotPresent()


    }
}
