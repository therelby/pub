package statuscheck.common.page

import above.RunWeb
import statuscheck.common.element.StatusCardHome
import statuscheck.common.element.StatusCheckLogin

class UtStatusHomePage extends RunWeb {
    static void main(String[] args) {
        new UtStatusHomePage().testExecute([

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

        setup('vdiachuk', 'Status Check Home page | Framework  unit tests',
                ['product:statuscheck|dev',
                 "tfsProject: Webstaurant.StoreFront",
                 'keywords: status home page',
                 "PBI: $PBI",
                 'logLevel:info',])

        StatusCheckLogin login = new StatusCheckLogin()


        log "login success: " + login.loginDefault()

        StatusHomePage statusHomePage = new StatusHomePage()
        assert statusHomePage.navigate()
        assert statusHomePage.isStatusHomePage()

        def cardNames = statusHomePage.getAllCardNames()
        assert cardNames.size() > 5
        assert statusHomePage.getCardsQuantity() > 5

        List<StatusCardHome> cards = statusHomePage.getAllCards()

        for (int i = 0; i < cards.size(); i++) {
            def card = cards[i]
            assert card.getCardTitle() == cardNames[i]
            def cardByIndex = new StatusCardHome(i)
            assert cardByIndex.getCardTitle() == cardNames[i]
            assert cardByIndex.isCardPresent()
            assert card.isCardPresent()
            assert card.isStarChecked() == cardByIndex.isStarChecked()
        }
        String lastRefreshTime = statusHomePage.getLastRefreshTime()
        log "Refresh Time: " + lastRefreshTime
        assert lastRefreshTime.contains("AM") || lastRefreshTime.contains("PM")

    }
}
