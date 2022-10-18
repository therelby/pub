package framework.wss.pages.servicepage.reciperesizer

import above.RunWeb
import wss.pages.servicepage.RecipeResizerPage
import wss.user.UserDetail

class UtRecipeResizerQtyFieldsFillup extends RunWeb {
    static void main(String[] args) {
        new UtRecipeResizerQtyFieldsFillup().testExecute([
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
                title   : 'UtRecipeResizerQtyFieldsFillup',
                PBI     : pbi,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'recipe resizer qty fields unit tests',
                logLevel: 'info'
        ])

        RecipeResizerPage recipeResizerPage = new RecipeResizerPage()

        recipeResizerPage.navigate()

        log("--")
        def valuesToSetQty = "1"
        assert recipeResizerPage.fillQtyFields(valuesToSetQty, 0)
        assert recipeResizerPage.fillQtyFields(valuesToSetQty, 1)
        assert recipeResizerPage.fillQtyFields(valuesToSetQty, 2)
        assert recipeResizerPage.fillQtyFields(valuesToSetQty, 3)
        assert recipeResizerPage.fillQtyFields(valuesToSetQty, 4)
        assert recipeResizerPage.fillQtyFields(valuesToSetQty, 5)

        closeBrowser()
    }
}
