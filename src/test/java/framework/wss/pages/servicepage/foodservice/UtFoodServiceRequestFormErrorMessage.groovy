package framework.wss.pages.servicepage.foodservice

import above.RunWeb
import wss.pages.servicepage.FoodServiceLayout

class UtFoodServiceRequestFormErrorMessage extends RunWeb {

    static void main(String[] args) {
        new UtFoodServiceRequestFormErrorMessage().testExecute([

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
                title   : 'Food Service Request Consultation Form Error Message unit test | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'food service form request consultation error message unit test',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])

        FoodServiceLayout foodServiceLayout = new FoodServiceLayout()
        assert foodServiceLayout.navigate()
        assert foodServiceLayout.clickRequestConsultation()

        def errorMessagesData = foodServiceLayout.getRequestConsultationFormErrorMessages()
        log errorMessagesData.toString()
        assert errorMessagesData['name'] == "Contact Name is a required field"
        assert errorMessagesData['email'] == "Email Address is a required field"
        assert errorMessagesData['phone'] == "Phone Number is a required field"
        assert errorMessagesData['company'] == "Company Name is a required field"
        assert errorMessagesData['address'] == "Address is a required field"
        assert errorMessagesData['city'] == "City is a required field"
        assert errorMessagesData['state'] == "Please select a state"
        assert errorMessagesData['zip'] == "Zipcode is a required field"
        assert errorMessagesData['typeFood'] == "Please select an option"
        assert errorMessagesData['space'] == "Please select an option"
        assert errorMessagesData['square'] == "Please select an option"
        assert errorMessagesData['ready'] == "Please select an option"
        assert errorMessagesData['budget'] == "Please select an option"


    }
}
