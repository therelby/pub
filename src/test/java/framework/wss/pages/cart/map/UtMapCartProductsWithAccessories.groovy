package framework.wss.pages.cart.map

import above.RunWeb
import all.Money
import all.util.StringUtil
import wss.pages.cart.CartItemBox
import wss.pages.cart.ViewCartPage
import wss.pages.productdetail.PDPPriceTile
import wss.pages.productdetail.PDPage
import wsstest.cart.map.HpMapCartTesting
import wsstest.cart.map.HpMapCartTestingQueries

class UtMapCartProductsWithAccessories extends RunWeb{

    static void main(String[] args) {
        new UtMapCartProductsWithAccessories().testExecute([

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
    static String[] mapStyles = ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "S", "T", "V", "W", "X", "Z"]

    static String mapStyleBLoneProductShowPlatinumPlusPrice = "109HBSS2410M"
    static String mapStyleBLoneProductShowMapPrice = "560BEM530SCW"
    static String mapStyleBLoneSuffixProduct = "7156235     208/2301"
    static String mapStyleBVirtualGroupingProductShowPlatinumPlusPrice = "109TSFG300"
    static String mapStyleBVirtualGroupingProductShowMapPrice = "596KODB612C"

    static String mapStyleDLoneProduct = "255IBI42NG"
    static String mapStyleDLoneProductQuantityDiscount = "10537967770"
    static String mapStyleDLoneProductShowMapPrice = "822401W"

    static String mapStyleE = "415KM45M30SF"
    static String mapStyleF = "109HDRCP5BS"
    static String mapStyleG = "922VCBA128"

    static String mapStyleHLoneProduct = "479PROSEMIOV"
    static String mapStyleHLoneProductQuantityDiscount = "479SAMAR350P"
    static String mapStyleHLoneProductShowPlatinumPlusPrice = "1099312418RL"

    static String mapStyleILoneProduct = "305SD472P18M"
    static String mapStyleILoneProductShowPlatinumPlusPrice = "439R502GTWW"
    static String mapStyleILoneProductShowBothPrices = "9361600216FL"

    static String mapStyleJLoneProductNoOverrides = "882ALT232DFS"
    static String mapStyleJLoneProductPlatinumOverride = "10993354"
    static String mapStyleJLoneProductMapOverride = "974IR4G12LP"
    static String mapStyleJLoneProductBothOverrides = "390C4T101GSN"

    static String mapStyleK = "109SAG3011"
    static String mapStyleL = "131AR7HTH"

    static String mapStyleOLoneProductNoOverrides = "645ICCXSI"
    static String mapStyleOLoneProductMapOverride = "7103061240BG"
    static String mapStyleOLoneProductMapOverrideQuantityDiscount = "7101533"

    static String mapStyleP = "270SW60N18M"

    static String mapStyleQLoneProductNoOverrides = "345SGD7742"
    static String mapStyleQLoneProductMapOverride = "92298636"
    static String mapStyleQLoneProductBothOverrides = "385PONY4"

    static String mapStyleT = "232CCR3072"
    static String mapStyleW = "740NR3635DSV"

    static String[] testProducts =
            [
             mapStyleBLoneProductShowPlatinumPlusPrice
             , mapStyleBLoneProductShowMapPrice
             , mapStyleBLoneSuffixProduct
             , mapStyleBVirtualGroupingProductShowPlatinumPlusPrice
             , mapStyleBVirtualGroupingProductShowMapPrice
             , mapStyleDLoneProduct
             , mapStyleDLoneProductQuantityDiscount
             , mapStyleDLoneProductShowMapPrice
             , mapStyleE
             , mapStyleF
             , mapStyleG

             , mapStyleHLoneProduct
             , mapStyleHLoneProductQuantityDiscount
             , mapStyleHLoneProductShowPlatinumPlusPrice

             , mapStyleILoneProduct
             , mapStyleILoneProductShowPlatinumPlusPrice
             , mapStyleILoneProductShowBothPrices

             , mapStyleJLoneProductNoOverrides
             , mapStyleJLoneProductPlatinumOverride
             , mapStyleJLoneProductMapOverride
             , mapStyleJLoneProductBothOverrides
             , mapStyleK
             , mapStyleL
             , mapStyleOLoneProductNoOverrides
             , mapStyleOLoneProductMapOverride
             , mapStyleOLoneProductMapOverrideQuantityDiscount
             , mapStyleP
             , mapStyleQLoneProductNoOverrides
             , mapStyleQLoneProductMapOverride
             , mapStyleQLoneProductBothOverrides
             , mapStyleT
             , mapStyleW
            ]

    def test() {

        setup('mwestacott', 'Cart - MAP - Products with accessories | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test cart map products with accessories',
                 "tfsTcIds:0", 'logLevel:info'])

        for(mapStyle in mapStyles){
            int size = HpMapCartTestingQueries.getMapProductsWithAccessories(true, mapStyle).size()
            assert size >= 0
            println(size)
        }

        assert openAndTryLoad('hp')
        for(testProduct in testProducts){
            def itemNumberForTesting = getIndividualMapProductWithAccessoriesInformation(testProduct)
            runTest(itemNumberForTesting[0])
        }
    }

    void runTest(def itemNumberForTesting){
        String testProduct = itemNumberForTesting['Item_Number']
        boolean showMapPrice = itemNumberForTesting['show_map_price']
        boolean showPlatinumPrice = itemNumberForTesting['show_platinum_plus_price']
        boolean hasSalePrice = itemNumberForTesting['has_sale_price']
        boolean hasQuantityDiscount = itemNumberForTesting['has_quantity_discount']

        ViewCartPage viewCartPage = new ViewCartPage()
        PDPage pdPage = new PDPage()
        PDPPriceTile pdpPriceTile = new PDPPriceTile()

        assert viewCartPage.emptyCart(true)
        assert pdPage.navigateToPDPWithItemNumber(testProduct)

        int numberOfAccessories = findElements(PDPPriceTile.addAccessoryXpath).size()

        assert pdpPriceTile.selectAllAccessories()
        assert pdpPriceTile.addToCart(1, false)

        assert viewCartPage.navigate()
        assert viewCartPage.verifyItemInCart(testProduct)

        int numberOfProductsInCart = viewCartPage.getAllItems().size()

        if(testProduct in ['415KM45M30SF', '390C4T101GSN', '385PONY4']){
            numberOfProductsInCart--
        }
        assert((numberOfProductsInCart-1) == numberOfAccessories)

        CartItemBox cartItemBox = new CartItemBox(testProduct)

        Money expectedPrice = new HpMapCartTesting().getExpectedPrice(itemNumberForTesting, 'guest', showMapPrice, showPlatinumPrice, hasSalePrice, hasQuantityDiscount)
        Money actualPrice = cartItemBox.getPrice()

        assert (actualPrice == expectedPrice)

    }

    static String queryIndividualMapProductsWithAccessories = '''        
        DECLARE @item_number VARCHAR(MAX) = '$itemNumber';
                
        SELECT
            PS.item_number_id
            , RTRIM(M.Item_Number) as Item_Number
            , M.Call_For_Pricing
            , VC.Name
            , PSI.P1
            , PSI.P4
            , PSI.P5
            , PSI.Sale_Price
            , PS.Min_Buy
            , PS.Must_Buy
            , PS.UOM
            , ISNULL(NULLIF(PMAP.[show_map_price], 0), VC.[show_map_price]) AS [show_map_price]
            , ISNULL(NULLIF(PMAP.[show_platinum_plus_map_price], 0), VC.[show_platinum_plus_price]) AS [show_platinum_plus_price]
            , (case when PSI.Sale_Price < PSI.P1 then 1 else 0 end) as has_sale_price
            , PS.customization_required
            , ISNULL(CPPI.is_active, 0) AS is_customizable
            , CASE
                WHEN LEN(ISNULL(PCS.url, '')) > 0 THEN 1
                ELSE 0
              END as has_customizable_url
            , case when Q.item_number is null then 0 else 1 end as has_quantity_discount
        FROM tblProductMapPricing M
        INNER JOIN tblProductSearch PS
            ON PS.Item_Number = M.Item_Number
            AND LEN(PS.virtual_url) > 0
        INNER JOIN tblProductSearchItem PSI
            ON PSI.Item_Number = PS.Item_Number
            AND PSI.Location = 851
            AND PSI.Hide_Item = 0
            AND PSI.Unavailable = 0
            and PS.is_demoted_part = 0
            and PSI.P1 > PSI.P4
            and PSI.P5 = 0
        INNER JOIN tblProducts P
            ON P.item_number = PS.Item_Number
            AND P.grouping = 'N'
            AND P.location = 851
        LEFT JOIN 
        (    
            SELECT D.item_number
                , D.start_qty as quantity_discount_start_qty
                , D.price as quantity_discount_price
                , ROW_NUMBER() OVER(PARTITION BY D.item_number ORDER BY D.price) AS quantity_discount_order
            FROM tblSpecialsQtyDictatesPrice D
            INNER JOIN tblSpecials S
                ON S.[index] = D.special_index
                AND S.date_start < GETDATE()
                AND S.date_end > GETDATE()
            WHERE D.start_qty > 1
        ) as Q
            on Q.item_number = P.item_number
        LEFT JOIN [products].[product_map_settings] AS PMAP
            ON PMAP.[Products_ItemNumber_id] = P.[item_number_id]
        INNER JOIN tblVendors V
            ON V.Vendor = PS.Vendor
        INNER JOIN tblVendorCache VC
            ON CAST(VC.Name AS VARCHAR(150)) = (
                CASE
                    WHEN LEN(LTRIM(RTRIM(ISNULL(P.vendor_name_output, '')))) > 0 THEN
                        P.vendor_name_output
                    WHEN LEN(RTRIM(LTRIM(ISNULL(V.output_vendor, V.Vendor_Name)))) > 0 THEN
                        ISNULL(V.output_vendor, V.Vendor_Name)
                    ELSE
                        NULL
                END
            )
        left join customizable_products.product_information CPPI
            ON CPPI.Products_ItemNumber_id = PS.item_number_id
            AND CPPI.date_customization_deleted IS NULL
        LEFT JOIN products.customization_settings PCS
            ON PCS.Products_ItemNumber_id = P.item_number_id
        WHERE M.item_number = @item_number
        ;
    '''

    static def getIndividualMapProductWithAccessoriesInformation(String itemNumber){
        RunWeb r = run()
        Map binding = [itemNumber: itemNumber]
        String finalQuery = StringUtil.formatWithMap(queryIndividualMapProductsWithAccessories, binding)
        return r.dbSelect(finalQuery, 'wss-ro')
    }
}
