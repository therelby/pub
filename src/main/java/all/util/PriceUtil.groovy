package all.util

import all.Money

import java.text.DecimalFormat

/**
 * (!) DEPRECATED
 * Please use all.Money
 */
@Deprecated
class PriceUtil {

    // Refactored method from Katalon com.wss.Tools.formatPrice()
    // Format price number to text, example 2.3 -> '$2.30'
    synchronized static formatPrice(BigDecimal price) {
        def pattern = "\$##,###.##"
        def moneyForm = new DecimalFormat(pattern)
        def fnum = moneyForm.format(price).trim()
        if (fnum.indexOf('.') == fnum.length()-2) {
            fnum += '0'
        }
        if (!fnum.contains('.')) {
            fnum += '.00'
        }
        return fnum
    }
}
