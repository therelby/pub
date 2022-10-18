package framework.wss.pages.cart.installnetwork

import above.RunWeb
import all.util.Addresses
import org.openqa.selenium.support.ui.Select
import wss.api.user.UserCreationApi
import wss.myaccount.Billing
import wss.myaccount.Shipping
import wss.pages.cart.ViewCartPage
import wss.pages.productdetail.PDPPriceTile
import wss.pages.productdetail.PDPWhatWeOffer
import wss.pages.productdetail.PDPage
import wss.pages.productdetail.modal.PDPInstallationServicesModal
import wss.user.UserDetail
import wss.user.UserType
import wss.user.UserUtil
import wss.user.userurllogin.UserUrlLogin
import wsstest.cart.installnetwork.HpCartInstallNetwork

class UtCartInstallNetworkNegativeTesting extends RunWeb{

    static Integer pbiNumber = 630550

    String installQueryProduct = '178GDC49HCB'
    Addresses address1 = UserCreationApi.defaultAddress
    Addresses address2 = Addresses.usCommercialAddressMI

    def userTypesForTesting = [UserType.REGULAR_USER, UserType.PLATINUM/*, UserType.PLATINUM_PLUS, UserType.WEB_PLUS*/]

    static void main(String[] args) {
        new UtCartInstallNetworkNegativeTesting().testExecute([

                browser      : 'chrome',
                remoteBrowser: false,
//                browserVersionOffset: -1,   // use specific browser version
                environment  : 'dev',        // use specific environment
                runType      : 'Debug',//'Regular'          //Debug
                /*   parallelThreads: 1,
               runType: 'Regular' ,
               browserVersionOffset: -1   */
        ])

    }

    def test() {

        setup('mwestacott', 'Cart - Install Network - Eligible Address Change | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test cart install network eligible address change',
                 "PBI: $pbiNumber", 'logLevel:info'])

        testingQuery(installQueryProduct, address1.getZip(), true)
        testingQuery(installQueryProduct, address2.getZip(), false)

        for(userTypeForTesting in userTypesForTesting) {
            UserDetail newUser = new UserUrlLogin().loginReusableUser(userTypeForTesting, pbiNumber, address1, null, '_testing_install_network_negative')

            assert newUser

            String userIndex = newUser.detail.index
            Integer userIndexInteger = userIndex.toInteger()
            settingUpSecondAddressIfNecessary(userIndexInteger)

            assert tryLoad('cart')

            ViewCartPage viewCartPage = new ViewCartPage()
            assert viewCartPage.emptyCart()

            assert Shipping.navigateToShippingPage()
            String shippingXpath = "//input[@value='defaultShippingAddress']/following-sibling::input[@type='submit']"
            assert jsClick(shippingXpath)

            PDPage pdPage = new PDPage()
            assert pdPage.navigateToPDPWithItemNumber(installQueryProduct)

            PDPWhatWeOffer pdpWhatWeOffer = new PDPWhatWeOffer()
            PDPInstallationServicesModal installationServicesModal = new PDPInstallationServicesModal()
            PDPPriceTile pdpPriceTile = new PDPPriceTile()

            boolean wasAbleToAddInstallationService = pdpWhatWeOffer.clickAddInstallationService()
            if (!wasAbleToAddInstallationService) {
                report("Skipping remaining tests due to failure to add installation service for user $userIndex.")
                continue
            }
            boolean isModalPresent = installationServicesModal.isModalPresent()
            if (isModalPresent) {
                assert installationServicesModal.addService()
            }
            assert pdpPriceTile.addToCart(1)

            assert tryLoad('cart')

            String xpath = "//select[@id='quickShippingAddress']"

            if(!waitForElement(xpath)){
                report("Skipping remaining tests due to failure of quick checkout to load for user $userIndex.")
                continue
            }

            Select quickSelectDropdown = new Select(find(xpath))
            quickSelectDropdown.selectByIndex(1)

            assert waitForElement(ViewCartPage.messageAboveXpath)

            List messages = new ViewCartPage().getAboveMessagesData()

            assert messages.size() == 0

/*            def message = messages[0]

            assert message['type'] == "Info"
            assert message['message'] == "The price of installation has been updated to reflect your address change."
            assert message['messageBackgroundColor'] == "rgba(204, 235, 243, 1)"*/

            assert viewCartPage.emptyCart()
        }
        assert closeBrowser()
    }

    void testingQuery(String itemNumber, String zipCode, boolean isZipExpectedToBePartOfInstallNetwork){
        assert (isZipExpectedToBePartOfInstallNetwork == HpCartInstallNetwork.confirmProductZipCodeInstallNetwork(itemNumber, zipCode.substring(0, 5)).size() > 0)
    }

    boolean addShippingAddress(Addresses address){
        String name = address['name']
        String address1 = address['address']
        String address2 = address['address2']
        String zip = address['zip']
        String phone = address['phone']
        String company = address['companyName']

        if(!Shipping.navigateToShippingPage()){
            return false
        }

        return Shipping.createNewUSShippingAddr(name, address1, address2, zip, phone, company)
    }

    void settingUpSecondAddressIfNecessary(Integer userIndex){
        RunWeb r = run()

        int numberOfShippingAddressesAssociatedWithUser = UserUtil.getNumberOfActiveShippingAddressesAssociatedWithUser(userIndex).get(0).get('shipping_address_count')
        if(numberOfShippingAddressesAssociatedWithUser == 2){
            r.log("Proceeding to test since $userIndex has two shipping addresses.")
        }
        else if(numberOfShippingAddressesAssociatedWithUser == 1){
            r.log "Preparing to add credit card and second shipping address."
            assert Billing.navigateToBillingPage()
            assert Billing.addNewCreditCard("Automation", "4111111111111111", '111', "12", '2027')
            assert addShippingAddress(address2)
        }
        else{
            r.log("$userIndex has $numberOfShippingAddressesAssociatedWithUser which isn't supported for this test case.")
        }
    }
}
