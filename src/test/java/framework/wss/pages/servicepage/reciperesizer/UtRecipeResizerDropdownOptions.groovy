package framework.wss.pages.servicepage.reciperesizer

import above.RunWeb
import wss.pages.servicepage.RecipeResizerPage
import wss.user.UserDetail

class UtRecipeResizerDropdownOptions extends RunWeb {
    static void main(String[] args) {
        new UtRecipeResizerDropdownOptions().testExecute([
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
                title   : 'UtRecipeResizerDropdownOptions',
                PBI     : pbi,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'recipe resizer dropdown options unit test',
                logLevel: 'info'
        ])

        RecipeResizerPage recipeResizerPage = new RecipeResizerPage()
        recipeResizerPage.navigate()

        assert recipeResizerPage.getImperialDropdownActualTexts() != null
        assert recipeResizerPage.getImperialDropdownActualTexts().size() > 0
        assert recipeResizerPage.getImperialDropdownActualTexts().size() == 6
        assert recipeResizerPage.getImperialDropdownActualTexts()[0] == [
                "Dry"    : [
                        "1": "Cups",
                        "2": "Dashes",
                        "3": "Ounces",
                        "4": "Pinches",
                        "5": "Tablespoons",
                        "6": "Teaspoons",
                        "7": "Pounds",
                        "8": "Each"
                ],
                "Liquids": [
                        "9" : "Cups",
                        "10": "Gallons",
                        "11": "Ounces",
                        "12": "Pints",
                        "13": "Quarts",
                        "14": "Tablespoons",
                        "15": "Teaspoons"
                ]
        ]

        assert recipeResizerPage.getMetricDropdownActualTexts() != null
        assert recipeResizerPage.getMetricDropdownActualTexts().size() > 0
        assert recipeResizerPage.getMetricDropdownActualTexts().size() == 15
        assert recipeResizerPage.getMetricDropdownActualTexts() == [
            "1" : "Cups" ,
            "2" : "Dashes" ,
            "3" : "Ounces" ,
            "4" : "Pinches" ,
            "5" : "Tablespoons" ,
            "6" : "Teaspoons" ,
            "7" : "Pounds" ,
            "8" : "Each" ,
            "9" : "Cups" ,
            "10" : "Gallons" ,
            "11" : "Ounces" ,
            "12" : "Pints" ,
            "13" : "Quarts" ,
            "14" : "Tablespoons" ,
            "15" : "Teaspoons"
        ]

        closeBrowser()
    }
}
