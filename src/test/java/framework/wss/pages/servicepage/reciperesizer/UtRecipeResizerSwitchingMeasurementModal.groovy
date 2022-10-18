package framework.wss.pages.servicepage.reciperesizer

import above.RunWeb
import wss.pages.servicepage.RecipeResizerPage
import wss.user.UserDetail

class UtRecipeResizerSwitchingMeasurementModal extends RunWeb {
    static void main(String[] args) {
        new UtRecipeResizerSwitchingMeasurementModal().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    int pbi = 281036
    UserDetail user

    void test() {
        setup([
                author  : 'mnazat',
                title   : 'UtRecipeResizerSwitchingMeasurementModal',
                PBI     : pbi,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'recipe resizer switching measurement modal unit tests',
                logLevel: 'info'
        ])

        RecipeResizerPage recipeResizerPage = new RecipeResizerPage()

        assert recipeResizerPage.navigate() == true

        log("--")
        def valuesToSet = [:]
        valuesToSet['RecipeName'] = "Sample Recipe Name"
        valuesToSet['OriginalServing'] = "1"
        valuesToSet['ResizedServing'] = "2"
        recipeResizerPage.fillRecipeNameAndServingSizes(valuesToSet)

        def valuesToSetQty = "1"
        recipeResizerPage.fillQtyFields(valuesToSetQty, 0)
        recipeResizerPage.fillQtyFields(valuesToSetQty, 1)
        recipeResizerPage.fillQtyFields(valuesToSetQty, 2)
        recipeResizerPage.fillQtyFields(valuesToSetQty, 3)
        recipeResizerPage.fillQtyFields(valuesToSetQty, 4)
        recipeResizerPage.fillQtyFields(valuesToSetQty, 5)

        recipeResizerPage.clickMetricRadioButton()
        assert recipeResizerPage.clickCancelButtonOnModal() == true
        assert recipeResizerPage.getQtyFieldData(0) != null
        assert recipeResizerPage.getQtyFieldData(1) != null
        assert recipeResizerPage.getQtyFieldData(2) != null
        assert recipeResizerPage.getQtyFieldData(3) != null
        assert recipeResizerPage.getQtyFieldData(4) != null
        assert recipeResizerPage.getQtyFieldData(5) != null
        assert valuesToSetQty == recipeResizerPage.getQtyFieldData(0)
        assert valuesToSetQty == recipeResizerPage.getQtyFieldData(1)
        assert valuesToSetQty == recipeResizerPage.getQtyFieldData(2)
        assert valuesToSetQty == recipeResizerPage.getQtyFieldData(3)
        assert valuesToSetQty == recipeResizerPage.getQtyFieldData(4)
        assert valuesToSetQty == recipeResizerPage.getQtyFieldData(5)

        recipeResizerPage.clickMetricRadioButton()
        assert recipeResizerPage.clickCloseIconOnModal() == true
        assert recipeResizerPage.getQtyFieldData(0) != null
        assert recipeResizerPage.getQtyFieldData(1) != null
        assert recipeResizerPage.getQtyFieldData(2) != null
        assert recipeResizerPage.getQtyFieldData(3) != null
        assert recipeResizerPage.getQtyFieldData(4) != null
        assert recipeResizerPage.getQtyFieldData(5) != null

        assert valuesToSetQty == recipeResizerPage.getQtyFieldData(0)
        assert valuesToSetQty == recipeResizerPage.getQtyFieldData(1)
        assert valuesToSetQty == recipeResizerPage.getQtyFieldData(2)
        assert valuesToSetQty == recipeResizerPage.getQtyFieldData(3)
        assert valuesToSetQty == recipeResizerPage.getQtyFieldData(4)
        assert valuesToSetQty == recipeResizerPage.getQtyFieldData(5)

        recipeResizerPage.clickMetricRadioButton()
        assert recipeResizerPage.clickSwitchButtonOnModal() == true
        assert recipeResizerPage.getQtyFieldData(0) == ""
        assert recipeResizerPage.getQtyFieldData(1) == ""
        assert recipeResizerPage.getQtyFieldData(2) == ""
        assert recipeResizerPage.getQtyFieldData(3) == ""
        assert recipeResizerPage.getQtyFieldData(4) == ""
        assert recipeResizerPage.getQtyFieldData(5) == ""
        assert valuesToSetQty != recipeResizerPage.getQtyFieldData(0)
        assert valuesToSetQty != recipeResizerPage.getQtyFieldData(1)
        assert valuesToSetQty != recipeResizerPage.getQtyFieldData(2)
        assert valuesToSetQty != recipeResizerPage.getQtyFieldData(3)
        assert valuesToSetQty != recipeResizerPage.getQtyFieldData(4)
        assert valuesToSetQty != recipeResizerPage.getQtyFieldData(5)

        closeBrowser()
    }
}
