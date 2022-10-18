package framework.wss.pages.productdetail.internalnotesmodal

import above.RunWeb
import org.openqa.selenium.WebElement
import wss.pages.productdetail.PDPInternalNotesModal

class UtInternalNotesVendorInstruction extends RunWeb {

    static void main(String[] args) {
        new UtInternalNotesVendorInstruction().testExecute([:])
//        new TcPDPInternalNotesItemInstructions().testExecute([
//                remoteBrowser: true,        // use remote browser
//                browser: 'edge',            // use specific browser
//                browserVersionOffset: -1,   // use specific browser version
//                environment: 'test',        // use specific environment
//                runType: 'Regular'          // Regular running (with stats saving)
//        ])
    }

    def test() {

        setup('vdiachuk', 'Internal Notes Modal Vendor Instructions unit test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test modal internal notes vendor instructions',
                 'PBI: 0',
                 'logLevel:info'])


        String testUrl = "https://www.dev.webstaurantstore.com/homer-laughlin-hl20246800-ameriwhite-alexa-11-1-2-bright-white-china-pasta-plate-case/42968002024.html"
        tryLoad(testUrl)
        click(PDPInternalNotesModal.buttonLinkXpath)
        sleep(2000)

        PDPInternalNotesModal internalNotesModal = new PDPInternalNotesModal()

        Map vendorInstructionData = internalNotesModal.getVendorInstructionData()
        log vendorInstructionData.keySet()

        WebElement contactInfoElement = vendorInstructionData.get("Contact Info")
        String contactInfoHtml = getAttributeSafe(contactInfoElement, "innerHTML")

        WebElement warrantyElement = vendorInstructionData.get("Defective / Warranty")
        String warrantyHtml = getAttributeSafe(warrantyElement, "innerHTML")
        log "--"
        log contactInfoHtml
        // Getting record from DB 0 - Contact Info, 1 - Warranty .. etc
        def dataDB = internalNotesModal.getDBData("42968002024").get(1).get(1)
        log "--"
        String messageDB = dataDB['message']
        log messageDB
        log "--"
        def contactInfoHtmlReplace = contactInfoHtml.replaceAll("\\s+ ", "")
        def warrantyHtmlReplace = warrantyHtml.replaceAll("\\s+ ", "")
        def messageDBReplace = messageDB.replaceAll("\\s+ ", "")
        assert warrantyHtmlReplace == messageDBReplace


    }
}
