package framework.wss.pages.element.footer

import above.RunWeb
import wss.pages.element.footer.FeedbackForm
import wss.pages.element.footer.FooterMenu

class UtFeedbackForm extends RunWeb {
    def test() {

        setup('vdiachuk', 'Feedback Form - Footer - Unit test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test footer feedback form',
                 "tfsTcIds:265471",
                 'logLevel:info'])

        tryLoad('homepage')
        FeedbackForm feedbackForm = new FeedbackForm()
        assert feedbackForm.isFeedbackForm()
        assert feedbackForm.isGoogleReview()


        tryLoad("https://www.google.com/")
        assert !feedbackForm.isFeedbackForm()
        assert !feedbackForm.isGoogleReview()


    }
}
