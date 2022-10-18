package framework.wss.pages.element.topper

import above.RunWeb
import wss.pages.element.Topper

class UtTopperElement extends RunWeb {
    def test() {

        setup('vdiachuk', 'Toppers Element unit test  | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test toppers element',
                 "tfsTcIds:265471",
                 'logLevel:info'])


        tryLoad('https://www.dev.webstaurantstore.com/feature/14235/commercial-ice-machines/')
        Topper topper = new Topper()
        // log "topper.isToppers() " + topper.isToppers()
        // log "topper.isTopperShown() " + topper.isTopperShown()
        assert topper.isToppers()
        assert topper.isTopperShown()

        assert topper.hideToppers()
        assert topper.isToppers()
        assert !topper.isTopperShown()

        assert topper.displayToppers()
        assert topper.isToppers()
        assert topper.isTopperShown()

        assert topper.getToppersTab().contains("Vendor")
        assert topper.getActiveTabName() == "Ice Type"
        assert topper.setActiveTypeByText("Vendor")
        assert topper.setActiveTypeByText("Vendor")
        assert topper.setActiveTypeByText("Ice Type")
        assert topper.setActiveTypeByText("Ice Type")

       // setLogLevel("debug")
        def activeTabToppersElements = topper.getToppersElementsFromActiveTab()
        assert activeTabToppersElements.size()>5
        assert activeTabToppersElements.collect{it.getText()}.contains("Half Size Cubes\nThese cubes measure 7/8\" x 7/8\" x 3/8\", or half the size of full size cubes.")



        tryLoad("https://www.dev.webstaurantstore.com/feature/18560/american-metalcraft-sale/")
        topper.setActiveTypeByText("Product Type")
        activeTabToppersElements = topper.getToppersElementsFromActiveTab()
        assert activeTabToppersElements.size()>10
        assert activeTabToppersElements.collect{it.getText()}.contains("Serving Boards")



        //page with no toppers
        tryLoad("https://www.dev.webstaurantstore.com/38425/drinking-jars-mason-jars.html")
        assert topper.isToppers() == false
        assert topper.isTopperShown() == false
        assert topper.hideToppers() == false
        assert topper.displayToppers() == false
        assert topper.getToppersTab() ==[]

        assert topper.getActiveTabName() ==  null
        assert topper.setActiveTypeByText("Ice Type") == false




    }
}
