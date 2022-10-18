package clarkassociates.carrers.applicationform


import above.RunWeb
import clarkassociates.common.ClarkAssociateApplicationFormPage

class UtCareersApplicationFormNavigation extends RunWeb {

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

        ClarkAssociateApplicationFormPage clarkForm = new ClarkAssociateApplicationFormPage()
        clarkForm.navigate()
        assert getCurrentUrl() == "https://www.dev.clarkassociatesinc.biz/application"
    }
}
