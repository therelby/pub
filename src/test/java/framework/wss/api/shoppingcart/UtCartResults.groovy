package framework.wss.api.shoppingcart

import above.RunWeb
import wss.api.shoppingcart.model.cartresults.CartResultsModel

class UtCartResults extends RunWeb {
    static void main(String[] args) {
        new UtCartResults().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    void test() {
        setup([
                author  : 'kyilmaz',
                title   : 'UtShoppingCart',
                PBI     : 0,
                product : 'wss',
                project : 'Automation.Framework',
                keywords: 'unit test',
                logLevel: 'info'
        ])
        testModelConstructor()
    }

    void testModelConstructor() {
        Map exampleRaw = [
                "alt_uom": "Each",
                "clark_category": "129",
                "class_code": "3",
                "classification_code": "",
                "clean_description": "Galaxy SDM400 Single Spindle 2 Speed Drink Mixer - 120V, 400W",
                "Cost": 52.9523,
                "cost_from_cart": 52.9523,
                "date_added": 637902067319590100,
                "Description": "Galaxy SDM400 Single Spindle 2 Speed Drink Mixer - 120V, 400W",
                "dms_item_number": "",
                "extended_suffix": "",
                "Grouping": "N",
                "grouping_item_number_id": 102020,
                "hbc_num": 0,
                "hide_item": "N",
                "is_auto_reorder_item": 0,
                "is_equipment": 1,
                "is_outlet_item": 0,
                "is_outlet_item_active": 0,
                "is_outlet_item_available": 0,
                "is_prop65": true,
                "left_item_number": "177SDM400",
                "min_buy": 1,
                "must_buy": 1,
                "Notes": "Updated from ApplyQuantityDiscounts (Zeus Last Section)",
                "Num": 637902067319590100,
                "outlet_item_number": null,
                "out_of_stock": "",
                "output_vendor": "Avantco Equipment",
                "price_p1": 129.99,
                "product_image_template_id": 0,
                "productpage_descript": "Galaxy SDM400 Single Spindle 2 Speed Drink Mixer - 120V, 400W",
                "productpage_description": "Galaxy SDM400 Single Spindle 2 Speed Drink Mixer - 120V, 400W",
                "productpage_item_number": "177SDM400",
                "quote_create_date": null,
                "quote_description": "",
                "quote_expiration_date": null,
                "quote_item_number": null,
                "quote_title": "",
                "shipping_type": "F",
                "special_applied": 0,
                "stock_flag": "Y",
                "TaxCode": "Y",
                "taxcode_from_product": "Y",
                "Thumbnail": "/images/products/thumbnails/102020/1890591.jpg",
                "Unavailable": "N",
                "Uom": "EA  ",
                "vendor_name": "Guangzhou Sunmile Industries C",
                "web_category": "Restaurant Equipment > Beverage Equipment > Commercial Milkshake Machines",
                "is_accessory_item": false,
                "link": "/galaxy-sdm400-single-spindle-2-speed-drink-mixer-120v-400w/177SDM400.html",
                "is_m1t": "N",
                "volume": 0,
                "weight": 0,
                "ups_ship_type": "M",
                "drop_flag": "N",
                "linked_from_item_number_id": 0,
                "pre_discount_price": 129.99,
                "ormd": "N",
                "shipping_text": "",
                "vendor": "SUN200",
                "actual_ship_type": "U",
                "enable_white_glove": false,
                "is_too_large_for_liftgate": false,
                "is_webstaurant_plus_eligible": true,
                "feed_identifier": "177SDM400",
                "has_webstaurant_plus_discounted_shipping": false,
                "is_platinum_plus_exclusive": false,
                "tax_line_identifier": "2a1afdfa-b448-c9ed-957a-1df510bcf7a3",
                "item_number": "177SDM400",
                "item_number_id": 102020,
                "product_accessory_id": 0,
                "is_free": "N",
                "free_shipping": "Y",
                "outlet_item_id": 0,
                "Price": 109.99,
                "qty": 1,
                "quote_number": null,
                "shopping_cart_id": "11925071",
                "product_warranty_id": 0,
                "service_tier_pricing_id": 0
        ]
        Map exampleFormatted = [
                alt_uom: "Each",
                clark_category: "129",
                class_code: "3",
                classification_code: "",
                clean_description: "Galaxy SDM400 Single Spindle 2 Speed Drink Mixer - 120V, 400W",
                cost: 52.9523,
                cost_from_cart: 52.9523,
                date_added: 637902067319590100,
                description: "Galaxy SDM400 Single Spindle 2 Speed Drink Mixer - 120V, 400W",
                dms_item_number: "",
                extended_suffix: "",
                grouping: "N",
                grouping_item_number_id: 102020,
                "hbc_num": 0,
                hide_item: "N",
                is_auto_reorder_item: 0,
                is_equipment: 1,
                is_outlet_item: 0,
                is_outlet_item_active: 0,
                is_outlet_item_available: 0,
                is_prop65: true,
                left_item_number: "177SDM400",
                min_buy: 1,
                must_buy: 1,
                notes: "Updated from ApplyQuantityDiscounts (Zeus Last Section)",
                num: 637902067319590100,
                outlet_item_number: null,
                out_of_stock: "",
                output_vendor: "Avantco Equipment",
                price_p1: 129.99,
                product_image_template_id: 0,
                productpage_descript: "Galaxy SDM400 Single Spindle 2 Speed Drink Mixer - 120V, 400W",
                productpage_description: "Galaxy SDM400 Single Spindle 2 Speed Drink Mixer - 120V, 400W",
                productpage_item_number: "177SDM400",
                quote_create_date: null,
                quote_description: "",
                quote_expiration_date: null,
                quote_item_number: null,
                quote_title: "",
                shipping_type: "F",
                special_applied: 0,
                stock_flag: "Y",
                taxCode: "Y",
                taxcode_from_product: "Y",
                thumbnail: "/images/products/thumbnails/102020/1890591.jpg",
                unavailable: "N",
                uom: "EA  ",
                vendor_name: "Guangzhou Sunmile Industries C",
                web_category: "Restaurant Equipment > Beverage Equipment > Commercial Milkshake Machines",
                is_accessory_item: false,
                link: "/galaxy-sdm400-single-spindle-2-speed-drink-mixer-120v-400w/177SDM400.html",
                is_m1t: "N",
                volume: 0,
                weight: 0,
                ups_ship_type: "M",
                drop_flag: "N",
                linked_from_item_number_id: 0,
                pre_discount_price: 129.99,
                ormd: "N",
                shipping_text: "",
                vendor: "SUN200",
                actual_ship_type: "U",
                enable_white_glove: false,
                is_too_large_for_liftgate: false,
                is_webstaurant_plus_eligible: true,
                feed_identifier: "177SDM400",
                has_webstaurant_plus_discounted_shipping: false,
                is_platinum_plus_exclusive: false,
                tax_line_identifier: "2a1afdfa-b448-c9ed-957a-1df510bcf7a3",
                item_number: "177SDM400",
                item_number_id: 102020,
                product_accessory_id: 0,
                is_free: "N",
                is_m1t: true,
                free_shipping: "Y",
                outlet_item_id: 0,
                price: 109.99,
                qty: 1,
                quote_number: null,
                shopping_cart_id: "11925071",
                product_warranty_id: 0,
                service_tier_pricing_id: 0
        ]
        CartResultsModel data = new CartResultsModel(exampleRaw)
        log data.toPrettyJson()
        String stringData = new TreeMap(data.toMap()).toString()
        String stringExample = new TreeMap(exampleFormatted).toString()
        assert stringData == stringExample

        log "Look here"
        log data.toString()
        log data.toMap()
        log data.toPrettyJson()
    }
}
