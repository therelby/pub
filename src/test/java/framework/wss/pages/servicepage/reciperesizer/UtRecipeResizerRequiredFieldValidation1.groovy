package framework.wss.pages.servicepage.reciperesizer

import above.RunWeb
import wss.pages.servicepage.RecipeResizerPage
import wss.user.UserDetail

class UtRecipeResizerRequiredFieldValidation1 extends RunWeb {
    static void main(String[] args) {
        new UtRecipeResizerRequiredFieldValidation1().testExecute([
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
                title   : 'UtRecipeResizerRequiredFieldValidation1',
                PBI     : pbi,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'recipe resizer required field validation unit tests',
                logLevel: 'info'
        ])

        RecipeResizerPage recipeResizerPage = new RecipeResizerPage()

        assert recipeResizerPage.navigate() == true

        assert recipeResizerPage.getRecipeNameAndServingSizesWebElements() != null
        assert recipeResizerPage.getRecipeNameAndServingSizesWebElements().size() > 0
        assert recipeResizerPage.getRecipeNameAndServingSizesWebElements().size() == 3

        recipeResizerPage.clickRecipeResizerButton()

        assert recipeResizerPage.getRecipeNameAndServingSizeActualErrorMessages() != null
        assert recipeResizerPage.getRecipeNameAndServingSizeActualErrorMessages().size() > 0
        assert recipeResizerPage.getRecipeNameAndServingSizeActualErrorMessages().size() == 3
        assert recipeResizerPage.getRecipeNameAndServingSizeActualErrorMessages() == [
                "RecipeName"         : "Recipe Name is required",
                "OriginalServings"   : "Must be a whole number greater than 0.",
                "ResizedServings"    : "Needs to Serve must be a whole number."
        ]

        closeBrowser()
    }
}
