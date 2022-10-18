package framework.wss.pages.element.gdprmodal

import above.RunWeb

class UtGDPRModal extends RunWeb {
    def test() {

        setup('vdiachuk', 'GDPR Modal Unit test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test modal window popup gdpr cookie ',
                 "tfsTcIds:265471",
                 'logLevel:info'])

        tryLoad('homepage')
       /* log "List of elements existing Xpath"
        log  findElements("//button[@value='Search']")*/
        log "List of elements FAKE Xpath"
        log  findElements("//button[@value='SearchFAKE']")

      //  GDPRModal gdprModal = new  GDPRModal()
      //  log "gdprModal.isGDPRModalPresent() "+  gdprModal.isGDPRModalPresent()
        log takeScreenshot()
       String sourceCode = getPageSource()

        File file = new File("source.txt")
        file.write sourceCode


    }
}
