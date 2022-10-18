package framework.wss.pages.productdetail.schema

import above.RunWeb
import wss.pages.element.SchemaElement

class UtPDPSchemaMultiElement extends RunWeb{
    static void main(String[] args) {
        new UtPDPSchemaMultiElement().testExecute([

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
        final int PBI = 530257
        setup([
                author  : 'vdiachuk',
                title   : 'PDP Schema multi elements unit test | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: ' pdp product multi detail page schema review breadcrumb video unit test',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])

        tryLoad("/edlund-401-nsf-electric-knife-sharpener-with-removable-guidance-system-115v/333401NSF.html")
        SchemaElement schemaElement = new SchemaElement()

        assert schemaElement.getMultiData().size() == 9

        log ""+ schemaElement.getMultiData()

        // no schema
        log "--"
        tryLoad('https://www.selenium.dev/')
        assert schemaElement.getMultiData()==[]

    }
}
