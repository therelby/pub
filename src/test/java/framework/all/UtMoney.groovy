package framework.all

import above.RunWeb
import all.Money

import java.math.RoundingMode

/**
 * Unfinished unit test. Only tests String operations right now.
 * @author kyilmaz
 */
class UtMoney extends RunWeb {
    static void main(String[] args) {
        new UtMoney().testExecute()
    }

    def test() {
        setup('kyilmaz', 'UtMoney',
                ['product:wss', 'tfsProject:Automation.Projects',
                 'keywords:unit test', "PBI:0", 'logLevel:info'])
        multiplyDecimal()
        testNullArithmetic()
        testNullValueOperation()
        testNullInit()
        testRoundingMode()
        testCompareToNull()
        equalsItself()
        testRounding()
        addString()
        subtractString()
        divideString()
        multiplyString()
        equalsString()
        greaterThanString()
        lessThanString()
        testConcatenation()
        testStringFormatting()
    }

    void equalsItself() {
        Money nullMoney = null
        check(nullMoney.equals(nullMoney), "Null Money object does not equal itself.")
        Money money = new Money('5')
        check(money.equals(money), "Money object does not equal itself.")
    }

    void testRounding() {
        def money = new Money('5')
        double lotsOfDecimals = 5.555555555555
        money += lotsOfDecimals
        check(money.value == new Money('10.56').value, "Expected rounding to two decimal " +
                "places, after adding $lotsOfDecimals to money with value 5. Got $money")
    }

    void addString() {
        def initValue = '9.40'
        def operandValue = '10.60'
        def money = new Money(initValue)
        money += operandValue
        check(money.value == new Money('20').value, "Added '$operandValue' to Money " +
                "with value = $initValue, got value = $money.")
    }

    void subtractString() {
        def initValue = '30.60'
        def operandValue = '10.60'
        def money = new Money(initValue)
        money -= operandValue
        check(money.value == new Money('20').value, "Subtracted '$operandValue' from Money " +
                "with value = $initValue, got value = $money.")
    }

    void divideString() {
        def initValue = '20.50'
        def operandValue = '4.50'
        def money = new Money(initValue)
        money /= operandValue
        check(money.value == new Money('4.56').value, "Divided '$operandValue' from Money " +
                "with value = $initValue, got value = $money.")
    }

    void multiplyString() {
        def initValue = '5.50'
        def operandValue = '10.50'
        def money = new Money(initValue)
        money *= operandValue
        check(money.value == new Money('57.75').value, "Multiplied '$operandValue' by Money " +
                "with value = $initValue, got value = $money.")
    }
    void multiplyDecimal() {
        def initValue = '28,003.50'
        def operandValue = 0.03
        def money = new Money(initValue)
        money *= operandValue
        check(money.value == new Money('840.11').value, "Multiplied '$operandValue' by Money " +
                "with value = $initValue, got value = $money.")
    }

    void equalsString() {
        def initValue = '5.55'
        def operandValue = '5.554'
        check(new Money(initValue) == operandValue, "Expected money with value " +
                "$initValue to be equal to string with value $operandValue.")
    }

    void greaterThanString() {
        def initValue = '5'
        def operandValue = '4'
        check(new Money(initValue) > operandValue, "Expected money with value " +
                "$initValue to be greater than string with value $operandValue.")
    }

    void lessThanString() {
        def initValue = '3'
        def operandValue = '4'
        check(new Money(initValue) < operandValue, "Expected money with value " +
                "$initValue to be less than string with value $operandValue.")
    }

    void testConcatenation() {
        def money = new Money('10.00')
        def message = "The price is $money"
        check(message == "The price is \$10.00", "Expected concatenated message: '$message' to match 'The price is 10.00'")
        money = new Money('20')
        message = "The price is $money"
        check(message == "The price is \$20.00", "Expected concatenated message: '$message' to match 'The price is 20.00'")
    }

    void testStringFormatting() {
        def price = '10,000.000 total'
        def money = new Money(price)
        def expected = '$10,000.00'
        check(money.toString() == expected, "Input string '$price' gave value $money. Expected: $expected")

        price = '$999,234,879.738 total'
        money = new Money(price)
        expected = '$999,234,879.74'
        check(money.toString() == expected, "Input string '$price' gave value $money. Expected: $expected")

        price = new BigDecimal("374.4900")
        money = new Money(price)
        expected = '$374.49'
        check(money.toString() == expected, "Input BigDecimal '$price' gave value $money. Expected: $expected")

        price = new BigDecimal("0.99")
        money = new Money(price)
        expected = '$0.99'
        check(money.toString() == expected, "Input BigDecimal '$price' gave value $money. Expected: $expected")
    }

    void testCompareToNull() {
        String s = null
        Integer i = null
        Double d = null
        new Money(null)
        assert new Money('0') != s
        assert new Money(null) != i
        assert new Money('100') != d
    }

    void testRoundingMode() {
        Money subtotal = new Money(4155.50)
        def mod = '0.03'
        Money generatedRounded = subtotal * mod
        generatedRounded.setRoundingMode(RoundingMode.HALF_DOWN)
        Money generatedNoChange = subtotal * mod
        assert generatedNoChange == '124.67'
        assert generatedRounded.getExactValue() == new BigDecimal('124.665')
        assert generatedRounded == '124.66'
        assert generatedRounded.toString() == '$124.66'
        generatedRounded.setRoundingMode(RoundingMode.HALF_UP)
        assert generatedRounded == '124.67'
        assert generatedRounded.toString() == '$124.67'
    }

    void testNullInit() {
        Money m = new Money(null)
        assert m.toString() == 'null'
        assert m.getNumericalString() == 'null'
    }

    void testNullValueOperation() {
        Money a = new Money(10)
        Money b = new Money(null)
        a += b
        assert a == a
    }

    void testNullArithmetic() {
        Money a = new Money(10)
        assert a + null == a
        assert a * null == a
        assert a - null == a
        assert a / null == a
        assert a > null
        assert a != null
    }
}
