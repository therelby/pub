package framework.runweb.funcread

import above.RunWeb

class FuncReadUnitTest extends RunWeb {

    def tcId = 265465
    // debugging testcase
    def searchButton = '//button[@value="Search"]'
    def restaurantEquipmentLink = '//a[@data-type="Restaurant<br> Equipment"]'

    // Test
    def test() {
        setup('kyilmaz', 'RunWeb - FuncWait Unit Test',
              ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test', 'logLevel:debug'])
        log 'Testing FuncRead'
        openBrowser(getUrl('homepage'))
        start()
        log 'Completed testing for FuncRead.'
        closeBrowser()
    }

    def start() {
        testGetText()
        testGetAttribute()
        testGetAllAttributes()
        return this
    }

    def testGetText() {
        def searchText = getText(searchButton)
        check(tcId,
              "Using getText(), text for search button = ${searchText}. Expected: 'Search'",
              searchText == "Search")
        def restaurantEquipmentText = getText(restaurantEquipmentLink)

        // <br> is included as an element here, so it should be read as a LF (line feed == \n)
        check(tcId,
              "Using getText(), text for Restaurant Equipment button = ${restaurantEquipmentText} " +
                      "Expected: 'Restaurant\nEquipment'",
              restaurantEquipmentText == "Restaurant\nEquipment")
    }

    def testGetAttribute() {
        def searchType = getAttribute(searchButton, 'type')
        check(tcId,
              "Using getAttribute(), 'type' for search button = ${searchType}. Expected: 'submit'",
              searchType == "submit")

        // <br> is included in a string here, so it should be read in full
        def restaurantEquipmentType = getAttribute(restaurantEquipmentLink, 'data-type')
        check(tcId,
              "Using getAttribute(), 'data-type' for Restaurant Equipment button = ${restaurantEquipmentType} " +
                      "Expected: 'Restaurant<br> Equipment'",
              restaurantEquipmentType == "Restaurant<br> Equipment")
    }

    def testGetAllAttributes() {
        def searchAttributes = getAllAttributes(searchButton)
        def restaurantAttributes = getAllAttributes(restaurantEquipmentLink)
        def searchExpected = [
                "class": "bg-origin-box-border bg-repeat-x border-solid border box-border cursor-pointer inline-block " +
                        "font-semibold text-center no-underline hover:no-underline antialiased align-middle " +
                        "hover:bg-position-y-15 rounded-l-none rounded-r-normal box-border leading-4 mt-0 mb-0 py-2 px-2" +
                        " text-base-1/2 capitalize align-top w-24 xs:py-3 xs:px-5 xs:w-auto  bg-blue-300 hover:bg-blue-600" +
                        " text-white text-shadow-black-60 bg-linear-gradient-180-blue border-black-25 shadow-inset-black-17",
                "color": "blue",
                "type" : "submit",
                "value": "Search"
        ]
        def restaurantExpected = [
                "aria-expanded": "false",
                "aria-haspopup": "true",
                "class"        : " \n        bg-white border-gray-300 border-solid border border-b-0 cursor-pointer block" +
                        " flex items-center justify-between font-semibold py-4 pl-2 pr-4 sticky top-0 shadow text-left " +
                        "text-gray-800 hover:text-gray-800 text-base no-underline antialiased lt:linear-green-gradient " +
                        "lt:green-shadow lt:text-shadow-800 lt:hover:linear-light-green-gradient " +
                        "lt:hover:linear-light-green-gradient lt:hover:light-green-shadow lt:hover:light-green-shadow " +
                        "lt:bg-green-800 lt:hover:bg-green-500 lt:bg-repeat-x lt:border-black lt:border-solid lt:border " +
                        "lt:border-r-0 lt:flex lt:items-center lt:justify-center lt:flex-auto lt:leading-tight lt:mt-0 " +
                        "lt:px-0 lt:py-2 lt:static lt:top-0 lt:text-center lt:text-white lt:hover:text-white lt:text-xs " +
                        "lg:nav-item-width xl:leading-4 xl:py-2-1/2 xl:text-sm  \n      lt:px-0 lg:px-2 two-lines\n      " +
                        "lt:rounded-l-normal   \n      \n      category-item",
                "data-type"    : "Restaurant<br> Equipment",
                "href"         : "/restaurant-equipment.html"
        ]
        logData searchAttributes
        logData restaurantAttributes
        check(tcId,
              "Using getAllAttributes(), for Search button.\nResult:\n${searchAttributes.toPrettyString()}. " +
                      "\nExpected:\n ${searchExpected.toPrettyString()}",
              searchExpected == searchAttributes)
        check(tcId,
              "Using getAllAttributes(), for Restaurant Equipment button.\nResult:\n" +
                      "${restaurantAttributes.toPrettyString()}.\nExpected:\n ${restaurantExpected.toPrettyString()}",
              restaurantExpected == restaurantAttributes)
    }


}
