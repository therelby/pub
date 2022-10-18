package framework.wss.pages.search

import above.RunWeb
import wss.pages.search.SearchForm

class UtSearchForm extends RunWeb {

    def tcIds = [265471]
    SearchForm searchForm

    // Test
    def test() {

        setup('kyilmaz', 'UtSearchForm',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test', "tfsTcIds:${tcIds.join(',')}", 'logLevel:debug'])
        searchForm = new SearchForm()
        log 'Testing...'
        testGetSynonymsForTerm()
        testGetRandomSearchTerms()
        testGetTopSearchTerms()
        testGetRandomTopSearchTerms()
    }

    void testGetSynonymsForTerm() {
        def ans = searchForm.getSynonymsForTerm('one')
        logData ans
        assert ans as Boolean
    }

    void testGetRandomSearchTerms() {
        def ans = searchForm.getRandomSearchTerms()
        logData ans
        assert ans as Boolean
    }

    void testGetTopSearchTerms() {
        def ans = searchForm.getTopSearchTerms()
        logData ans
        assert ans as Boolean
    }

    void testGetRandomTopSearchTerms() {
        def ans = searchForm.getRandomTopSearchTerms()
        logData ans
        assert ans as Boolean
    }

}
