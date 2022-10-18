package framework.wss.pages.servicepage.bentobox

import above.RunWeb
import wss.pages.servicepage.BentoBoxPage

class UtBentoBoxFormElements extends RunWeb {

    static void main(String[] args) {
        new UtBentoBoxFormElements().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    int pbi = 0

    void test() {
        setup([
                author  : 'mnazat',
                title   : 'Bento Box Form Elements Unit Test',
                PBI     : pbi,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'bento box form elements unit test',
                logLevel: 'info'
        ])

        BentoBoxPage bentoBoxPage = new BentoBoxPage()

        assert bentoBoxPage.navigate()
        assert bentoBoxPage.getBentoBoxFormLabelElements() != null
        assert bentoBoxPage.getBentoBoxFormLabelElements().size() > 0
        assert bentoBoxPage.getBentoBoxFormLabelElements().size() == 5

        def bentoBoxFormLabels = bentoBoxPage.getFormLabels()
        assert bentoBoxFormLabels.size() > 0
        assert bentoBoxFormLabels.size() == 5
        assert bentoBoxFormLabels[0] == "Name"
        assert bentoBoxFormLabels[1] == "Restaurant Name"
        assert bentoBoxFormLabels[2] == "Email"
        assert bentoBoxFormLabels[3] == "Phone Number"
        assert bentoBoxFormLabels[4] == "Restaurant Zip Code"

        assert bentoBoxPage.getBentoBoxFormInputElements() != null
        assert bentoBoxPage.getBentoBoxFormInputElements().size() > 0
        assert bentoBoxPage.getBentoBoxFormInputElements().size() == 5

        assert bentoBoxPage.getPrivacyPolicyLinkElements() != null
        assert bentoBoxPage.getPrivacyPolicyLinkElements().size() > 0
        assert bentoBoxPage.getPrivacyPolicyLinkElements().size() == 4

        def bentBoxPrivacyPolicyLinks = bentoBoxPage.getPrivacyPolicyLinks()
        assert bentBoxPrivacyPolicyLinks != null
        assert bentBoxPrivacyPolicyLinks.size() > 0
        assert bentBoxPrivacyPolicyLinks.size() == 4
        assert bentBoxPrivacyPolicyLinks[0].contains("/policies.html")
        assert bentBoxPrivacyPolicyLinks[1].contains("/policies.html#Privacy_Policy")
        assert bentBoxPrivacyPolicyLinks[2] == "https://getbento.com/terms/"
        assert bentBoxPrivacyPolicyLinks[3] == "https://getbento.com/privacy/"

        closeBrowser()
    }
}