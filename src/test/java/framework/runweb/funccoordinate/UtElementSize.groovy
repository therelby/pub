package framework.runweb.funccoordinate

import above.RunWeb
import org.openqa.selenium.WebElement

class UtElementSize extends RunWeb {
    static void main(String[] args) {
        new UtElementSize().testExecute([

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
                title   : 'Element Size, RunWeb | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'element size runweb core',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])

        tryLoad()
        def xpath = "//input[@id='searchval']"
        WebElement element = find(xpath)
//        def width = element?.getSize()?.getWidth()
//        def height = element.getSize()?.getHeight()
        assert getElementSize(xpath) == [element.getSize().getWidth(), element.getSize().getHeight()]
        assert getElementSize(find(xpath)) == [element.getSize().getWidth(), element.getSize().getHeight()]
        assert getElementSize("//input[@id='FAKEXPATh']") == [-1, -1]
        log "--"
        assert getElementLocation(xpath) == [element.getLocation().getX(), element.getLocation().getY()]
        assert getElementLocation(find(xpath)) == [element.getLocation().getX(), element.getLocation().getY()]
        assert getElementLocation("//input[@id='FAKEXPATh']") == [-1, -1]

    }
}
