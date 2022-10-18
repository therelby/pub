package all.util

import above.RunWeb

import java.awt.image.BufferedImage

/**
 * Utilities for Images
 */
class ImageUtil {
    RunWeb r = run()

    /**
     *  Replace svg links with full url to the file
     *  in case of //cdnimg.webstaurantstore.com/uploads/design/2016/2/safari-pinned-tab.svg -- add protocol https:
     *  getUrl() in all other cases
     * @param svgUrl
     */
    String svgLinkUrl(String svgUrl) {
        String result = ''
        if (svgUrl?.startsWith("//")) {
            result = "https:" + svgUrl
        } else {
            result = r.getUrl(svgUrl)
        }
        return result
    }
}
