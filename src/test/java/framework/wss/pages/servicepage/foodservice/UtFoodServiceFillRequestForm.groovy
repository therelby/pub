package framework.wss.pages.servicepage.foodservice

import above.RunWeb
import wss.pages.servicepage.FoodServiceLayout
import wss.user.UserDetail
import wss.user.UserType
import wss.user.userurllogin.UserUrlLogin

class UtFoodServiceFillRequestForm extends RunWeb {

    static void main(String[] args) {
        new UtFoodServiceFillRequestForm().testExecute([

                browser      : 'chrome',//'chrome',
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
        final int PBI = 599911

        setup([
                author  : 'vdiachuk',
                title   : 'Food Service Request Consultation Form unit test | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'food service form request consultation unit test',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])

        FoodServiceLayout foodServiceLayout = new FoodServiceLayout()
        assert foodServiceLayout.navigate()
        log "--"
        def valuesToSet = [:]
        valuesToSet['name'] = "Sample Name"
        valuesToSet['email'] = "sample@gmail.com"
        valuesToSet['phone'] = "4073454345"
        valuesToSet['company'] = "My Company"
        valuesToSet['address'] = "6315 N Armenia Ave"
        valuesToSet['city'] = "Tampa"
        List stateOptions = selectGetOptions(FoodServiceLayout.stateSelectXpath)?.findAll() { !getAllAttributesSafe(it).keySet().contains('disabled') }?.collect() { getTextSafe(it) }
        valuesToSet['state'] = stateOptions?.shuffled()?.getAt(0)
        log valuesToSet['state']
        valuesToSet['zip'] = "33604"


        List typesFood = findElements(FoodServiceLayout.foodServiceRadioXpath)?.collect() { getTextSafe(it) }
        valuesToSet['typeFood'] = typesFood?.shuffled()?.getAt(0)
        log valuesToSet['typeFood']

        List spaceOptions = selectGetOptions(FoodServiceLayout.spaceSelectXpath)?.findAll() { !getAllAttributesSafe(it).keySet().contains('disabled') }?.collect() { getTextSafe(it) }
        valuesToSet['space'] = spaceOptions?.shuffled()?.getAt(0)
        log valuesToSet['space']

        List squareOptions = selectGetOptions(FoodServiceLayout.squareSelectXpath)?.findAll() { !getAllAttributesSafe(it).keySet().contains('disabled') }?.collect() { getTextSafe(it) }
        valuesToSet['square'] = squareOptions?.shuffled()?.getAt(0)
        log valuesToSet['square']

        List readyOptions = selectGetOptions(FoodServiceLayout.readyStartSelectXpath)?.findAll() { !getAllAttributesSafe(it).keySet().contains('disabled') }?.collect() { getTextSafe(it) }
        valuesToSet['ready'] = readyOptions?.shuffled()?.getAt(0)
        log valuesToSet['ready']

        List budgetOptions = selectGetOptions(FoodServiceLayout.budgetSelectXpath)?.findAll() { !getAllAttributesSafe(it).keySet().contains('disabled') }?.collect() { getTextSafe(it) }
        valuesToSet['budget'] = budgetOptions?.shuffled()?.getAt(0)
        log valuesToSet['budget']

        assert foodServiceLayout.fillRequestConsultationForm(valuesToSet)
        log "--"
        def formData = foodServiceLayout.getRequestConsultationFormData()
        log formData
        assert formData['name'] == valuesToSet['name']
        assert formData['email'] == valuesToSet['email']
        assert formData['phone'] == valuesToSet['phone']
        assert formData['company'] == valuesToSet['company']
        assert formData['address'] == valuesToSet['address']
        assert formData['city'] == valuesToSet['city']
        assert formData['state'] == valuesToSet['state']
        assert formData['zip'] == valuesToSet['zip']
        assert formData['typeFood'] == valuesToSet['typeFood']
        assert formData['space'] == valuesToSet['space']
        assert formData['square'] == valuesToSet['square']
        assert formData['ready'] == valuesToSet['ready']
        assert formData['budget'] == valuesToSet['budget']

        closeBrowser()
        log "--"

        UserUrlLogin userUrlLogin = new UserUrlLogin()
        UserDetail userDetail = userUrlLogin.loginNewUser(UserType.REGULAR_USER, PBI)
        log userDetail
        assert foodServiceLayout.navigate()

        def presetFormData = foodServiceLayout.getRequestConsultationFormData()

        assert presetFormData['name'] == userDetail.detail['name']
        assert presetFormData['email'] == userDetail.detail['email']
        assert presetFormData['phone'] == userDetail.detail.getAt('addresses')['phone']
        assert presetFormData['company'] == userDetail.detail['userCompany']
        assert presetFormData['address'] == userDetail.detail.getAt('addresses')['address']
        assert presetFormData['city'] == userDetail.detail.getAt('addresses')['city']
        assert presetFormData['state'] == userDetail.detail.getAt('addresses')['state']
        assert presetFormData['zip'] == userDetail.detail.getAt('addresses')['zip']
    }

}
