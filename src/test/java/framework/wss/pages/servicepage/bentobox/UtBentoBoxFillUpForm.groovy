package framework.wss.pages.servicepage.bentobox

import above.RunWeb
import wss.pages.servicepage.BentoBoxPage

class UtBentoBoxFillUpForm extends RunWeb {

    static void main(String[] args) {
        new UtBentoBoxFillUpForm().testExecute([
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
                title   : 'Bento Box Form Unit Test',
                PBI     : pbi,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'bento box form unit test',
                logLevel: 'info'
        ])

        BentoBoxPage bentoBoxPage = new BentoBoxPage()

        log("--")
        def valuesToSet = [:]
        valuesToSet['name'] = "Sample Name"
        valuesToSet['restaurantName'] = "Sample Restaurant"
        valuesToSet['email'] = "sample@gmail.com"
        valuesToSet['phoneNumber'] = "(407) 345-4345"
        valuesToSet['restaurantZipCode'] = "33600"
        valuesToSet['privacyPolicy'] = true
        log(valuesToSet)
        assert bentoBoxPage.navigate()
        assert bentoBoxPage.fillBentoBoxForm(valuesToSet)

        log "--"
        def formData = bentoBoxPage.getBentoBoxFormData()
        log(formData)
        assert formData['name'] == valuesToSet['name']
        assert formData['restaurantName'] == valuesToSet['restaurantName']
        assert formData['email'] == valuesToSet['email']
        assert formData['phoneNumber'] == valuesToSet['phoneNumber']
        assert formData['restaurantZipCode'] == valuesToSet['restaurantZipCode']
        assert formData['privacyPolicy'] == valuesToSet['privacyPolicy']

        closeBrowser()
    }
}