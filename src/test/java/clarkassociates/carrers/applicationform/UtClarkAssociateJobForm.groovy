package clarkassociates.carrers.applicationform

import above.RunWeb
import clarkassociates.common.ClarkAssociateJobFormPersonalInformation

class UtClarkAssociateJobForm extends RunWeb {
    static void main(String[] args) {
        new UtCareersApplicationFormNavigation().testExecute([
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
        ClarkAssociateJobFormPersonalInformation clarkFormPage = new ClarkAssociateJobFormPersonalInformation()
        clarkFormPage.navigate()
        assert getCurrentUrl() == "https://www.dev.clarkassociatesinc.biz/application"

        assert clarkFormPage.isJobApplicationInformationHeaderPresent()
        assert clarkFormPage.jobApplicationInformationTextPresent()
        assert clarkFormPage.isPersonalInformationContainerPresent()
        assert clarkFormPage.isPersonalInformationTextPresent()
        assert clarkFormPage.isLegalFirstNameTextPresent()
        assert clarkFormPage.isLegalFirstNameRequiredTextPresent()
        assert clarkFormPage.isLegalLastNamePresent()
        def    jobApplicationInformationText = clarkFormPage.getJobApplicationInformationText()
        assert jobApplicationInformationText == clarkFormPage.expectedValues.jobApplicantInformationText
        def    personalInformationText = clarkFormPage.getPersonalInformationText()
        assert personalInformationText == clarkFormPage.expectedValues.personalInformationText
        def    legalFirstNameText = clarkFormPage.getLegalFirstNameText()
        assert legalFirstNameText == clarkFormPage.expectedValues.legalFirstNameText
        def    initialText = clarkFormPage.getInitialText()
        assert initialText == clarkFormPage.expectedValues.initialText
        closeBrowser()


    }
}