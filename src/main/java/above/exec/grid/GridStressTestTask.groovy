package above.exec.grid

import above.RunWeb

class GridStressTestTask extends RunWeb {

    static void main(String[] args) {
        new GridStressTestTask().testExecute([ remoteBrowser: true, runType: 'Regular' ])
    }

    void test() {

        setup([ author: 'akudin', product : 'wss', project: 'Webstaurant.StoreFront',
                title: 'Grid Stress Task', keywords: 'debug', PBI: 1, logLevel: 'info' ])

        log "Task started"

        tryLoad()
        log getTitle()
        setText('//input[@id="searchval"]', 'kitchen')
        clickAndTryLoad('//button[@value="Search"]', 'https://www.dev.webstaurantstore.com/search/kitchen.html')
        log getTitle()
        tryLoad('cart')
        log getTitle()

    }

}
