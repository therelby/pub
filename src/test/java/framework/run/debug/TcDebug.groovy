package framework.run.debug

import above.RunWeb
import above.types.IssueCategory
import wap.common.WAPLogin
import wap.common.logistics.shippingcalc.WAPGroundRefrigeratedAndFrozenGoods

class TcDebug extends RunWeb {

    static void main(String[] args) {
        new TcDebug().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Regular'
        ])
    }

    def userName
    def password

    TcDebug(Object userName = WAPLogin.usernameDev, Object password = WAPLogin.passwordDev) {
        this.userName = userName
        this.password = password
    }

    //def valid values
    def dryIceFeeValue = '100'
    def frozenPackagingFeeValue = '10.00'
    def refrigeratedPackagingFeeValue = '19.99'
    def defrostedPackagingFeeValue = '9.99'
    def alivePackagingFeeValue = '14.99'
    def keepCoolPackagingFeeValue = '4.99'
    def residentialDeliveryFeeValue = '6.00'
    def remotePerishableMarkupValue = '50.00'
    def frozenMaxTransitTimeValue = '3'
    def refrigeratedMaxTransitTimeValue = '2'
    def defrostedMaxTransitTimeValue = '1'
    def aliveMaxTransitTimeValue = '0'

    //def default values to set back after testing

    def dryIceFeeDefaultValue = '5.85'
    def frozenPackagingFeeDefaultValue = '3.00'
    def refrigeratedPackagingFeeDefaultValue = '4.00'
    def defrostedPackagingFeeDefaultValue = '0.00'
    def alivePackagingFeeDefaultValue = '0.00'
    def keepCoolPackagingFeeDefaultValue = '4.50'
    def residentialDeliveryFeeDefaultValue = '3.00'
    def remotePerishableMarkupDefaultValue = '75.00'
    def frozenMaxTransitTimeDefaultValue = '2'
    def refrigeratedMaxTransitTimeDefaultValue = '2'
    def defrostedMaxTransitTimeDefaultValue = '2'
    def aliveMaxTransitTimeDefaultValue = '1'

    WAPGroundRefrigeratedAndFrozenGoods common

    Integer PBI = 699963

    void test() {
        //https://tfs.clarkinc.biz/DefaultCollection/Automation%20Projects/_workitems/edit/699963
        setup([author  : 'lsavka',
               title   : 'Shipping Calculator - Ground Refrigerated and Frozen Goods',
               product : 'wap',
               PBI     :  PBI,
               project : 'Webstaurant.AdminPortal',
               keywords: 'Ground Refrigerated and Frozen Goods, Shipping Calculator',
               logLevel: 'info', verifyAlwaysPrintDetails: false
        ])

        common = new WAPGroundRefrigeratedAndFrozenGoods(userName, password)

        if (!verify(
                common.loadRefrigeratedAndFrozenGoodsPage(),
                [
                        id     : '1cd55f1f-c7dc-45fe-bd98-37f15c4aff7a',
                        title  : 'Refrigerated and Frozen Goods Page loads',
                        details: [
                                lastIssue: lastIssue
                        ]
                ],
                IssueCategory.PAGE_LOADING
        )) {
            return
        }

//        verify(
//                setText(WAPGroundRefrigeratedAndFrozenGoods.dryIceFeeXpath, dryIceFeeValue),
//                [
//                        id     : '9c08c0dd-6863-4b3c-990d-08097f037c61',
//                        title  : "The Dry Ice Fee of 100 is entered",
//                        details: [
//                                xpath: WAPGroundRefrigeratedAndFrozenGoods.dryIceFeeXpath
//                        ]
//                ]
//        )
//
//        verify(
//                setText(WAPGroundRefrigeratedAndFrozenGoods.frozenPackagingFeeXpath, frozenPackagingFeeValue),
//                [
//                        id     : '246f27f9-50ff-4316-ba46-4a55ebb73214',
//                        title  : "The Frozen Packaging Fee of 10.00 is entered",
//                        details: [
//                                xpath: WAPGroundRefrigeratedAndFrozenGoods.frozenPackagingFeeXpath
//                        ]
//                ]
//        )
//
//        verify(
//                setText(WAPGroundRefrigeratedAndFrozenGoods.refrigeratedPackagingFeeXpath, refrigeratedPackagingFeeValue),
//                [
//                        id     : '95a08bb4-da12-4282-8e20-63c7533554de',
//                        title  : "The Refrigerated Packaging Fee of 19.99 is entered",
//                        details: [
//                                xpath: WAPGroundRefrigeratedAndFrozenGoods.refrigeratedPackagingFeeXpath
//                        ]
//                ]
//        )
//
//        verify(
//                setText(WAPGroundRefrigeratedAndFrozenGoods.defrostedPackagingFeeXpath, defrostedPackagingFeeValue),
//                [
//                        id     : '93bf0cba-8fe4-4414-b929-4129cea19e02',
//                        title  : "The Defrosted Packaging Fee of 9.99 is entered",
//                        details: [
//                                xpath: WAPGroundRefrigeratedAndFrozenGoods.defrostedPackagingFeeXpath
//                        ]
//                ]
//        )
//
//        verify(
//                setText(WAPGroundRefrigeratedAndFrozenGoods.alivePackagingFeeXpath, alivePackagingFeeValue),
//                [
//                        id     : 'c1959c35-6ea5-4966-bb54-1a13fb5c6d73',
//                        title  : "The Alive Packaging Fee of 14.99 is entered",
//                        details: [
//                                xpath: WAPGroundRefrigeratedAndFrozenGoods.alivePackagingFeeXpath
//                        ]
//                ]
//        )
//
//        verify(
//                setText(WAPGroundRefrigeratedAndFrozenGoods.keepCoolPackagingFeeXpath, keepCoolPackagingFeeValue),
//                [
//                        id     : 'd8c32a6c-af30-4f2b-bbd6-ef0d5e2341cc',
//                        title  : "The Keep Packaging Fee of 4.99 is entered",
//                        details: [
//                                xpath: WAPGroundRefrigeratedAndFrozenGoods.keepCoolPackagingFeeXpath
//                        ]
//                ]
//        )
//        verify(
//                setText(WAPGroundRefrigeratedAndFrozenGoods.residentialDeliveryFeeXpath, residentialDeliveryFeeValue),
//                [
//                        id     : '11005b60-4dd4-49db-8011-33c8108cd5e8',
//                        title  : "The Residential Delivery Fee of 6.00 is entered",
//                        details: [
//                                xpath: WAPGroundRefrigeratedAndFrozenGoods.residentialDeliveryFeeXpath
//                        ]
//                ]
//        )


        verify(
                setText(WAPGroundRefrigeratedAndFrozenGoods.remotePerishableMarkupXpath, remotePerishableMarkupValue),
                [
                        id     : 'b606901b-0bcb-4f32-9b39-842d6d8d3446',
                        title  : "The Remote Perishable Markup Fee of 50.00 is entered",
                        details: [
                                xpath: WAPGroundRefrigeratedAndFrozenGoods
                        ]
                ]
        )

        verify(
                setText(WAPGroundRefrigeratedAndFrozenGoods.frozenMaxTransitTimeXpath, frozenMaxTransitTimeValue),
                [
                        id     : 'e0e6f4c0-9cde-4a8e-934a-6d9ae52c2425',
                        title  : "The Frozen Max Transit Time of s Days is entered",
                        details: [
                                xpath: WAPGroundRefrigeratedAndFrozenGoods.frozenMaxTransitTimeXpath
                        ]
                ]
        )

        verify(
                setText(WAPGroundRefrigeratedAndFrozenGoods.refrigeratedMaxTransitTimeXpath, refrigeratedMaxTransitTimeValue),
                [
                        id     : 'cb1e7dac-cfba-47bf-ac90-d8e2fd5b0ed1',
                        title  : "The Refrigerated Max Transit Time of 2 days is entered",
                        details: [
                                xpath: WAPGroundRefrigeratedAndFrozenGoods.refrigeratedMaxTransitTimeXpath
                        ]
                ]
        )

        verify(
                setText(WAPGroundRefrigeratedAndFrozenGoods.defrostedMaxTransitTimeXpath, defrostedMaxTransitTimeValue),
                [
                        id     : '35641cf2-798e-43a1-93f7-743c2a2c8896',
                        title  : "The Defrosted Max Transit Time of 1 Day is entered",
                        details: [
                                xpath: WAPGroundRefrigeratedAndFrozenGoods.defrostedMaxTransitTimeXpath
                        ]
                ]
        )

        verify(
                setText(WAPGroundRefrigeratedAndFrozenGoods.aliveMaxTransitTimeXpath, aliveMaxTransitTimeValue),
                [
                        id     : '267d850a-91d7-4ace-98c6-ce32eb952b4f',
                        title  : "The Alive Max Transit Time of 0 Days is entered",
                        details: [
                                xpath: WAPGroundRefrigeratedAndFrozenGoods.aliveMaxTransitTimeXpath
                        ]
                ]
        )
        verify(
                tryClick(WAPGroundRefrigeratedAndFrozenGoods.updateButtonXpath),
                [
                        id     : '3acce9f5-8eeb-4ac6-9c3f-dd9ea6256296',
                        title  : "Update Button is clicked",
                        details: [
                                xpath: WAPGroundRefrigeratedAndFrozenGoods.updateButtonXpath
                        ]
                ]
        )

        //setting back default values

        verify(
                setText(WAPGroundRefrigeratedAndFrozenGoods.dryIceFeeXpath, dryIceFeeDefaultValue),
                [
                        id     : '875c15c3-f218-4408-9562-bb05db8a27a5',
                        title  : "The Dry Ice Default Fee of 5.85 is entered",
                        details: [
                                xpath: WAPGroundRefrigeratedAndFrozenGoods.dryIceFeeXpath
                        ]
                ]
        )

        verify(
                setText(WAPGroundRefrigeratedAndFrozenGoods.frozenPackagingFeeXpath, frozenPackagingFeeDefaultValue),
                [
                        id     : 'ff2802b7-f91c-497a-bdd0-ac324073f0ff',
                        title  : "The Frozen Packaging Default Fee of 3.00 is entered",
                        details: [
                                xpath: WAPGroundRefrigeratedAndFrozenGoods.frozenPackagingFeeXpath
                        ]
                ]
        )

        verify(
                setText(WAPGroundRefrigeratedAndFrozenGoods.refrigeratedPackagingFeeXpath, refrigeratedPackagingFeeDefaultValue),
                [
                        id     : 'ce063606-0921-4f5c-8aa8-91184213a18f',
                        title  : "The Refrigerated Packaging Default Fee of 4.00 is entered",
                        details: [
                                xpath: WAPGroundRefrigeratedAndFrozenGoods.refrigeratedPackagingFeeXpath
                        ]
                ]
        )

        verify(
                setText(WAPGroundRefrigeratedAndFrozenGoods.defrostedPackagingFeeXpath, defrostedPackagingFeeDefaultValue),
                [
                        id     : 'af9047e8-f136-451d-9b78-33238b193357',
                        title  : "The Defrosted Packaging Default Fee of 0.00 is entered",
                        details: [
                                xpath: WAPGroundRefrigeratedAndFrozenGoods.defrostedPackagingFeeXpath
                        ]
                ]
        )

        verify(
                setText(WAPGroundRefrigeratedAndFrozenGoods.alivePackagingFeeXpath, alivePackagingFeeDefaultValue),
                [
                        id     : '684fc5ab-97a5-409c-8d83-9a80aba95f65',
                        title  : "The Alive Packaging Default Fee of 0.00 is entered",
                        details: [
                                xpath: WAPGroundRefrigeratedAndFrozenGoods.alivePackagingFeeXpath
                        ]
                ]
        )

        verify(
                setText(WAPGroundRefrigeratedAndFrozenGoods.keepCoolPackagingFeeXpath, keepCoolPackagingFeeDefaultValue),
                [
                        id     : 'b93ba51c-91b4-4e7e-80e0-be09c4b017f2',
                        title  : "The Keep Cool Packaging Fee of 4.50 is entered",
                        details: [
                                xpath: WAPGroundRefrigeratedAndFrozenGoods.keepCoolPackagingFeeXpath
                        ]
                ]
        )

        verify(
                setText(WAPGroundRefrigeratedAndFrozenGoods.residentialDeliveryFeeXpath, residentialDeliveryFeeDefaultValue),
                [
                        id     : '0b5d17cd-4eb6-4457-86b6-fbc42fbaaa46',
                        title  : "The Residential Delivery Default Fee of 3.00 is entered",
                        details: [
                                xpath: WAPGroundRefrigeratedAndFrozenGoods.residentialDeliveryFeeXpath
                        ]
                ]
        )

        verify(
                setText(WAPGroundRefrigeratedAndFrozenGoods.remotePerishableMarkupXpath, remotePerishableMarkupDefaultValue),
                [
                        id     : '13fc5df1-4527-484c-a02c-2ba08f5ebd29',
                        title  : "The Remote Perishable Markup Default Fee of 75.00 is entered",
                        details: [
                                xpath: WAPGroundRefrigeratedAndFrozenGoods.remotePerishableMarkupXpath
                        ]
                ]
        )

        verify(
                setText(WAPGroundRefrigeratedAndFrozenGoods.frozenMaxTransitTimeXpath, frozenMaxTransitTimeDefaultValue),
                [
                        id     : '33e3fc9b-c081-4285-8d22-8d64826fa91f',
                        title  : "The Frozen Max Transit Default Time of 2 Days is entered",
                        details: [
                                xpath: WAPGroundRefrigeratedAndFrozenGoods.frozenPackagingFeeXpath
                        ]
                ]
        )

        verify(
                setText(WAPGroundRefrigeratedAndFrozenGoods.refrigeratedMaxTransitTimeXpath, refrigeratedMaxTransitTimeDefaultValue),
                [
                        id     : '48327096-3c22-489b-8ed1-0c042ae0d7ca',
                        title  : "The Refrigerated Max Transit Default Time of 2 Days is entered",
                        details: [
                                xpath: WAPGroundRefrigeratedAndFrozenGoods.refrigeratedMaxTransitTimeXpath
                        ]
                ]
        )

        verify(
                setText(WAPGroundRefrigeratedAndFrozenGoods.defrostedMaxTransitTimeXpath, defrostedMaxTransitTimeDefaultValue),
                [
                        id     : 'ec4fd916-e5e4-4bf5-981e-affadd9347d5',
                        title  : "The Defrosted Max Transit Default Time of 2 Days is entered",
                        details: [
                                xpath: WAPGroundRefrigeratedAndFrozenGoods.defrostedPackagingFeeXpath
                        ]
                ]
        )

        verify(
                setText(WAPGroundRefrigeratedAndFrozenGoods.aliveMaxTransitTimeXpath, aliveMaxTransitTimeDefaultValue),
                [
                        id     : '4a18fd3c-72c2-4240-88d9-52a9acf984d8',
                        title  : "The Alive Max Transit Default Time of 1 Day is entered",
                        details: [
                                xpath: WAPGroundRefrigeratedAndFrozenGoods.alivePackagingFeeXpath
                        ]
                ]
        )

        verify(
                tryClick(WAPGroundRefrigeratedAndFrozenGoods.updateButtonXpath),
                [
                        id     : 'e07d1742-7b71-4be5-bbf3-862f53b9efe3',
                        title  : "The Update Button is clicked",
                        details: [
                                xpath: WAPGroundRefrigeratedAndFrozenGoods.updateButtonXpath
                        ]
                ]
        )

        closeBrowser()

    }

}
