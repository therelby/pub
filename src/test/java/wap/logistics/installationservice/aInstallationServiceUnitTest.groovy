package wap.logistics.installationservice

import above.Execute

Execute.suite([remoteBrowser: true], [
        new UtWAPPricingMaintenanceElements(),
        new UtWAPPricingMaintenanceTableData(),
        new UtWAPPricingMaintenanceModal()
], 1)