package framework.runweb.select

import above.RunWeb

class UtSelectRandomOptionWithValue extends RunWeb {
    static void main(String[] args) {
        new UtSelectRandomOptionWithValue().testExecute([

                browser      : 'safari',//'chrome',
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
        final int PBI = 562613
        setup([
                author  : 'vdiachuk',
                title   : 'Select Random Option With Value Unit tests - RunWeb | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'select runweb random option value',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])
        tryLoad("/american-tables-seating-as-36vn-3-4-3-4-circle-v-shape-back-corner-booth-36-high/132AS36VN34.html")

        String selectXpath = "//select[@data-testid='accessory-dropdown-1']"
        (0..9).each {
            assert selectRandomOptionWithValue(selectXpath) == getAttributeSafe(selectFirstSelectedOption(selectXpath), "value")
        }
        //    with disabled options
        tryLoad("/g/3730/t-s-b-0206-deck-mounted-single-hole-pantry-faucet-with-12-swing-nozzle")
        String selectSecondXpath = "//select[@data-testid='virtual-grouping-select']"

        (0..9).each {
            assert selectRandomOptionWithValue(selectSecondXpath) == getAttributeSafe(selectFirstSelectedOption(selectSecondXpath), "value")
        }

        // other domain check
        tryLoad("https://the-internet.herokuapp.com/dropdown")
        String selectThirdXpath = "//select[@id='dropdown']"

        (0..9).each {
            assert selectRandomOptionWithValue(selectThirdXpath) ==  getAttributeSafe(selectFirstSelectedOption(selectThirdXpath), "value")
        }

        // no select
        assert selectRandomOptionWithValue("//select[@id='FAKE']") == null

    }
}
