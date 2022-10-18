package framework.above.test

import above.ConfigReader
import above.RunWeb

class Ut03TestRandom extends RunWeb {

    static void main(String[] args) {
        new Ut03TestRandom().testExecute([remoteBrowser:true])
    }

    void test() {

        setup([
                author: ConfigReader.get('frameworkDebugPerson'),
                title: 'UNIT TEST Random Testing Stuff',
                product: 'wss',
                tfsProject: 'Webstaurant.StoreFront',
                keywords: 'framework unit test',
                PBI: 1
        ])

        String page = "/${UUID.randomUUID().toString()}"
        if (!tryLoad(page)) {
            assert lastIssue.contains(page)
        } else {
            assert false
        }

        if (!tryLoad()) {
            report(lastIssue)
            return
        }

        if (!tryLoad('cart')) {
            report(lastIssue)
            return
        }

        assert getCurrentUrl().toLowerCase().contains('cart')

        if (!clickAndTryLoad('//a[@href="/"]', '/')) {
            report(lastIssue)
            return
        }

        assert getTitle() != ''

        check(getTitle() != '', 'Empty title')
        check(getPageText() != '', 'No text on the page')

    }

}
