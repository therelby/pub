package framework.project

import above.ConfigReader
import above.RunWeb
import wss.api.catalog.product.Products
import wss.item.ItemUtil

/**
 * Run this test after an installation to make sure certificates are setup correctly.
 */
class InstallHealth extends RunWeb {
    public static void main(String[] args) {
        new InstallHealth().testExecute()
    }

    def test() {

        setup(ConfigReader.get('frameworkDebugPerson'), 'Installation Health',
                ['product:wss', 'tfsProject:none', 'keywords:framework', 'PBI:1'])
        log "Checking SSL certificate..."
        try {
            def call = new Products('962WBS1432')
            log("Success! Got status code ${call.getStatusCode()}", console_color_green)
        } catch(javax.net.ssl.SSLHandshakeException e) {
            logError("Certificate not correctly setup. Please follow the 'SSL Certificate Setup' of the README.")
        } catch (e) {
            logError("Could not call Catalog API.")
        }
        log "Checking JDBC driver..."
        try {
            def testQuery = "SELECT TOP 1 id FROM products.item_numbers"
            def itemId = dbSelect(testQuery, "wss-ro")
            log("Success! Got item id from DB: ${itemId}", console_color_green)
        } catch (e) {
            logError("Could not call Database. Please complete the 'Database Setup' section of the README.")
        }

    }

}
