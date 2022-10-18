package all
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Money class based on BigDecimal. Can be perform arithmetic with Strings easily, and automatically rounds to two
 * decimal places.
 *
 * To concatenate with a string, use moneyObject.toString() + string
 * If the string contains digits, mathematical operators will perform arithmetic
 *
 * Note that the currency value is just for display. There is no cross-currency arithmetic or conversions. It will be
 * ignored in all comparison operations and all arithmetic operations.
 *
 * @author kyilmaz
 */
class Money implements Comparable {
    /**
     * exactValue is just a reference to the unrounded value. If there is difficulty with rounding on multiplication or
     * division with prices. Please look into using this value instead for those operations. Currently, regular value is
     * used to prevent something like  $5 - $4.99 = $0.02, e.g. (5.009 - 4.990 = .019 -> round 0.02), but it is unclear
     * what the expected result of fractional cent multiplication is.
     */

    BigDecimal exactValue
    BigDecimal value
    String formattedValue
    static final String defaultCurrency = '$'
    String currency
    private RoundingMode rounding = RoundingMode.HALF_UP

    Money(String stringValue) {
        initCurrency(stringValue)
        initExactValue(formatString(stringValue))
        initValue()
        initFormattedValue()
    }

    Money(BigDecimal decimalValue, String currency = defaultCurrency) {
        setCurrency(currency)
        initExactValue(decimalValue)
        initValue()
        initFormattedValue()
    }

    Money(Integer num, String currency = defaultCurrency) {
        setCurrency(currency)
        initExactValue(num)
        initValue()
        initFormattedValue()
    }

    Money(Long num, String currency = defaultCurrency) {
        setCurrency(currency)
        initExactValue(num)
        initValue()
        initFormattedValue()
    }

    Money(Double num, String currency = defaultCurrency) {
        setCurrency(currency)
        initExactValue(num)
        initValue()
        initFormattedValue()
    }

    Money(Float num, String currency = defaultCurrency) {
        setCurrency(currency)
        initExactValue(num)
        initValue()
        initFormattedValue()
    }

    private void initCurrency(String stringInput) {
        if (!stringInput) {
            return
        }
        def numStartIndex = getIndexOfFirstNumber(stringInput)
        if (numStartIndex < 0) {
            throw new Exception("No numbers found for Money class input: '$stringInput'")
        }
        setCurrency(stringInput.substring(0, numStartIndex).trim())
        if (!currency) {
            setCurrency(defaultCurrency)
        }
    }

    private void initFormattedValue() {
        if (currency == null) {
            formattedValue = 'null'
        } else {
            DecimalFormat df = new DecimalFormat("$currency###,##0.00")
            formattedValue = df.format(value)
        }
    }

    private void initExactValue(BigDecimal decimalValue) {
        exactValue = decimalValue
    }

    private void initExactValue(input) {
        if (input) {
            exactValue = new BigDecimal(input)
        } else {
            exactValue = null
        }
    }

    private void initValue() {
        if (exactValue == null) {
            value = exactValue
        } else {
            value = roundToCent(exactValue)
        }
    }

    String getNumericalString() {
        if (value == null) {
            return 'null'
        } else {
            return value.toString()
        }
    }

    void setCurrency(String newValue) {
        currency = newValue
    }

    @Override
    String toString() {
        return formattedValue
    }

    @Override
    int hashCode() {
        return value.hashCode()
    }

    boolean equals(Money money) {
        def result = true
        if (money == null) {
            result = false
        } else if (this.value != money.value) {
            result = false
        }
        return result
    }

    Money plus(Money money) {
        if (this.value == null || money.value == null) {
            return new Money(this.value)
        }
        return new Money(this.value + money.value)
    }

    Money minus(Money money) {
        if (this.value == null || money.value == null) {
            return new Money(this.value)
        }
        return new Money(this.value - money.value)
    }

    Money div(Money money) {
        if (this.value == null || money.value == null) {
            return new Money(this.value)
        }
        return new Money(this.value / money.value)
    }

    Money multiply(Money money) {
        if (this.value == null || money.value == null) {
            return new Money(this.value)
        }
        return new Money(this.value * money.value)
    }

    int compareTo(Money money) {
        if (this.value == null && money.value == null) {
            return 0
        } else if (this.value == null) {
            return -1
        } else if (money.value == null) {
            return 1
        }
        return this.value <=> money.value
    }

    Money plus(String string) {
        return new Money(value + new BigDecimal(formatString(string)))
    }

    Money minus(String string) {
        return new Money(value - new BigDecimal(formatString(string)))
    }

    Money multiply(String string) {
        return new Money(value * new BigDecimal(formatString(string)))
    }

    Money div(String string) {
        return new Money(value / new BigDecimal(formatString(string)))
    }

    int compareTo(String string) {
        if (string == null) {
            return 1
        } else {
            return value <=> roundToCent(new BigDecimal(formatString(string)))
        }
    }

    boolean equals(other) {
        def result = true
        if (other == null) {
            result = false
        } else  {
            try {
                if (value != roundToCent(new BigDecimal(other))) {
                    result = false
                }
            } catch(ignored) {
                result = false
            }
        }
        return result
    }

    Money plus(other) {
        if (other == null || value == null) {
            return new Money(this.value)
        } else {
            return new Money(value + new BigDecimal(other))
        }
    }

    Money minus(other) {
        if (other == null || value == null) {
            return new Money(this.value)
        } else {
            return new Money(value - new BigDecimal(other))
        }
    }

    Money multiply(other) {
        if (other == null || value == null) {
            return new Money(this.value)
        } else {
            return new Money(value * new BigDecimal(getStringIfLessThan1(other)))
        }
    }

    Money div(other) {
        if (other == null || value == null) {
            return new Money(this.value)
        } else {
            return new Money(value / new BigDecimal(getStringIfLessThan1(other)))
        }
    }

    /**
     * Only to be used for calculation accuracy internally
     * @param input
     * @return
     */
    private def getStringIfLessThan1(input) {
        if (input < 1) {
            return input.toString()
        } else {
            return input
        }
    }

    @Override
    int compareTo(other) {
        if (other == null) {
            return 1
        } else {
            return value <=> roundToCent(new BigDecimal(other))
        }
    }

    void round() {
        value = exactValue.setScale(2, rounding)
        initFormattedValue()
    }

    BigDecimal roundToCent(BigDecimal decimal) {
        return decimal.setScale(2, rounding)
    }

    static Money roundToCent(Money money) {
        money.value = roundToCent(money.value)
        return money
    }

    private assertStringFormat(String string) {
        if (!string.find(/[\d]/)) {
            throw new Exception("To perform an operation between a String and Money object, the given String must have numerical " +
                    "value (given String = '$string'). To concatenate with a string, use: objectName.toString() + string")
        }
    }

    static String formatString(String string) {
        if (!string) {
            return string
        }
        string = string.replaceAll(/[.](?!\d)/, '')
        return string.replaceAll(/[^\d.]/, '')
    }

    static int getIndexOfFirstNumber(String string) {
        Pattern pattern = Pattern.compile(/[0-9]/)
        Matcher matcher = pattern.matcher(string)
        if (matcher.find()) {
            return matcher.start()
        } else {
            return -1
        }
    }

    /**
     * This method was included due to the possibility of different areas of the site having different rounding rules. Only
     * set this rule if certain that the domain the object being used in supports this rounding rule
     * @param mode examples: RoundingMode.HALF_DOWN, RoundingMode.HALF_EVEN
     */
    void setRoundingMode(RoundingMode mode) {
        rounding = mode
        round()
    }
    RoundingMode getRoundingMode() {
        return rounding
    }
}
