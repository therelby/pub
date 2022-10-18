package framework.all.json

import above.RunWeb
import all.Json

class UtExtractJson extends RunWeb {

    static void main(String[] args) {
        new UtExtractJson().testExecute([

                browser      : 'chrome',
                remoteBrowser: true,
                //  browserVersionOffset: -1,   // use specific browser version
                environment  : 'dev',        // use specific environment
                runType      : 'Debug',//'Regular'          //Debug
                /*   parallelThreads: 1,
               runType: 'Regular' ,
               browserVersionOffset: -1   */
        ])
    }

    def test() {

        setup('vdiachuk', 'Json Util  unit test  | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test json',
                 "tfsTcIds:0",
                 'logLevel:info'])


        String withJson = """

                window.dataLayer = window.dataLayer || [];
                window.dataLayer = window.dataLayer.concat([
                    {
                        "page_type": "product",
                        "page_value": "999L89400RFW",
                        "clean_url": "/89-400-white-ribbed-plastic-cap-with-foam-liner/999L89400RFW.html",
                        "page_category": "",
                        "search_value": "",
                        "page_extra_info": "Part - WebPlus"
                    }
                ]);
"""

        String withJson1 = """

                window.dataLayer = window.dataLayer || [];
                window.dataLayer = window.dataLayer.concat([
                    {
                        "page_type": "product",
                        "page_value": "999L89400RFW",
                        "clean_url": "/89-400-white-ribbed-plastic-cap-with-foam-liner/999L89400RFW.html",
                        "page_category": "",
                        "search_value": "",
                        "page_extra_info": "Part - WebPlus"
                    }"""
        String withErrorJson = """
                window.dataLayer = window.dataLayer || [];
                window.dataLayer = window.dataLayer.concat([
                    {
                        "page_type": "product",
                        "page_value": "999L89400RFW",
                        "clean_url": "/89-400-white-ribbed-plastic-cap-with-foam-liner/999L89400RFW.html",
                        "page_category": "",
                        "search_value": "",
                        "page_extra_info": "Part - WebPlus"
                    
                ]);
"""
        String withErrorJson1 = """
                window.dataLayer = window.dataLayer || [];
                window.dataLayer = window.dataLayer.concat([
                    
                        "page_type": "product",
                        "page_value": "999L89400RFW",
                        "clean_url": "/89-400-white-ribbed-plastic-cap-with-foam-liner/999L89400RFW.html",
                        "page_category": "",
                        "search_value": "",
                        "page_extra_info": "Part - WebPlus"
                    }
                ]);
"""


        String withJson2 = """
gacData = {"conversionLabel":"sSC5CPrhmawBEIGDpMgD","conversionId":"386396044","shouldIncludeTag":true,"tags":{"ecomm_prodid":"109HF3EA","ecomm_totalvalue":"947.2000","vendor":"Advance Tabco","sku":"109HF3EA","ecomm_pagetype":"product"}}
"""
        def extracted0 = Json.extractJsonFromString(withJson)
        assert extracted0.size() > 3
        assert Json.jsonToData(extracted0).page_type == 'product'


        def extracted1 = Json.extractJsonFromString(withJson1)
        assert extracted1.size() > 3
        assert Json.jsonToData(extracted1).page_type == 'product'

        def extracted2 = Json.extractJsonFromString(withJson2)
        assert extracted2.size() > 3
        assert Json.jsonToData(extracted2).tags.sku == "109HF3EA"

        // Negative
        assert Json.extractJsonFromString(withErrorJson) == null
        assert Json.extractJsonFromString(null) == null
        Json.extractJsonFromString('') == null

    }
}
