package framework.runweb



import above.RunWeb;
import org.openqa.selenium.JavascriptExecutor;

 class FuncLoadUnitTest extends RunWeb {
    def test() {

        setup('vdiachuk', 'Page Load functionality unit test | Framework Self Testing Tool',
              ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test load page Error Sorry',
               'logLevel:info'])

        ///Loading Empty page and add "error" text to it - check error exists
        tryLoad("https://this-page-intentionally-left-blank.org/")
        assert !isPageError()
        String code =
                "body = document.querySelector('body');" +
                        "element = document.createElement('div');" +
                        "text = document.createTextNode('Sorry about that');" +
                        "element.appendChild(text);" +
                        "body.append(element);"
        JavascriptExecutor js = (JavascriptExecutor) webDriver
        js.executeScript(code)
        assert isPageError()
        assert getPageError() == 'Sorry about that'


    }
}