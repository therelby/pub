package framework.wss.pages.element.footer

import above.RunWeb
import wss.pages.element.footer.FooterMenu

class UtFooterMenu extends RunWeb {
    def test() {

        setup('vdiachuk', 'Footer Menu Unit test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test footer menu',
                 "tfsTcIds:265471",
                 'logLevel:info'])

        tryLoad('homepage')
        FooterMenu footerMenu = new FooterMenu()
        assert footerMenu.isFooterMenu()
        assert footerMenu.getHeaders().size() > 4
        assert footerMenu.getSubItems().size() > 4
        assert footerMenu.getSubItems()[0].size() > 3


       /* log footerMenu.getHeaders()
        log footerMenu.getSubItems()*/
        tryLoad("https://www.google.com/")
        assert !footerMenu.isFooterMenu()
        assert !footerMenu.getHeaders()
        assert !footerMenu.getSubItems()


    }
}