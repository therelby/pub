package framework.runweb.action

import above.RunWeb
import org.openqa.selenium.WebElement
import wss.pages.element.MainMenuElement

class UtActionJsMouseOver extends RunWeb {

    static void main(String[] args) {
        new UtActionJsMouseOver().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }
    def test() {
        setup('vdiachuk', 'Actions jsMouseOver method unit test | Framework Self ' +
                'Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test funcactions action mouse over hover js javascript',
                 'PBI: 0',
                 'logLevel:info'])

        tryLoad("homepage")
        String mainMenu1 = '//*[@id="flyout-nav"]//div//a[1]'
        String mainMenu11 = '//*[@id="flyout-nav"]//div//a[11]'
        String searchButtonXpath = '//button[@value=\'Search\']'
        String subDivXpath = MainMenuElement.flyOutDivXpath

        //no submenu flyout divs before hover main menu element
        List subMenuDivs = findElements(subDivXpath)
        assert subMenuDivs.size() == 0

        //hover 1st menu element - 1st div text's populated.. not empty
        log "js mouseover main menu1: "
        assert jsMouseOver(mainMenu1)
        sleep(1500)


        subMenuDivs = findElements(subDivXpath)
        List subMenu1items = findElementsInElement(subMenuDivs?.getAt(0), "//li").collect() { it.getText() }
        assert subMenu1items.every() { it.size() > 3 }


        //move to menu 11 element - 1st element div texts are "", 11th div text are not empty
        log "js mouseover main menu11: "
        assert jsMouseOver(mainMenu11)
        sleep(1500)
        subMenuDivs = findElements(subDivXpath)
        subMenu1items = findElementsInElement(subMenuDivs?.getAt(0), "//li").collect() { it.getText() }
        assert subMenu1items.every() { it == "" }

        subMenu1items = findElementsInElement(subMenuDivs?.getAt(10), "//li").collect() { it.getText() }
        assert subMenu1items.every() { it.size() > 3 }

        //move mouse over search button - no divs shown
        log "js mouseover search: "
        assert jsMouseOver(searchButtonXpath)
        sleep(1500)
        subMenuDivs = findElements(subDivXpath)
        assert subMenuDivs.size()==0
    }
}