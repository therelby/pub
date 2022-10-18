package statuscheck.common.element

import above.RunWeb
import statuscheck.common.page.StatusDetails
import statuscheck.common.page.StatusHomePage

class UtStatusCardHome extends RunWeb {

    static void main(String[] args) {
        new UtStatusCardHome().testExecute([

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
        final int PBI = 0

        setup('vdiachuk', 'Status Card | Framework  unit tests',
                ['product:statuscheck|dev',
                 "tfsProject: Webstaurant.StoreFront",
                 'keywords: status card',
                 "PBI: $PBI",
                 'logLevel:info',])

        StatusCheckLogin login = new StatusCheckLogin()

        log "Login: " + login.loginDefault()

        StatusCardHome statusCardHome = new StatusCardHome(0)
        log "xpath: " + statusCardHome.cartBlockXpath

        assert verifyElement(statusCardHome.cartBlockXpath)
        assert statusCardHome.getCardTitle().size() > 1
        assert statusCardHome.isCardPresent()

        StatusCardHome statusCardHome1 = new StatusCardHome('Another CAMP App! 3')
        log "xpath1: " + statusCardHome1.cartBlockXpath

        assert verifyElement(statusCardHome1.cartBlockXpath)
        assert statusCardHome1.isCardPresent()
        assert statusCardHome1.getCardTitle() == 'Another CAMP App! 3'
        log "Color1: " + statusCardHome1.getTopColor()

        Boolean startStatus = statusCardHome1.isStarChecked()

        assert statusCardHome1.clickStar()
        assert statusCardHome1.isStarChecked() == !startStatus
        assert statusCardHome1.clickStar()
        assert statusCardHome1.isStarChecked() == startStatus


        assert statusCardHome.clickDetails()
        waitForPage()
        StatusDetails statusDetails = new StatusDetails()
        assert statusDetails.isStatusDetails()

        // navigating back
        StatusHomePage statusHomePage = new StatusHomePage()
        assert  statusHomePage.navigate()

        StatusCardHome statusCardHome49 = new StatusCardHome(49)
        startStatus = statusCardHome49.isStarChecked()

        assert statusCardHome49.clickStar()
        assert statusCardHome49.isStarChecked() == !startStatus
        assert statusCardHome49.clickStar()
        assert statusCardHome49.isStarChecked() == startStatus
        log "Color49: " + statusCardHome1.getTopColor()

        assert statusCardHome49.clickDetails()
         waitForPage()
         statusDetails = new StatusDetails()
         assert statusDetails.isStatusDetails()

    }
}
