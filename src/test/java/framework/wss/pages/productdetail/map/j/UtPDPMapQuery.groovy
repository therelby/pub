package framework.wss.pages.productdetail.map.j

import above.RunWeb
import wss.actions.WssRenderingModeBanner
import wss.user.UserQuickLogin
import wsstest.product.productdetailpage.mapRedo.individualmap.HpPDPMapTestIndividualMapQuery

class UtPDPMapQuery extends RunWeb{

    static void main(String[] args) {
        new UtPDPMapQuery().testExecute([

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

        setup('mwestacott', 'MAP PDP Query unit test',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test, pdp, map, on sale ',
                 "PBI: 0",
                 'logLevel:info'])

        assert HpPDPMapTestIndividualMapQuery.getMapProducts(true, 'J').size() > 0
    }
}
