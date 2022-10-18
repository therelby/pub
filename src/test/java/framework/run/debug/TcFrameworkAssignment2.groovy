package framework.run.debug

import above.RunWeb
import above.types.IssueCategory

class TcFrameworkAssignment2 extends RunWeb {

    static void main(String[] args) {
        new TcFrameworkAssignment2().testExecute([
                browser      : 'chrome',
                environment  : 'dev',
                remoteBrowser: false,
                runType      : 'debug',
        ])
    }

    void test() {
        setup([
                author  : 'vhardeo',
                title   : 'Framework Assignment 2',
                product : 'wss',
                PBI     : 702166,
                project : 'Automation Bootcamp',
                keywords: 'Assignment2'
        ])
        /**VARIABLES*/
        String searchField = '//*[@id="searchval"]'
        String searchButtonXpath = '//button[@value="Search"]'
        String searchWithinXpath = "//input[contains(@id,'within')]"
        String withinSearchBtnXpath = "//button[@aria-label='Search']"
        String searchResultPrice = "//p[@data-testid='price']"
        def searchTerm = "potato peeler"
        /**LOAD WSS DEV HOMEPAGE*/
        if (!verify(
                tryLoad('homepage'),
                [
                        id     : '32754e24-0971-4967-b13e-5b5df0e55ea1',
                        title  : 'Load WebstaurantStore Homepage',
                        details: [
                                lastIssue: lastIssue
                        ]
                ],
                IssueCategory.PAGE_LOADING
        )) {
            testStop()
        }
        /**ENTER SEARCH TERM*/
        if (!verify(
                setText(searchField, searchTerm),
                [
                        id     : '5b0dbef1-72e0-450f-bcbf-7bc034f157d7',
                        title  : 'Enter Search Term',
                        details: [
                                searchField : searchField,
                                searchTerm  : searchTerm
                        ]
                ],
                IssueCategory.PAGE_ELEMENT
        )) {
            testStop()
        }

    }

}