package framework.runweb.funccleartextaction

import above.RunWeb
import org.openqa.selenium.WebElement

class UtClearTextAction extends RunWeb {

    static void main(String[] args) {
        new UtClearTextAction().testExecute([
                browser      : 'chrome',//'safari',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    def test() {
        setup([
                author  : 'vdiachuk',
                title   : 'Clear Text using Action | Framework Self Testing Tool',
                PBI     : 720998,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'runweb clear action',
                logLevel: 'info',
        ])

        String howManyDaysXpath = "//input[@id='howManyFryingDays']"
        tryLoad('/fryclone-50-lb-low-profile-portable-fryer-oil-filter-machine-with-pump-120v/259FLTRM50.html')

        // xpath
        log "Setting without clear: " + setText(howManyDaysXpath, '3')
        assert getAttributeSafe(howManyDaysXpath, 'value') == '13'


        assert clearTextAction(howManyDaysXpath)
        assert getAttributeSafe(howManyDaysXpath, 'value') == ''
        log "Setting with clear: " + setText(howManyDaysXpath, '3')
        assert getAttributeSafe(howManyDaysXpath, 'value') == '3'

        closeBrowser()
        tryLoad('/fryclone-50-lb-low-profile-portable-fryer-oil-filter-machine-with-pump-120v/259FLTRM50.html')

        // Webelement
        WebElement howManyDaysElement = find(howManyDaysXpath)
        log "Setting without clear: " + setText(howManyDaysElement, '3')
        assert getAttributeSafe(howManyDaysElement, 'value') == '13'

        assert clearTextAction(howManyDaysElement)
        assert getAttributeSafe(howManyDaysElement, 'value') == ''
        log "Setting with clear: " + setText(howManyDaysElement, '3')
        assert getAttributeSafe(howManyDaysElement, 'value') == '3'

        // negative
        tryLoad()
        assert clearTextAction(howManyDaysElement) == false
        assert clearTextAction(howManyDaysXpath) == false
        tryLoad('/fryclone-50-lb-low-profile-portable-fryer-oil-filter-machine-with-pump-120v/259FLTRM50.html')
        assert clearTextAction("//*[@id='FAKEID']") == false
    }
}
