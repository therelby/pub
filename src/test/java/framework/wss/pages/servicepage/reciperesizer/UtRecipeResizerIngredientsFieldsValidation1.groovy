package framework.wss.pages.servicepage.reciperesizer

import above.RunWeb
import wss.pages.servicepage.RecipeResizerPage
import wss.user.UserDetail

class UtRecipeResizerIngredientsFieldsValidation1 extends RunWeb {
    static void main(String[] args) {
        new UtRecipeResizerIngredientsFieldsValidation1().testExecute([
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
                title   : 'UtRecipeResizerIngredientsFieldsValidation1',
                PBI     : pbi,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'recipe resizer ingredient fields validation unit test',
                logLevel: 'info'
        ])

        RecipeResizerPage recipeResizerPage = new RecipeResizerPage()

        recipeResizerPage.navigate()
        def ingredientTopErrorMessageXpath = recipeResizerPage.ingredientTopErrorMessageXpath

        def testData = [
                'RecipeName'      : 'test',
                'OriginalServing' : '1',
                'ResizedServing'  : '2'
        ]
        recipeResizerPage.fillRecipeNameAndServingSizes(testData)
        recipeResizerPage.clickRecipeResizerButton()

        waitForElement(ingredientTopErrorMessageXpath)

        def ingredientTopErrorMessageActualText = recipeResizerPage.getIngredientTopErrorMessage()
        def ingredientTopErrorMessageExpectedText = recipeResizerPage.ingredientTopErrorMessageExpectedText
        assert ingredientTopErrorMessageActualText != null
        assert ingredientTopErrorMessageExpectedText == ingredientTopErrorMessageActualText

        def qtyAndMeasurementWebElementsSize = recipeResizerPage.getQtyAndMeasurementWebElements().size()
        assert qtyAndMeasurementWebElementsSize == 1

        def actualErrorMessagesForEmptyQtyAndMeasurement = recipeResizerPage.getActualErrorMessagesForEmptyQtyAndMeasurement()
        def expectedErrorMessagesForEmptyQtyAndMeasurement = [
                ["index" : 0,
                 "text" : "At least one valid Ingredient must be provided before submitting your recipe for conversion."]
        ]

        assert actualErrorMessagesForEmptyQtyAndMeasurement != null
        assert actualErrorMessagesForEmptyQtyAndMeasurement.size() == 1
        assert actualErrorMessagesForEmptyQtyAndMeasurement == expectedErrorMessagesForEmptyQtyAndMeasurement

        closeBrowser()
    }
}
