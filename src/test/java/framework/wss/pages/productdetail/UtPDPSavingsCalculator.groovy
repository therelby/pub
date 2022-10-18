package framework.wss.pages.productdetail

import above.RunWeb
import wss.pages.productdetail.savingsCalculator.PDPSavingsCalculator
import wss.pages.productdetail.savingsCalculator.SavingsCalculatorTypes

class UtPDPSavingsCalculator extends RunWeb {

    String url = 'carnival-king-ccm21ct-cotton-candy-machine-with-21-stainless-steel-bowl-and-cart-110v-1050w/382CCM21CT.html'

    def expectedInputLabels=[ "Cones per Hour",
                              "Sell Price per Cone",
                              "Cost per Cone"]

    def expectedOutputLabels=[  " Profit per Cone ",
                                " Machine Pays for Itself In ",
                                " Daily Profit "]
    LinkedHashMap<String,String> expectedInputLabelsWithDefaultValues=[ "Cones per Hour text": "Enter cones",
                                                                        "Cones per Hour value": "",
                                                                        "Sell Price per Cone text": "Enter price",
                                                                        "Sell Price per Cone value": "",
                                                                        "Cost per Cone text": "Enter cost",
                                                                        "Cost per Cone value": ".15"]

    PDPSavingsCalculator calc= new PDPSavingsCalculator(SavingsCalculatorTypes.COTTONCANDY)
    static void main(String[] args) {
        new UtPDPSavingsCalculator().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',

        ])
    }

    void test() {
        setup([author : 'mrizkallah', title: 'Savings calculator Unit testing', product: 'wss|dev,test',PBI: 0000,
               project: 'Webstaurant.StoreFront', keywords: 'Unit test'])
        assertions()
    }

    def assertions (){
        tryLoad(url)
        assert calc.isCalculatorDisplayed()
        assert calc.getCalcInputLabels() == expectedInputLabels
        assert  calc.getInputLabelsAndDefaultValues()==expectedInputLabelsWithDefaultValues
        assert calc.getCalcOutputLabels()==expectedOutputLabels
        LinkedHashMap actualOutputValues=  calc.getCalcOutputValue()
        def actualValues=actualOutputValues.values()
        assert actualValues.size()==0
        assert calc.fillCalculatorRandomData()
        assert  calc.fillCalculatorRandomData()
        assert calc.calculateSavings()
        actualOutputValues=  calc.getCalcOutputValue()
        actualValues=actualOutputValues.values()
        assert actualValues.size()>0
        assert calc.restCalculatorFields()
        assert calc.fillCottonCandyCalculator('20','6','.5')
    }
}