package framework.wss.webadmin.specializedpage

import above.RunWeb
import wss.myaccount.AutoReorder
import wss.myaccount.DetailsSettings
import wss.myaccount.MyAccount
import wss.myaccount.Net30Terms
import wss.myaccount.PlusAccountSubscription
import wss.user.UserQuickLogin
import wss.webadmin.WebAdmin
import wss.webadmin.specializedpage.SpecializedPageUpdate

class UtSpecializedPageUpdate extends RunWeb{
    def test() {

        setup('vdiachuk', 'Unit test Specialized Page Update class | NewWebAdim ' +
                'Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test specialized page update newwebadmin webadmin ',
                 "tfsTcIds:265471", 'logLevel:info'])

        tryLoad("homepage")
        WebAdmin.loginToWebAdmin()
        SpecializedPageUpdate spUpdate = new SpecializedPageUpdate(17482)
        assert spUpdate.navigateToSpUpdate()
        assert  spUpdate.isSpUpdate()
        assert !spUpdate.isShowCustomizableChecked()
        assert spUpdate.checkShowCustomizable()
        assert spUpdate.isShowCustomizableChecked()
        assert spUpdate.uncheckShowCustomizable()
        assert !spUpdate.isShowCustomizableChecked()
/*

         spUpdate = new SpecializedPageUpdate(18459)
        assert spUpdate.navigateToSpUpdate()
        assert  spUpdate.isSpUpdate()
        assert !spUpdate.isShowCustomizableChecked()
        assert spUpdate.checkShowCustomizable()
        assert spUpdate.isShowCustomizableChecked()
        assert spUpdate.uncheckShowCustomizable()
        assert !spUpdate.isShowCustomizableChecked()

        spUpdate = new SpecializedPageUpdate(6223)
        assert spUpdate.navigateToSpUpdate()
        assert  spUpdate.isSpUpdate()
        assert !spUpdate.isShowCustomizableChecked()
        assert spUpdate.checkShowCustomizable()
        assert spUpdate.update()

        //go other place
        tryLoad("homepage")
        spUpdate = new SpecializedPageUpdate(6223)
        assert spUpdate.navigateToSpUpdate()
        assert spUpdate.isSpUpdate()
        assert spUpdate.isShowCustomizableChecked()
        assert spUpdate.uncheckShowCustomizable()
*/



    }
}
