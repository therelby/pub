package framework.wss.pages.servicepage.reciperesizer

import above.RunWeb
import wss.pages.servicepage.RecipeResizerPage
import wss.user.UserDetail

class UtRecipeResizerImperialMeasurementSelection extends RunWeb {
    static void main(String[] args) {
        new UtRecipeResizerImperialMeasurementSelection().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    int pbi = 721377
    UserDetail user

    void test() {
        setup([
                author  : 'mnazat',
                title   : 'UtRecipeResizerImperialMeasurementSelection',
                PBI     : pbi,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'recipe resizer imperial measurement selection unit tests',
                logLevel: 'info'
        ])

        RecipeResizerPage recipeResizerPage = new RecipeResizerPage()

        recipeResizerPage.navigate()

        log("--")
        def valuesToSetImperialMeasurement = "Cups"
        assert recipeResizerPage.fillMeasurementDropdowns(valuesToSetImperialMeasurement, 0)
        assert recipeResizerPage.fillMeasurementDropdowns(valuesToSetImperialMeasurement, 1)
        assert recipeResizerPage.fillMeasurementDropdowns(valuesToSetImperialMeasurement, 2)
        assert recipeResizerPage.fillMeasurementDropdowns(valuesToSetImperialMeasurement, 3)
        assert recipeResizerPage.fillMeasurementDropdowns(valuesToSetImperialMeasurement, 4)
        assert recipeResizerPage.fillMeasurementDropdowns(valuesToSetImperialMeasurement, 5)

        recipeResizerPage.clickMetricRadioButton()

        assert recipeResizerPage.getMeasurementDropdownSelection(0) == valuesToSetImperialMeasurement
        assert recipeResizerPage.getMeasurementDropdownSelection(1) == valuesToSetImperialMeasurement
        assert recipeResizerPage.getMeasurementDropdownSelection(2) == valuesToSetImperialMeasurement
        assert recipeResizerPage.getMeasurementDropdownSelection(3) == valuesToSetImperialMeasurement
        assert recipeResizerPage.getMeasurementDropdownSelection(4) == valuesToSetImperialMeasurement
        assert recipeResizerPage.getMeasurementDropdownSelection(5) == valuesToSetImperialMeasurement

        closeBrowser()
    }
}
