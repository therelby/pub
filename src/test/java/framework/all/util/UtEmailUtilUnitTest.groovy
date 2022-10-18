package framework.all.util

import above.RunWeb
import all.util.EmailUtil
import org.openqa.selenium.WebDriver

class UtEmailUtilUnitTest extends RunWeb {

    def test() {

        setup('vdiachuk', 'Email Utility functionality unit test | Framework Self Testing Tool',
              ['product:wss', 'tfsProject:Webstaurant.StoreFront',
               "tfsTcIds:265471",
               'keywords:unit test email generator emails ',
               'logLevel:debug'])

        int tryQuantity = 3

        def emailList = ["sfsdfsdff112@sdfs3434.com",
                         "121221240-34343@121-8676.com",
                         "q@f.cm",
                         "q=-@f.com",

        ]
        def wrongEmailList = [
                              "q=-+@+_+.cm",
                              "safdsdf.cm",
                              "qsdfsdf@sfdcm",
        ]
        emailList.each {
            assert EmailUtil.isEmail(it)
        }
        wrongEmailList.each {
            assert !EmailUtil.isEmail(it)
        }
        for (int i = 0;i<tryQuantity;i++){
            assert EmailUtil.isEmail(EmailUtil.getEmail())
        }

    }
}
