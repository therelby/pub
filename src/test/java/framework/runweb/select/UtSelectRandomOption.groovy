package framework.runweb.select

import above.RunWeb
import groovy.time.TimeCategory

class UtSelectRandomOption extends RunWeb {

    static void main(String[] args) {
        new UtSelectRandomOption().testExecute([

                browser      : 'chrome',//'chrome',
                remoteBrowser: true,
                //  browserVersionOffset: -1,   // use specific browser version
                environment  : 'dev',        // use specific environment
                runType      : 'Debug',//'Regular'          //Debug
                /*   parallelThreads: 1,
               runType: 'Regular' ,
               browserVersionOffset: -1   */
        ])
    }

    def test() {
        final int PBI = 562613
        setup([
                author  : 'vdiachuk',
                title   : 'Select Random Option Unit tests - RunWeb | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'select runweb random option',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])

        tryLoad("/american-tables-seating-as-36vn-3-4-3-4-circle-v-shape-back-corner-booth-36-high/132AS36VN34.html")
        def dataOn = new Date()
        String selectXpath = "//select[@data-testid='accessory-dropdown-1']"
        (0..9).each {
            if (!selectRandomOption(selectXpath)) {
                log takeScreenshot()
            }
            log getAttributeSafe(selectFirstSelectedOption(selectXpath), "value")
        }

        def dataOff = new Date()
        log '0' + TimeCategory.minus(dataOn, dataOff)
        //    with disabled options
        tryLoad("/g/3730/t-s-b-0206-deck-mounted-single-hole-pantry-faucet-with-12-swing-nozzle")
        String selectSecondXpath = "//select[@data-testid='virtual-grouping-select']"
        dataOn = new Date()
        (0..9).each {
            if (!selectRandomOption(selectSecondXpath)) {
                log takeScreenshot()
            }
            log getAttributeSafe(selectFirstSelectedOption(selectSecondXpath), "value")
        }
        dataOff = new Date()
        log '1' + TimeCategory.minus(dataOn, dataOff)


        // no webrestaurant check
        tryLoad("https://the-internet.herokuapp.com/dropdown")
        String selectThirdXpath = "//select[@id='dropdown']"
        dataOn = new Date()
        (0..9).each {
            if (!selectRandomOption(selectThirdXpath)) {
                log takeScreenshot()
            }
            log getAttributeSafe(selectFirstSelectedOption(selectThirdXpath), "value")
        }
        dataOff = new Date()
        log '2' + TimeCategory.minus(dataOn, dataOff)
        // no select
        assert selectRandomOption("//select[@id='FAKE']") == false
    }
}


