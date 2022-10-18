package framework.all.util.addresses

import above.RunWeb
import all.util.Addresses
import wss.item.itembox.ProductListingItemBox

class UtAddresses extends RunWeb {
    def test() {

        setup('vdiachuk', ' Addresses unit test | Framework Self ' +
                'Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test address Addresses',
                 "tfsTcIds:265471", 'logLevel:info'])


        Addresses addresses = new Addresses(Addresses.usResidentialAddressAK)
        log addresses
        assert addresses.address == "141 Patterson St"
        assert addresses.convertToMap().get("name") == "Alaska TestAddr"

        Map map = [name:"Viktor",address:" "]
        Addresses addresses1 = new Addresses(map)
        assert addresses1['address'] == ' '
        assert addresses1['address2'] == ''
        assert addresses1['name'] == "Viktor"
        log addresses1

    }
}
