package framework.wss.pages.specializedpage

import above.RunWeb
import wss.pages.specializedpage.SpecializedPage

class UtSpecializePage2 extends RunWeb {
    def test() {

        setup('vdiachuk', 'Specialized Page class  unit test  | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test specialized page ',
                 "tfsTcIds:265471",
                 'logLevel:info'])
        tryLoad("homepage")
        List pagesExistsButNotFirstStrings = ['111111', '999999', '15652', '16805']
        assert SpecializedPage.navigateSPNoErrors(pagesExistsButNotFirstStrings).spId == 15652

        List pagesNotExists = [111111, 999999, 999998, 000000]
        assert SpecializedPage.navigateSPNoErrors(pagesNotExists) == null
        List pagesExists = [16805, 15652]
        assert SpecializedPage.navigateSPNoErrors(pagesExists).spId == 16805
        List pagesExists2 = [15652, 16805]
        assert SpecializedPage.navigateSPNoErrors(pagesExists2).spId == 15652

        List pagesExistsButNotFirst = [111111, 999999, 15652, 16805]
        assert SpecializedPage.navigateSPNoErrors(pagesExistsButNotFirst).spId == 15652
        List pagesNotValid = ["sdfsd", 454.67, 15652, 16805]
        assert SpecializedPage.navigateSPNoErrors(pagesNotValid) == null

        List emptyList = []
        assert SpecializedPage.navigateSPNoErrors(emptyList) == null
    }
}


