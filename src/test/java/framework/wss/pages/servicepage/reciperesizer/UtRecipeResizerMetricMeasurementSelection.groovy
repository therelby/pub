package framework.wss.pages.servicepage.reciperesizer

import above.RunWeb
import wss.pages.servicepage.RecipeResizerPage
import wss.user.UserDetail

class UtRecipeResizerMetricMeasurementSelection extends RunWeb {
    static void main(String[] args) {
        new UtRecipeResizerMetricMeasurementSelection().testExecute([
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
                title   : 'UtRecipeResizerMetricMeasurementSelection',
                PBI     : pbi,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'recipe resizer metric measurement selection unit tests',
                logLevel: 'info'
        ])

        RecipeResizerPage recipeResizerPage = new RecipeResizerPage()

        recipeResizerPage.navigate()
        recipeResizerPage.clickMetricRadioButton()

        log("--")
        def valuesToSetMetricMeasurement = "Grams"
        assert recipeResizerPage.fillMeasurementDropdowns(valuesToSetMetricMeasurement, 0)
        assert recipeResizerPage.fillMeasurementDropdowns(valuesToSetMetricMeasurement, 1)
        assert recipeResizerPage.fillMeasurementDropdowns(valuesToSetMetricMeasurement, 2)
        assert recipeResizerPage.fillMeasurementDropdowns(valuesToSetMetricMeasurement, 3)
        assert recipeResizerPage.fillMeasurementDropdowns(valuesToSetMetricMeasurement, 4)
        assert recipeResizerPage.fillMeasurementDropdowns(valuesToSetMetricMeasurement, 5)

        assert recipeResizerPage.getMeasurementDropdownSelection(0) == valuesToSetMetricMeasurement
        assert recipeResizerPage.getMeasurementDropdownSelection(1) == valuesToSetMetricMeasurement
        assert recipeResizerPage.getMeasurementDropdownSelection(2) == valuesToSetMetricMeasurement
        assert recipeResizerPage.getMeasurementDropdownSelection(3) == valuesToSetMetricMeasurement
        assert recipeResizerPage.getMeasurementDropdownSelection(4) == valuesToSetMetricMeasurement
        assert recipeResizerPage.getMeasurementDropdownSelection(5) == valuesToSetMetricMeasurement

        closeBrowser()
    }
}
