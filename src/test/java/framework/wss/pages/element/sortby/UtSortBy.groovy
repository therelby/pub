package framework.wss.pages.element.sortby

import above.RunWeb
import wss.pages.element.SortBy
//
class UtSortBy extends RunWeb {
    def test() {

        setup('vdiachuk', 'SortBy Element unit test  | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test sortBy sort by element',
                 "tfsTcIds:265471",
                 'logLevel:info'])

       // setLogLevel("debug")
      //  tryLoad('https://www.dev.webstaurantstore.com/43905/dishwasher-parts-and-dishwashing-components.html')
     //   SortBy sortBy = new SortBy()
      //  log "All options: ${sortBy.options} "
      //  assert 'Most Popular' == sortBy.getFirstSelectedOptionText()
        tryLoad('https://www.dev.webstaurantstore.com/43905/dishwasher-parts-and-dishwashing-components.html?order=rating_desc')
        SortBy sortByRating = new SortBy()
        log "All options: ${sortByRating.options} "
        log 'optionsRating: '+ sortByRating.getSelectedSortingOptionText()

      //  sortByRating.sortBy.selectByVisibleText(sortByRating.sortBy.options[2].getText())
        //sortByRating.sortBy.selectByVisibleText('Price: Low to High')
        assert sortByRating.selectSortByVisibleText(sortByRating.options[5])

        assert sortByRating.selectSortByVisibleText('Price: Low to High')
        assert !sortByRating.selectSortByVisibleText("FAKE SORTING")

        log "=="
        tryLoad('https://www.dev.webstaurantstore.com/43905/dishwasher-parts-and-dishwashing-components.html?order=rating_desc')
        SortBy sortByUrlCheck = new SortBy()
       assert sortByUrlCheck.selectSort('popular')
        tryLoad('https://www.dev.webstaurantstore.com/43905/dishwasher-parts-and-dishwashing-components.html')
        assert sortByUrlCheck.selectSort('popular')
        //check parameter that does not exists in current options - but exists in possible values
        assert !sortByUrlCheck.selectSort('relevancy_desc')
        //check parameter that does not exists in possible values list
        assert !sortByUrlCheck.selectSort('popularFake')



    }
}
