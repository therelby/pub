package framework.wss.api.catalog.homepage.topproduct

import above.RunWeb
import wss.api.catalog.homepage.topproduct.HomePageTopProductAPI

class UtHomePageTopProduct extends RunWeb {

    static void main(String[] args) {
        new UtHomePageTopProduct().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    void test() {
        setup([
                author  : 'vdiachuk',
                title   : 'HomePage Top Product API ',
                PBI     : 0,
                product : 'wss|dev,test',
                project : 'Automation.Framework',
                keywords: 'unit test api homepage',
                logLevel: 'info'
        ])
        def categoryIds =
                [
                        195,
                        1,
                        57683,
                        875,
                        13403,
                        1539,
                        821,
                        53981,
                        2403,
                        3415,
                        3673
                ]

        HomePageTopProductAPI homePageTopProduct = new HomePageTopProductAPI(categoryIds, null)
        //    log homePageTopProduct.getRequest()
        log "--"
        def data0 = homePageTopProduct.getValues(''/*, categoryIds[0]*/)
        log "=="

        HomePageTopProductAPI homePageTopProduct1 = new HomePageTopProductAPI()
        def data1 = homePageTopProduct1.getValues('')
        assert data1?.keySet()?.contains("ads")
        assert data1?.keySet()?.contains("banner")
        assert data1?.keySet()?.contains("carousel")

        assert data0 == data1

        //
        // Negative
        //
        def categoryIds2 =
                [
                        'fake',
                ]
        HomePageTopProductAPI homePageTopProduct2 = new HomePageTopProductAPI(categoryIds2, null)
        assert homePageTopProduct2.getValues('') == null

        //
        // Negative, complete fail
        //
        def categoryIds3
        HomePageTopProductAPI homePageTopProduct3 = new HomePageTopProductAPI(categoryIds3, null)
        assert homePageTopProduct3.getStatusCode() == null
        assert homePageTopProduct3.getValues('') == null
    }
}
