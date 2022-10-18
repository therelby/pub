package wap.content.hiddenProduct

import above.RunWeb
import wap.common.WAPLogin
import wap.common.content.hiddenproducts.WAPHpUpdate

/**
 * Unit test check update function for all fields in hidden product update
 *
 * @author ttoanle
 */
class UtWAPHpUpdateFunction extends RunWeb {

    def userName
    def password

    UtWAPHpUpdateFunction(def userName = WAPLogin.usernameDev, def password = WAPLogin.passwordDev) {
        this.userName = userName
        this.password = password
    }

    def test() {

        setup('ttoanle', 'Admin Portal | Hidden Product Update Unit Test',
                ['product:wap', 'tfsProject:Webstaurant.AdminPortal',
                 'keywords:unit test hidden product update',
                 'PBI: 0',
                 'logLevel:info'])
        WAPHpUpdate wapHpUpdate = new WAPHpUpdate(userName, password)

        /**
         * Login to admin portal and load hidden product update page
         */
        if (!wapHpUpdate.loginAndLoadHiddenPageUpdate()) {
            report("Cannot login to admin portal and load hidden product update page")
            closeBrowser()
            return
        }
        waitForElementVisible(wapHpUpdate.updateClass.selectClassXpath)
        waitForElementClickable(wapHpUpdate.updateClass.selectClassXpath)
        waitForPage()

        def update =
                [
                        Class:['Class':'A1 - Pending Content','InProgress':'Yes'],
                        Writer:['Writer':'aanjier'],
                        Curator:['UnAssignCurator':true],
                        ETA:['ClearETA':true],
                        FollowUpDate:['FollowUpDate':'04/14/2022'],
                        Review:['Review':'Waiting on Vendor Info'],
                        Comment:['Comment':'I want to add comment here'],
                        HideUnavailable:['Hide':'Yes','HideNonWeb':'No','CreateELP':'Yes','Unavailable':'No','UnavailableNonWeb':'Yes'],
                        ImageUpdate:['AddPlaceHolder':true,'RejectedImage':true,'Reason':'Reason Rejected']
                ]

        assert wapHpUpdate.selectClassToUpdate(update.get('Class').Class)
        assert wapHpUpdate.selectClassInProgressOption(update.get('Class').InProgress)
        assert wapHpUpdate.selectWriter(update.get('Writer').Writer)
        assert wapHpUpdate.selectUnAssignCurator()
        assert wapHpUpdate.selectClearETA()
        assert wapHpUpdate.setFollowUpDate(update.get('FollowUpDate').FollowUpDate)
        assert wapHpUpdate.selectReview(update.get('Review').Review)
        assert wapHpUpdate.commentOnItem(update.get('Comment').Comment)
        assert wapHpUpdate.selectHideUnavailable(update.get('HideUnavailable'))
        assert wapHpUpdate.selectRejectImage(update.get('ImageUpdate').Reason)
        /**
         * Close browser
         */
        closeBrowser()
    }
}
