package framework.wss.pages.element.mainmenu

import above.RunWeb
import wss.pages.element.MainMenuElement

class UtMainMenu extends RunWeb {
    def test() {

        setup('vdiachuk', 'Main Menu Element unit test | Framework Self ' +
                'Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test element menu main green',
                 'PBI: 0',
                 'logLevel:info'])

        tryLoad("homepage")
        MainMenuElement mainMenuElement = new MainMenuElement()
        /*  def mainElNames = mainMenuElement.getMainElementNames()
          log mainElNames
          assert mainElNames.size() == 11*/

        //TO FAIL
        //   tryLoad("https://www.guru99.com/take-screenshot-selenium-webdriver.html")
        List subElementsByIndex = mainMenuElement.getSubElementsByMainElementIndex(2)
        // log subElementsByIndex
        assert subElementsByIndex.contains("Cookware")
        assert subElementsByIndex.contains("Seafood Tools")
        assert !subElementsByIndex.contains("Cooking Equipment")

        //checking last Main menu element(causing prob without js)
        List subElementsByIndex10 = mainMenuElement.getSubElementsByMainElementIndex(10)
        //  log subElementsByIndex10
        assert subElementsByIndex10.contains("Trending Products")
        assert subElementsByIndex10.contains("Donut Shop Equipment")
        assert subElementsByIndex10.contains("Event Management Supplies")


        List allSubElements = mainMenuElement.getAllSubElements()
        log allSubElements
        assert allSubElements.any() { it.contains("Electric Hand Dryers") }
        assert allSubElements.any() { it.contains("Laundromat Supplies") }
        assert allSubElements.any() { it.contains("Wooden Dinnerware") }

        assert !allSubElements.any() { it.contains("FAKE") }
    }
}
