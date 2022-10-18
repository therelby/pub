package framework.wss.pages.account

import above.RunWeb
import wss.pages.account.MyReviews
import wss.user.userurllogin.UserUrlLogin

class UtMyReviews extends RunWeb {

    def test() {

        setup('vdiachuk', 'Unit test Reviews page in My Account | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords: unit test Review page user account my  ',
                 'PBI: 0',
                 'logLevel: info'])
        UserUrlLogin userUrlLogin = new UserUrlLogin()
        MyReviews myReviews = new MyReviews()
        tryLoad('homepage')
        assert !myReviews.isMyReviewsPage()

        userUrlLogin.loginNewUser("Regular User", 0)

        assert myReviews.navigate()
        assert myReviews.isMyReviewsPage()

    }
}
