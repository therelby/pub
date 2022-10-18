package framework.wss.cart

import above.RunWeb
import wsstest.cart.map.HpMapCartTestingQueries

class UtMapCartTesting extends RunWeb{
    def test() {

        setup('mwestacott', 'MAP Cart testing unit test  | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test map cart testing ',
                 "tfsTcIds:0",
                 'logLevel:info'])

        assert HpMapCartTestingQueries.getMapProductsForCartTesting(5, 'D', 'Lone', false, false, false, false).size() == 5
    }
}
