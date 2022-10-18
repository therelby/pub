package framework.runweb.funcread

import above.RunWeb

class UtFuncReadGetNodeText extends RunWeb {

    static void main(String[] args) {
        new UtFuncReadGetNodeText().testExecute([

                browser             : 'chrome',//'edge',//'safari',//'edge',//'chrome',//'safari'
                remoteBrowser       : false,//true,// false,// true,//
                //  browserVersionOffset: -1,   // use specific browser version
                environment         : 'dev',        // use specific environment
                runType             : 'Debug',//'Regular'          //Debug
                /*   parallelThreads: 1,
               runType: 'Regular' , */
                browserVersionOffset: 0
        ])
    }

    def test() {

        setup('vdiachuk', 'Func Read, RunWeb, Read Node Text Unit test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 "tfsTcIds:265471",
                 'keywords:unit test funcread node text read get',
                 'logLevel:info'])

        /*     tryLoad('/search/table.html')

             String h1Xpath = "//h1"
             String h1Text = "Results for:"
             String fakeXpath = "//h1[@class='fake']"
             String actualH1Text = getNodeText(h1Xpath)
             String actualH1TextWebElement = getNodeText(find(h1Xpath))
             log actualH1Text
             log actualH1TextWebElement
             log getTextSafe(h1Xpath)
             assert actualH1Text.contains(h1Text)
             assert actualH1TextWebElement.contains(h1Text)

             //no element
             assert getNodeText(fakeXpath) == ""
             assert getNodeText(null) == ""
             assert getNodeText(find(fakeXpath)) == ""


             // Problematic case from Elena
             tryLoad('/lavex-industrial-15-x-1476-30-gauge-pre-stretched-cast-pallet-wrap-film-stretch-film-case/18315301476.html')

             def rowElements = findElements("//div[@id='priceBox']//tr")

             def priceElement = findInElement(rowElements[0], '//td')
             assert getNodeText(priceElement) == '$25.05'
             def priceElement1 = findInElement(rowElements[1], '//td')
             assert getNodeText(priceElement1) == '$26.64'
             def priceElement2 = findInElement(rowElements[2], '//td')
             assert getNodeText(priceElement2) == '$28.49'

     */

        tryLoad("/cvrall-micropour-hd-5x-ea/488MP4005XL.html")
        sleep(1000)

      //  def xpath0 = "//div[@id='spotlight-banner-SideBySide']"
        def xpath1 = "//p[contains(@class, 'block mb')]//span"
      //  log getNodeText(xpath0)
        log getNodeText(xpath1)
    }
}
