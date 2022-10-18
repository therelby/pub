package framework.wss.pages.servicepage.bentobox

import above.RunWeb
import wss.pages.servicepage.BentoBoxPage

class UtBentoBoxPageElements extends RunWeb{

    static void main(String[] args) {
        new UtBentoBoxPageElements().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    int pbi = 0

    void test() {
        setup([
                author  : 'mnazat',
                title   : 'Bento Box Page Elements Unit Test',
                PBI     : pbi,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'bento box page elements unit test',
                logLevel: 'info'
        ])

        BentoBoxPage bentoBoxPage = new BentoBoxPage()

        assert bentoBoxPage.navigate()
        assert bentoBoxPage.clickHeaderDemoButton()
        assert bentoBoxPage.clickFormDemoButton()

        def bentoBoxGridData = bentoBoxPage.getActualGridElements()
        assert bentoBoxGridData[0] == [
                imgLink: getUrl('hp') + "/uploads/design/2022/3/bb-online-icon.svg",
                header : "Build your online presence",
                text   : "Elevate your online presence with sleek design and seamless content management."
        ]
        assert bentoBoxGridData[1] == [
                imgLink: getUrl('hp') + "/uploads/design/2022/3/bb-efficiency-icon.svg",
                header : "Increase operational efficiency",
                text   : "BentoBox seamlessly integrates with the restaurant technology you are already using."
        ]
        assert bentoBoxGridData[2] == [
                imgLink: getUrl('hp') + "/uploads/design/2022/3/bb-engage-icon.svg",
                header : "Engage diners",
                text   : "Access a unified view of data and automate personalized marketing touchpoints to keep diners coming back."
        ]
        assert bentoBoxGridData[3] == [
                imgLink: getUrl('hp') + "/uploads/design/2022/3/bb-revenue-icon.svg",
                header : "Diversify revenue",
                text   : "Build additional revenue streams with catering, private events, and an online storefront for gift cards and merchandise."
        ]

        closeBrowser()
    }
}
