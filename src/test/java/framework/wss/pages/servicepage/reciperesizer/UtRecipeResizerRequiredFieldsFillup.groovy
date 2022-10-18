package framework.wss.pages.servicepage.reciperesizer

import above.RunWeb
import wss.pages.servicepage.RecipeResizerPage
import wss.user.UserDetail

class UtRecipeResizerRequiredFieldsFillup extends RunWeb {
    static void main(String[] args) {
        new UtRecipeResizerRequiredFieldsFillup().testExecute([
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
                title   : 'UtRecipeResizerRequiredFieldsFillup',
                PBI     : pbi,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'recipe resizer required fields unit tests',
                logLevel: 'info'
        ])

        RecipeResizerPage recipeResizerPage = new RecipeResizerPage()

        assert recipeResizerPage.navigate() == true

        log("--")
        def valuesToSet = [:]
        valuesToSet['RecipeName'] = "Sample Recipe Name"
        valuesToSet['OriginalServing'] = "1"
        valuesToSet['ResizedServing'] = "2"
        log(valuesToSet)
        assert recipeResizerPage.fillRecipeNameAndServingSizes(valuesToSet)

        log "--"
        def formData = recipeResizerPage.getRecipeNameAndServingSizesData()
        log(formData)
        assert formData['RecipeName'] == valuesToSet['RecipeName']
        assert formData['OriginalServing'] == valuesToSet['OriginalServing']
        assert formData['ResizedServing'] == valuesToSet['ResizedServing']

        closeBrowser()
    }
}
