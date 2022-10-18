package above.smokecloud

/**
 *      Smoke Cloud Test
 *      -- (!) any changes impact to all Smoke Cloud testing
 */

class ASmokeCloudTest extends RunSmoke {

    def test() {

        setup('akudin', "Smoke Cloud Test Starter",
                ["product:wss|dev,test,prod", "tfsProject:SmokeCloud",
                 'keywords:smoke cloud', "PBI:434381", 'logLevel:info'])

        smokeExecute()
   }

}
