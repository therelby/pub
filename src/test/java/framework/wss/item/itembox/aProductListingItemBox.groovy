package framework.wss.item.itembox

import above.Execute

//Execute.suite([remoteBrowser:true],[
Execute.suite([
   //     new UtProductListingItemBoxAddToCart(),
        new UtProductListingItemBoxCategory(),
        new UtProductListingItemBoxSecondConstructor(),
        new UtProductListingItemBox(),
], 1)