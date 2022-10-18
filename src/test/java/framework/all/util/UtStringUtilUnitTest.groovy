package framework.all.util

import above.RunWeb
import all.util.StringUtil

class UtStringUtilUnitTest extends RunWeb {
    def test() {

        setup('vdiachuk', 'all.util.StringUtil  Unit test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test find findElement', 'logLevel:debug'])


        int length = 100
        // checking string with spaces
        String strNoSpaces = StringUtil.randomString(length, false)
        log("Random String:$strNoSpaces")
        assert strNoSpaces.size() == length

        //checking string with no spaces
        String strSpaces = StringUtil.randomString(length, true)
        log("Random String:$strSpaces")
        assert strSpaces.size() == length

        assert StringUtil.isStringContainsOnlyDigits("4353652342341241234903056")
        assert !StringUtil.isStringContainsOnlyDigits(" 4356")
        assert !StringUtil.isStringContainsOnlyDigits("s4356")
        assert !StringUtil.isStringContainsOnlyDigits("4356W")
        assert !StringUtil.isStringContainsOnlyDigits("43[56")
        testFormatWithMap()
    }

    def testFormatWithMap() {
        assert bindingComplex() == "WSS is awesome. Hi from automation"
        assert classicComplex() == "WSS is awesome. Hi from automation"
        assert bindingSimple() == "WSS is very awesome"
        assert classicSimple() == "WSS is very awesome"
        assert bindingQuery() == '''\
                select top 10
                from tblWhatever
                where tblWhatever.property = value
                ORDER BY NEWID()'''.stripIndent()
        assert classicQuery() == '''\
                select top 10
                from tblWhatever
                where tblWhatever.property = value
                ORDER BY NEWID()'''.stripIndent()
    }

    /**
     * The following methods are for testFormatWithMap
     * @return
     */
     String bindingComplex() {
        def binding = [name: "wss", adjective: "awesome", var1: "Hi", var2: "from", var3: "automation"]
        return StringUtil.formatWithMap(stringStorage["bindingFormat"], binding)
    }

     String classicComplex() {
        return String.format(stringStorage["classicFormat"], "wss".toUpperCase(), "awesome", "Hi", "from", "automation")
                .stripIndent()
    }

     String bindingSimple() {
        def map = [adverb: "very", adjective: "awesome"]
        return StringUtil.formatWithMap(stringStorage["bindingSimple"], map)
    }

     String classicSimple() {
        return String.format(stringStorage["classicSimple"], "very", "awesome").stripIndent()
    }

     String bindingQuery() {
        def map = [quantity: 10, propertyValue: "value", extraQueryLine: "ORDER BY NEWID()"]
        return StringUtil.formatWithMap(stringStorage["bindingQuery"], map)
    }

     String classicQuery() {
        return String.format(stringStorage["classicQuery"], "10", "value", "ORDER BY NEWID()").stripIndent()
    }


    Map stringStorage = [
         bindingFormat : '${name.toUpperCase()} is $adjective. $var1 $var2 $var3',
         classicFormat : '%s is %s. %s %s %s',
         bindingSimple : 'WSS is $adverb $adjective',
        classicSimple : 'WSS is %s %s',
        bindingQuery : '''\
                select top $quantity 
                from tblWhatever
                where tblWhatever.property = $propertyValue
                $extraQueryLine''',
        classicQuery : '''\
                select top %s
                from tblWhatever
                where tblWhatever.property = %s
                %s''',
    ]
}




