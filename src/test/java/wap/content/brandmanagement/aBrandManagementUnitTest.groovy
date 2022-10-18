package wap.content.brandmanagement

import above.Execute

Execute.suite([remoteBrowser: true], [
        new UtWAPBrandGroupListing(),
        new UtWAPManageBrandGroup(),
        new UtWAPVendorListing(),
        new UtWAPVendorListingPagination(),
], 1)