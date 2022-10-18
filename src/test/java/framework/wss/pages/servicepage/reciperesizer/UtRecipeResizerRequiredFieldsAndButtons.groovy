package framework.wss.pages.servicepage.reciperesizer

import above.RunWeb
import wss.pages.servicepage.RecipeResizerPage
import wss.user.UserDetail

class UtRecipeResizerRequiredFieldsAndButtons extends RunWeb {
    static void main(String[] args) {
        new UtRecipeResizerRequiredFieldsAndButtons().testExecute([
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
                title   : 'UtRecipeResizerMainContainerInputsAndButtons',
                PBI     : pbi,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'recipe resizer main container inputs and buttons unit test',
                logLevel: 'info'
        ])

        RecipeResizerPage recipeResizerPage = new RecipeResizerPage()

        assert recipeResizerPage.navigate() == true
        assert recipeResizerPage.clickImperialRadioButton() == true
        assert recipeResizerPage.clickMetricRadioButton() == true
        assert recipeResizerPage.clickAddIngredientButton() == true
        assert recipeResizerPage.clickRecipeResizerButton() == true
        assert recipeResizerPage.clickClearButton() == true

        assert recipeResizerPage.getRecipeResizerLabelsWebElements() != null
        assert recipeResizerPage.getRecipeResizerLabelsWebElements().size() > 0
        assert recipeResizerPage.getRecipeResizerLabelsWebElements().size() == 4

        assert recipeResizerPage.getResizeYourRecipeLabelsActualTexts() != null
        assert recipeResizerPage.getResizeYourRecipeLabelsActualTexts().size() > 0
        assert recipeResizerPage.getResizeYourRecipeLabelsActualTexts().size() == 4
        assert recipeResizerPage.getResizeYourRecipeLabelsActualTexts() == [
            "RecipeName"        : "Recipe Name",
            "Notes"             : "Notes",
            "OriginalServings"  : "Original Recipe Serves",
            "ResizedServings"   : "Needs to Serve"
        ]

        assert recipeResizerPage.getIngredientsRadioButtonsWebElements() != null
        assert recipeResizerPage.getIngredientsRadioButtonsWebElements().size() > 0
        assert recipeResizerPage.getIngredientsRadioButtonsWebElements().size() == 2

        assert recipeResizerPage.getIngredientsRadioButtonNamesWebElements() != null
        assert recipeResizerPage.getIngredientsRadioButtonNamesWebElements().size() > 0
        assert recipeResizerPage.getIngredientsRadioButtonNamesWebElements().size() == 2

        assert recipeResizerPage.getIngredientsRadioButtonsActualNames() != null
        assert recipeResizerPage.getIngredientsRadioButtonsActualNames().size() > 0
        assert recipeResizerPage.getIngredientsRadioButtonsActualNames().size() == 2
        assert recipeResizerPage.getIngredientsRadioButtonsActualNames() == ["Imperial", "Metric"]
        assert recipeResizerPage.getMeasurementDropdownsWebElements().size() > 0
        assert recipeResizerPage.getMeasurementDropdownsWebElements().size() == 6

        closeBrowser()
    }
}
