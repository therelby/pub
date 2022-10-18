package framework.wss.pages.productdetail.internalnotesmodal

import above.RunWeb
import org.openqa.selenium.WebElement
import wss.pages.productdetail.PDPInternalNotesModal

class UtInternalNotesDateConverter extends RunWeb {

    static void main(String[] args) {
        new UtInternalNotesDateConverter().testExecute([:])
//        new TcPDPInternalNotesItemInstructions().testExecute([
//                remoteBrowser: true,        // use remote browser
//                browser: 'edge',            // use specific browser
//                browserVersionOffset: -1,   // use specific browser version
//                environment: 'test',        // use specific environment
//                runType: 'Regular'          // Regular running (with stats saving)
//        ])
    }

    def test() {

        setup('chorne', 'Internal Notes Modal Vendor Instructions unit test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test modal internal notes vendor instructions',
                 'PBI: 0',
                 'logLevel:info'])

        PDPInternalNotesModal internalNotesModal = new PDPInternalNotesModal()

        assert "08/22/1990" == internalNotesModal.internalNotesDateConverter('1990-08-22 12:27:05.0','MM/dd/yyyy')
        log "oldPattern: 1990-08-22 12:27:05.0, newPattern: MM/dd/yyyy == Result: " + internalNotesModal.internalNotesDateConverter('1990-08-22 12:27:05.0','MM/dd/yyyy')
        log"--"

        assert "08/22/1990 12:27:05" == internalNotesModal.internalNotesDateConverter('1990-08-22 12:27:05.0','MM/dd/yyyy HH:mm:ss')
        log "oldPattern: 1990-08-22 12:27:05.0, newPattern: MM/dd/yyyy HH:mm:ss === Result: " + internalNotesModal.internalNotesDateConverter('1990-08-22 12:27:05.0','MM/dd/yyyy HH:mm:ss')
        log"--"

        assert "08/22/1990 12:27:05 PM" == internalNotesModal.internalNotesDateConverter('1990-08-22 12:27:05.0','MM/dd/yyyy hh:mm:ss a')
        log "oldPattern: 1990-08-22 12:27:05.0, newPattern: MM/dd/yyyy hh:mm:ss a == Result: " + internalNotesModal.internalNotesDateConverter('1990-08-22 12:27:05.0','MM/dd/yyyy hh:mm:ss a')

        log"=="

        assert "08/22/1990" == internalNotesModal.internalNotesDateConverter('1990-08-22 12:27:05','MM/dd/yyyy')
        log "oldPattern: 1990-08-22 12:27:05, newPattern: MM/dd/yyyy == Result: " + internalNotesModal.internalNotesDateConverter('1990-08-22 12:27:05','MM/dd/yyyy')
        log"--"

        assert "08/22/1990 12:27:05" == internalNotesModal.internalNotesDateConverter('1990-08-22 12:27:05','MM/dd/yyyy HH:mm:ss')
        log "oldPattern: 1990-08-22 12:27:05, newPattern: MM/dd/yyyy HH:mm:ss === Result: " + internalNotesModal.internalNotesDateConverter('1990-08-22 12:27:05','MM/dd/yyyy HH:mm:ss')
        log"--"

        assert "08/22/1990 12:27:05 PM" == internalNotesModal.internalNotesDateConverter('1990-08-22 12:27:05','MM/dd/yyyy hh:mm:ss a')
        log "oldPattern: 1990-08-22 12:27:05, newPattern: MM/dd/yyyy hh:mm:ss a == Result: " + internalNotesModal.internalNotesDateConverter('1990-08-22 12:27:05','MM/dd/yyyy hh:mm:ss a')

        log"=="

        assert "08/22/1990" == internalNotesModal.internalNotesDateConverter('1990-08-22T12:27:05.0','MM/dd/yyyy')
        log "oldPattern: 1990-08-22T12:27:05.0, newPattern: MM/dd/yyyy == Result: " + internalNotesModal.internalNotesDateConverter('1990-08-22T12:27:05.0','MM/dd/yyyy')
        log"--"

        assert "08/22/1990 12:27:05" == internalNotesModal.internalNotesDateConverter('1990-08-22T12:27:05.0','MM/dd/yyyy HH:mm:ss')
        log "oldPattern: 1990-08-22T12:27:05.0, newPattern: MM/dd/yyyy HH:mm:ss === Result: " + internalNotesModal.internalNotesDateConverter('1990-08-22T12:27:05.0','MM/dd/yyyy HH:mm:ss')
        log"--"

        assert "08/22/1990 12:27:05 PM" == internalNotesModal.internalNotesDateConverter('1990-08-22T12:27:05.0','MM/dd/yyyy hh:mm:ss a')
        log "oldPattern: 1990-08-22T12:27:05.0, newPattern: MM/dd/yyyy hh:mm:ss a == Result: " + internalNotesModal.internalNotesDateConverter('1990-08-22T12:27:05.0','MM/dd/yyyy hh:mm:ss a')

        log"=="

        assert "08/22/1990" == internalNotesModal.internalNotesDateConverter('1990-08-22T12:27:05','MM/dd/yyyy')
        log "oldPattern: 1990-08-22T12:27:05.0, newPattern: MM/dd/yyyy == Result: " + internalNotesModal.internalNotesDateConverter('1990-08-22T12:27:05','MM/dd/yyyy')
        log"--"

        assert "08/22/1990 12:27:05" == internalNotesModal.internalNotesDateConverter('1990-08-22T12:27:05.0','MM/dd/yyyy HH:mm:ss')
        log "oldPattern: 1990-08-22T12:27:05.0, newPattern: MM/dd/yyyy HH:mm:ss === Result: " + internalNotesModal.internalNotesDateConverter('1990-08-22T12:27:05.0','MM/dd/yyyy HH:mm:ss')
        log"--"

        assert "08/22/1990 12:27:05 PM" == internalNotesModal.internalNotesDateConverter('1990-08-22T12:27:05','MM/dd/yyyy hh:mm:ss a')
        log "oldPattern: 1990-08-22T12:27:05.0, newPattern: MM/dd/yyyy hh:mm:ss a == Result: " + internalNotesModal.internalNotesDateConverter('1990-08-22T12:27:05','MM/dd/yyyy hh:mm:ss a')

        log"=="

        assert "08/22/1990" == internalNotesModal.internalNotesDateConverter('1990-08-22T12:27:05','MM/dd/yyyy')
        log "oldPattern: 1990-08-22T12:27:05, newPattern: MM/dd/yyyy == Result: " + internalNotesModal.internalNotesDateConverter('1990-08-22T12:27:05','MM/dd/yyyy')
        log"--"

        assert "08/22/1990 12:27:05" == internalNotesModal.internalNotesDateConverter('1990-08-22T12:27:05','MM/dd/yyyy H:mm:ss')
        log "oldPattern: 1990-08-22T12:27:05, newPattern: MM/dd/yyyy HH:mm:ss === Result: " + internalNotesModal.internalNotesDateConverter('1990-08-22T12:27:05','MM/dd/yyyy H:mm:ss')
        log"--"

        assert "08/22/1990 12:27:05 PM" == internalNotesModal.internalNotesDateConverter('1990-08-22T12:27:05','MM/dd/yyyy h:mm:ss a')
        log "oldPattern: 1990-08-22T12:27:05, newPattern: MM/dd/yyyy hh:mm:ss a == Result: " + internalNotesModal.internalNotesDateConverter('1990-08-22T12:27:05','MM/dd/yyyy h:mm:ss a')

    }
}
