package framework.wss.pages.servicepage.reciperesizer

import above.RunWeb
import wss.pages.servicepage.RecipeResizerPage
import wss.user.UserDetail

class UtRecipeResizerRelatedLinks extends RunWeb {
    static void main(String[] args) {
        new UtRecipeResizerRelatedLinks().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    int pbi = 281263
    UserDetail user

    void test() {
        setup([
                author  : 'mnazat',
                title   : 'UtRecipeResizerRelatedLinks',
                PBI     : pbi,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'recipe resizer related links unit test',
                logLevel: 'info'
        ])

        RecipeResizerPage recipeResizerPage = new RecipeResizerPage()
        recipeResizerPage.navigate()

        assert recipeResizerPage.getRelatedLinksElements() != null
        assert recipeResizerPage.getRelatedLinksElements().size() > 0
        assert recipeResizerPage.getRelatedLinksElements().size() == 8

        assert recipeResizerPage.getRelatedLinksInfo() != null
        assert recipeResizerPage.getRelatedLinksInfo().size() > 0
        assert recipeResizerPage.getRelatedLinksInfo().size() == 2
        assert recipeResizerPage.getRelatedLinksInfo()[0] == [
                "index"           : 0,
                "iconLink"        : "https://cdnimg.webstaurantstore.com/uploads/design/2021/8/food-service-resource-icon.jpg",
                "title"           : "Food Service Resources",
                "titleLink"       : getUrl('hp') + "/food-service-resources.html",
                "desc"            : "Articles, buying guides, tips, and more",
                "resourceLinkText": "Explore Resources",
                "resourceLink"    : getUrl('hp') + "/food-service-resources.html"
        ]
        assert recipeResizerPage.getRelatedLinksInfo()[1] == [
                "index"           : 1,
                "iconLink"        : "https://cdnimg.webstaurantstore.com/uploads/design/2021/8/recipe-icon.jpg",
                "title"           : "Our Recipes",
                "titleLink"       : getUrl('hp') + "/food-service-resources/recipes.html",
                "desc"            : "Check out our money making recipes",
                "resourceLinkText": "Explore Recipes",
                "resourceLink"    : getUrl('hp') + "/food-service-resources/recipes.html"
        ]
        closeBrowser()
    }
}
