package above.exec.browsers

import above.RunWeb
import above.web.helpers.WebDriverGrid

/**
 *      Browser Versions List Update
 *      -- saving to the Variable Storage
 */

class BrowserVersionsUpdate extends RunWeb {

    static void main(String[] args) {
        new BrowserVersionsUpdate().testExecute()
    }

    def test() {

        setup('akudin', 'Browser Versions List Update',
             ['product:wss', 'tfsProject:none', 'keywords:framework', 'logLevel:info'])

        log 'Trying to update the browser versions list...'
        log '--'
        WebDriverGrid.updateBrowserVersions()
        log '--'
        log 'Done.'

    }

}
