package framework.wss.pages.productdetail.map.c

import framework.wss.pages.productdetail.map.hUtPDPMap
import wss.pages.productdetail.PDPMap

class hUtPDPMapC extends hUtPDPMap{

    protected String itemNumberCLoneNoOverride = "338ECS20WHRB"
    protected String itemNumberCLoneNoOverrideUrl = "https://www.dev.webstaurantstore.com/e-z-up-ec3stl20kfwhtrb-eclipse-instant-shelter-10-x-20-royal-blue-canopy-with-white-frame/338ECS20WHRB.html"

    protected String getXpathForToSeeLabel(){
        return PDPMap.getXpathForMAPAspect("C", "To see our price add to cart", "toSeeLabel")
    }

    protected String getXpathForToSeePriceAddToCart(){
        return PDPMap.getXpathForMAPAspect("C", "To see our price add to cart", "toSeePriceAddToCart")
    }
}
