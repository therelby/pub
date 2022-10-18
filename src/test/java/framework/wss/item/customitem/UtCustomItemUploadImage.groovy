package framework.wss.item.customitem

import above.RunWeb
import above.azure.AzureDevOpsWorkItems
import wss.item.CustomItem

class UtCustomItemUploadImage extends RunWeb {

    static void main(String[] args) {
        new UtCustomItemUploadImage().testExecute([

                browser      : 'chrome',//'chrome',
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
        final int PBI = 530427
        setup([
                author  : 'vdiachuk',
                title   : 'Customizable item upload image | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'item customizable custom upload image unit test',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])
        tryLoad("/customizable-red-peppermint-starlites-case/113CPSWL1M.html")
        //   log takeScreenshot()
        String buttonXpath = "//button[contains(@class,'btn-icon--customize')]"
        String inputXpath = "//div[@id='upload-dropzone']"
        String filePath = AzureDevOpsWorkItems.downloadAttachment("label.jpg", 257686, 'QA-Webstaurantstore ' + 'Projects');
        log filePath
        log click(buttonXpath)

        waitForElement(inputXpath)
        log "--"
        verifyElement(inputXpath)
        log takeScreenshot()
        CustomItem customItem = new CustomItem()
        assert new CustomItem().uploadImageToCustomItem(filePath)
        log takeScreenshot()
// "https://www.webstaurantstore.com/customized-products/113cpswl1m/" page with item customization

        tryLoad()
        assert !new CustomItem().uploadImageToCustomItem(filePath)
    }
}

