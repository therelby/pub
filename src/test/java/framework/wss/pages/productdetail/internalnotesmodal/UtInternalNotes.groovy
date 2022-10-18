package framework.wss.pages.productdetail.internalnotesmodal

import above.RunWeb
import wss.pages.productdetail.PDPInternalNotesModal

import java.time.OffsetDateTime

class UtInternalNotes extends RunWeb {

    static void main(String[] args) {
        new UtInternalNotes().testExecute([:])
//        new TcPDPInternalNotesItemInstructions().testExecute([
//                remoteBrowser: true,        // use remote browser
//                browser: 'edge',            // use specific browser
//                browserVersionOffset: -1,   // use specific browser version
//                environment: 'test',        // use specific environment
//                runType: 'Regular'          // Regular running (with stats saving)
//        ])
    }
    def test() {

        setup('chorne', 'Internal Notes Modal unit test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test modal internal notes',
                 'PBI: 0',
                 'logLevel:info'])

        testGetProductPipelineStatus()
        PDPInternalNotesModal internalNotesModal = new PDPInternalNotesModal()

        String testUrl = "https://www.dev.webstaurantstore.com/avantco-a-19r-hc-29-solid-door-reach-in-refrigerator/178A19RHC.html"

        tryLoad(testUrl)

        // Basics
        assert internalNotesModal.isButtonPresent()
        assert internalNotesModal.isButtonIconPresent()
        assert internalNotesModal.checkButtonIconColor(internalNotesModal.buttonIconXpath, '#cf3700')
        assert internalNotesModal.getButtonDataModified().size()>4
        assert internalNotesModal.getButtonUserName().size()>1
        assert internalNotesModal.openModal()
        assert internalNotesModal.isModalPresent()
        assert internalNotesModal.checkButtonIconColor(internalNotesModal.buttonIconXpath, '#ffffff')
        assert internalNotesModal.checkButtonIconColor(internalNotesModal.buttonLinkXpath, '#188d00')
        assert internalNotesModal.closeModalX()
        assert internalNotesModal.openModal()
        assert internalNotesModal.closeModalButton()
        assert internalNotesModal.deleteButton()

        log "=="

        // with no date and name
        // https://www.dev.webstaurantstore.com/nor-lake-klf68-c-kold-locker-6-x-8-x-6-7-indoor-walk-in-freezer/596KLF68.html

        testUrl = "https://www.dev.webstaurantstore.com/nor-lake-klf68-c-kold-locker-6-x-8-x-6-7-indoor-walk-in-freezer/596KLF68.html"

        tryLoad(testUrl)

        assert internalNotesModal.isButtonPresent()
        assert internalNotesModal.isButtonIconPresent()
        assert internalNotesModal.checkButtonIconColor(internalNotesModal.buttonIconXpath, '#cf3700')
        assert internalNotesModal.getButtonDataModified() == ''
        assert internalNotesModal.getButtonUserName() == ''
        assert internalNotesModal.openModal()
        assert internalNotesModal.isModalPresent()
        assert internalNotesModal.checkButtonIconColor(internalNotesModal.buttonIconXpath, '#ffffff')
        assert internalNotesModal.checkButtonIconColor(internalNotesModal.buttonLinkXpath, '#188d00')
        assert internalNotesModal.closeModalX()
        assert internalNotesModal.openModal()
        assert internalNotesModal.closeModalButton()
        assert internalNotesModal.deleteButton()

        log "=="

        tryLoad("homepage")
        assert !internalNotesModal.isButtonPresent()
        assert internalNotesModal.getButtonDataModified() == ''
        assert internalNotesModal.getButtonUserName() == ''
        assert internalNotesModal.deleteButton()

        log "=="
        def dataByItemNumber = internalNotesModal.getDBData("100FOLDCHAFE")
        Map parametersMap =    ["item_number":"100FOLDCHAFE","num_of_comments":1,"location":851,"return_kit_data":false,"virtual_member_list":null]
        def dataWithParameters =  internalNotesModal.getDBData(parametersMap)
        assert  dataByItemNumber == dataWithParameters

    }

    void testGetProductPipelineStatus() {
        def internalNotes = new PDPInternalNotesModal()
        def data = internalNotes.getProductPipelineStatus([ "962wbs1432", "32644850" ])
        assert OffsetDateTime.now() > data["962wbs1432"]['lastCachedDate']
    }
}
