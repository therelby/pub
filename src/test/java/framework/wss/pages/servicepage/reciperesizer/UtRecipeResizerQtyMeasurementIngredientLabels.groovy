package framework.wss.pages.servicepage.reciperesizer

import above.RunWeb
import wss.pages.servicepage.RecipeResizerPage
import wss.user.UserDetail

class UtRecipeResizerQtyMeasurementIngredientLabels extends RunWeb {
    static void main(String[] args) {
        new UtRecipeResizerQtyMeasurementIngredientLabels().testExecute([
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
                title   : 'UtRecipeResizerQtyMeasurementIngredientLabels',
                PBI     : pbi,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'recipe resizer qty measurement ingredient labels unit tests',
                logLevel: 'info'
        ])

        RecipeResizerPage recipeResizerPage = new RecipeResizerPage()

        assert recipeResizerPage.navigate() == true

        assert recipeResizerPage.getQtyMeasurementIngredientsLabelsWebElements() != null
        assert recipeResizerPage.getQtyMeasurementIngredientsLabelsWebElements().size() > 0
        assert recipeResizerPage.getQtyMeasurementIngredientsLabelsWebElements().size() == 3

        assert recipeResizerPage.getQtyMeasurementIngredientsActualLabels() != null
        assert recipeResizerPage.getQtyMeasurementIngredientsActualLabels().size() > 0
        assert recipeResizerPage.getQtyMeasurementIngredientsActualLabels().size() == 3
        assert recipeResizerPage.getQtyMeasurementIngredientsActualLabels() == ["QTY.", "MEASUREMENT", "INGREDIENT"]

        assert recipeResizerPage.getQtyFieldsWebElements() != null
        assert recipeResizerPage.getQtyFieldsWebElements().size() > 0
        assert recipeResizerPage.getQtyFieldsWebElements().size() == 6

        assert recipeResizerPage.getIngredientFieldsWebElements() != null
        assert recipeResizerPage.getIngredientFieldsWebElements().size() > 0
        assert recipeResizerPage.getIngredientFieldsWebElements().size() == 6

        assert recipeResizerPage.getMeasurementDropdownsWebElements() != null
        assert recipeResizerPage.getMeasurementDropdownsWebElements().size() > 0
        assert recipeResizerPage.getMeasurementDropdownsWebElements().size() == 6

        closeBrowser()
    }
}
