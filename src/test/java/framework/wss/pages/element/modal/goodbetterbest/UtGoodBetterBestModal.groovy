package framework.wss.pages.element.modal.goodbetterbest

import above.RunWeb
import wss.pages.element.modal.GoodBetterBestModal

class UtGoodBetterBestModal extends RunWeb {

    static void main(String[] args) {
        new UtGoodBetterBestModal().testExecute([

                browser      : 'chrome',
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
        setup([
                author  : 'vdiachuk',
                title   : 'Good Better Best, PDP, Modal | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'good better best modal ',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])
        GoodBetterBestModal goodBetterBestModal = new GoodBetterBestModal()

        // Positive and negative PDP
        tryLoad('/lancaster-table-seating-22-x-22-black-3-standard-height-column-cast-iron-table-base-with-flat-tech-equalizer-table-levelers/349C22S3SEQ.html')

        assert goodBetterBestModal.getItemsData() == []
        assert goodBetterBestModal.closeModalByX() == false
        assert goodBetterBestModal.waitForModal() == false
        assert goodBetterBestModal.closeModalByX() == false

        // clicking ribbon to open modal
        log click("//span[@class='block text-white transform skew-x-15 skew-y-0']")

        //  sleep(500)
        assert goodBetterBestModal.waitForModal()
        assert goodBetterBestModal.getItemsData().size() == 3
        assert goodBetterBestModal.getHeaderText() == GoodBetterBestModal.headerModalText

        assert goodBetterBestModal.closeModalByX()
        assert goodBetterBestModal.closeModalByX() == false

        // Negative home page
        tryLoad()
        assert goodBetterBestModal.closeModalByX() == false
        assert goodBetterBestModal.getItemsData() == []
        assert goodBetterBestModal.waitForModal() == false
        assert goodBetterBestModal.closeModalByX() == false

        //
        // Listing Page
        //
        log "--"
        tryLoad('/42543/restaurant-table-bases.html')
        assert goodBetterBestModal.getItemsData() == []
        assert goodBetterBestModal.closeModalByX() == false
        assert goodBetterBestModal.waitForModal() == false
        assert goodBetterBestModal.closeModalByX() == false
        log "--"
        // clicking ribbon to open modal
        log click("//span[@class='block text-white transform skew-x-15 skew-y-0']")
        assert goodBetterBestModal.waitForModal()
        assert goodBetterBestModal.isPresent()
        assert goodBetterBestModal.getItemsData().size() == 3
        assert goodBetterBestModal.closeModalByX()

    }
}
