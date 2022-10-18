package clarkassociates.carrers.applicationform

import above.RunWeb
import clarkassociates.common.ClarkAssociateJobFormLocation


class UtClarkAssociateApplicationLocation extends  RunWeb{

    static void main(String[] args) {
        new UtClarkAssociateApplicationLocation().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug'
        ])
    }

    void test() {
        setup([author  : 'nyadav',
               title   : 'Clark Associates',
               product : 'ca',
               PBI     : 0,
               project : 'Clark Associates',
               keywords: 'careers',
               logLevel: 'info'
        ])

        ClarkAssociateJobFormLocation clarkFormPage = new ClarkAssociateJobFormLocation()
        clarkFormPage.navigate()
        assert getCurrentUrl() == "https://www.dev.clarkassociatesinc.biz/application"
        assert clarkFormPage.isFormContainerPresent()
        assert clarkFormPage.isLocationHeaderPresent()
        assert clarkFormPage.isItalicTextPresent()
        assert clarkFormPage.isAddressPresent()
        assert clarkFormPage.isAddressInputBoxPresent()
        assert clarkFormPage.isAddress2InputBoxClickable()
        assert clarkFormPage.isAddressLabelPresent()
        assert clarkFormPage.isZipLabelPresent()
        def    jobApplicationLocationText = clarkFormPage.getLocationText()
        assert jobApplicationLocationText == clarkFormPage.expectedValues.locationText
        def    ItalicText = clarkFormPage.getItalicText()
        assert ItalicText == clarkFormPage.expectedValues.italicText
        def    jobApplicationAddressText = clarkFormPage.getAddressText()
        assert jobApplicationAddressText == clarkFormPage.expectedValues.addressText
        def    jobApplicationAddressRequiredText = clarkFormPage.getAddressRequiredText()
        assert jobApplicationAddressRequiredText == clarkFormPage.expectedValues.requiredText



        closeBrowser()



    }
}
