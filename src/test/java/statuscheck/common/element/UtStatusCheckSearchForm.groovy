package statuscheck.common.element

import above.RunWeb

class UtStatusCheckSearchForm extends RunWeb{
    static void main(String[] args) {
        new UtStatusCheckSearchForm().testExecute([

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

        setup('vdiachuk', 'Status Check Search Form | Framework  unit tests',
                ['product:statuscheck|dev',
                 "tfsProject: Webstaurant.StoreFront",
                 'keywords: status check search form',
                 "PBI: $PBI",
                 'logLevel:info',])

        StatusCheckLogin login = new StatusCheckLogin()
        log "Login Success: "+ login.loginDefault()

        StatusSearchFormHome statusSearchFormHome = new StatusSearchFormHome()
        String textToSet = "Camp"
        assert  statusSearchFormHome.setSearchText(textToSet)
        assert getAttributeSafe(StatusSearchFormHome.searchInputXpath,'value') == textToSet
        assert statusSearchFormHome.clearSearchForm()
        assert getAttributeSafe(StatusSearchFormHome.searchInputXpath,'value') == ''

    }
}
