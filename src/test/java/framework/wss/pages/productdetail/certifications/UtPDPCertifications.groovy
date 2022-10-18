package framework.wss.pages.productdetail.certifications

import above.RunWeb
import wss.pages.productdetail.PDPCertifications


class UtPDPCertifications extends RunWeb {

    static void main(String[] args) {
        new UtPDPCertifications().testExecute([

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
        final int PBI = 714076
        setup([
                author  : 'ikomarov',
                title   : 'PDP, Certifications unit test | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: ' pdp product detail page certifications unit test',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])
        PDPCertifications pdpCertifications = new PDPCertifications()

        String link = "https://www.dev.webstaurantstore.com/grapeola-100-grape-seed-oil-3-liter/101GRAPOIL3L.html"
        tryLoad(link)

        List certificatesData = pdpCertifications.getCertificationsData()

        assert certificatesData
        for (certificate in certificatesData) {
            assert certificate["name"] as boolean
            assert certificate["imageUrl"] as boolean
            assert certificate["description"] as boolean
        }

        String linkWithOneCertificate = "https://www.dev.webstaurantstore.com/choice-economy-8-qt-full-size-stainless-steel-chafer-with-folding-frame/100FOLDCHAFE.html"
        tryLoad(linkWithOneCertificate)

        List certificates = pdpCertifications.getCertificationsData()
        assert certificates
        for (certificate in certificates) {
            assert certificate["name"] as boolean
            assert certificate["imageUrl"] as boolean
            assert certificate["description"] as boolean
        }

        tryLoad()
        List homePageCertificates = pdpCertifications.getCertificationsData()
        assert !homePageCertificates
        closeBrowser()
    }
}
