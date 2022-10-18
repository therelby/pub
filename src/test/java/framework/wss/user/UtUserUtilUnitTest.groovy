package framework.wss.user

import above.RunWeb
import all.util.EmailUtil
import wsstest.account.testingtool.UserHelper
import wss.user.UserUtil

class UtUserUtilUnitTest extends RunWeb {
    static void main(String[] args) {
        new UtUserUtilUnitTest().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,//true,
                //  browserVersionOffset: -1,   // use specific browser version
                environment  : 'dev',        // use specific environment
                runType      : 'Debug',//'Regular'          //Debug
                /*   parallelThreads: 1,
               runType: 'Regular' ,
               browserVersionOffset: -1   */
        ])
    }

    def test() {

        setup('vdiachuk', 'user.UserUtil Unit test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test user util userUtil utility',
                 "tfsTcIds:265471", 'logLevel:info'])

        def emailToCheck = "tiffanylanes@att.net"
        def expectedUserId = "2528445"


        //    assert UserUtil.getUserByType('Platinum WebPlus').get("user_id")>0

        //log "User By Email:"+ UserUtil.getUserByEmail(emailToCheck)
        assert UserUtil.getUserByEmail(emailToCheck).get(0).get("index").toString() == expectedUserId

        assert UserUtil.getUserByUserId(expectedUserId as Integer)?.getAt(0)?.email == emailToCheck


        log "Email from Random Registered user:" + UserUtil.getRandomRegisteredUser().get('email')
        assert EmailUtil.isEmail(UserUtil.getRandomRegisteredUser().get('email'))

        log("Supervisor Subordinate users:" + UserUtil.getSupervisorSubordinateUser())
        assert UserUtil.getSupervisorSubordinateUser().get("subordinate_user_index") > 0 && UserUtil.getSupervisorSubordinateUser().get("supervisor_user_index") > 0


        log "Get user Shipping date by userId, shipName:" + UserUtil.getShippingInfoByUserIndex("2528445").get(0).get("shipname")
        assert UserUtil.getShippingInfoByUserIndex(expectedUserId).get(0).get("user_index").toString() == expectedUserId


        log "Get User with Credit Card expired by Year:" + UserUtil.getUserWithCreditCardExpiredByYear('2023')
        assert UserUtil.getUserWithCreditCardExpiredByYear('2023').get('User_Index') > 0

        log "Get Users with Plus Subscription and conditions:" + UserUtil.getUserEnroledPlusWithConditions()
        assert UserUtil.getUserEnroledPlusWithConditions().get("only_active") > 0

        log "UserHelper Class test. Get users with conditions:" + UserHelper.getUserEnroledPlusWithConditions("total_enrollments > 1", "waived_shipping_revenue < fees_paid", "1=1")
        assert UserHelper.getUserEnroledPlusWithConditions("total_enrollments > 1",
                "waived_shipping_revenue < fees_paid", "1=1")
                .get(0).get('user_index') > 0

// not used anymore methods
        /*   log "Users with reorder history:" + UserUtil.getUserWithReoredHistory()
           assert UserUtil.getUserWithReorderHistory().get(0).get('user_index') > 0

           log "USer with Reorder Item that required accessories " + UserUtil.getUserReorderItemRequiredAccessories(2)
           assert UserUtil.getUserReorderItemRequiredAccessories(2).get(0).get('user_index') > 0*/

        log "User With Orders By Type:" + UserUtil.getUserWithOrdersByType('Regular User', 2)
        assert UserUtil.getUserWithOrdersByType('Regular User', 2).get(0).get('user_index') > 0

        log "UserHelper test. Get user with Rapid Order By Item Quantity:" + UserHelper.getUserWithRapidOrderByItemQuantity(20)
//         can be checked  -> https://www.dev.webstaurantstore.com/?login_as_user=[add userId here]
//         and check quantity of Rapid reorder items on page https://www.dev.webstaurantstore.com/reorder.cfm

        log "Get users with over 1000 orders:" + UserUtil.getUsersWithOver1000Orders()
        assert UserUtil.getUsersWithOver1000Orders().get(0).get("user_index") > 0

        log "Recent orders for user:" + UserUtil.getRecentOrdersForUser('10168', 2)
        assert UserUtil.getRecentOrdersForUser('10168', 2).size() == 2

        log "Users with recent autoreorder:" + UserUtil.getUsersWithAutoReorder(2)
        assert UserUtil.getUsersWithAutoReorder(2).size() == 2


        //  Extreamly long execution time!!
        log "User with conditions:" + UserUtil.getUsersWithConditions('1', '0', '0', "NULL", "NULL", "NULL", "NULL", "NULL", "NULL")


        log  UserUtil.getUserDefaultShipToIndex(expectedUserId as Integer)


    }
}
