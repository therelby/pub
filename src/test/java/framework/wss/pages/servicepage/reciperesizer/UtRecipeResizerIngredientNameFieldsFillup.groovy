package framework.wss.pages.servicepage.reciperesizer

import above.RunWeb
import wss.pages.servicepage.RecipeResizerPage
import wss.user.UserDetail

class UtRecipeResizerIngredientNameFieldsFillup extends RunWeb {
    static void main(String[] args) {
        new UtRecipeResizerIngredientNameFieldsFillup().testExecute([
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
                title   : 'UtRecipeResizerIngredientNameFieldsFillup',
                PBI     : pbi,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'recipe resizer ingredient name fields unit tests',
                logLevel: 'info'
        ])

        RecipeResizerPage recipeResizerPage = new RecipeResizerPage()

        recipeResizerPage.navigate()

        log("--")
        def valuesToSetIngredientName = "Flour"
        assert recipeResizerPage.fillIngredientNameFields(valuesToSetIngredientName, 0)
        assert recipeResizerPage.fillIngredientNameFields(valuesToSetIngredientName, 1)
        assert recipeResizerPage.fillIngredientNameFields(valuesToSetIngredientName, 2)
        assert recipeResizerPage.fillIngredientNameFields(valuesToSetIngredientName, 3)
        assert recipeResizerPage.fillIngredientNameFields(valuesToSetIngredientName, 4)
        assert recipeResizerPage.fillIngredientNameFields(valuesToSetIngredientName, 5)

        assert recipeResizerPage.getIngredientNameFieldData(0) != null
        assert recipeResizerPage.getIngredientNameFieldData(1) != null
        assert recipeResizerPage.getIngredientNameFieldData(2) != null
        assert recipeResizerPage.getIngredientNameFieldData(3) != null
        assert recipeResizerPage.getIngredientNameFieldData(4) != null
        assert recipeResizerPage.getIngredientNameFieldData(5) != null

        assert valuesToSetIngredientName == recipeResizerPage.getIngredientNameFieldData(0)
        assert valuesToSetIngredientName == recipeResizerPage.getIngredientNameFieldData(1)
        assert valuesToSetIngredientName == recipeResizerPage.getIngredientNameFieldData(2)
        assert valuesToSetIngredientName == recipeResizerPage.getIngredientNameFieldData(3)
        assert valuesToSetIngredientName == recipeResizerPage.getIngredientNameFieldData(4)
        assert valuesToSetIngredientName == recipeResizerPage.getIngredientNameFieldData(5)

        closeBrowser()
    }
}
