package framework.wss.pages.element.filter

import above.RunWeb
import wss.pages.element.Filter

class UtFilterShowMoreCollapse extends RunWeb {
    def test() {

        setup('vdiachuk', 'Filter Element Show More/Collapse unit test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test filter element show more collapse ',
                 "tfsTcIds:265471",
                 'logLevel:info'])


        Filter filter = new Filter()
        //Checking URl with Filters
        String url = "https://www.webstaurantstore.com/2591/uninsulated-beverage-dispensers.html"
        tryLoad(url)
        //  assert filter.isSearchWithin()
        def showMoreButtons = filter.getShowMoreButton()
        assert showMoreButtons.size() == 2
        def firstButton = showMoreButtons.get(0).get("webelement")
        assert click(firstButton)


        // page with NO show more buttons
        tryLoad("https://www.webstaurantstore.com/search/paper-towel-dispenser.html?category=48571")
         showMoreButtons = filter.getShowMoreButton()
        assert showMoreButtons.size() == 0
        assert showMoreButtons[0]== null


    }
}
