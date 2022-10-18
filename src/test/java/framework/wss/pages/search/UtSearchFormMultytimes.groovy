package framework.wss.pages.search

import above.RunWeb
import wss.pages.search.SearchForm

class UtSearchFormMultytimes extends RunWeb {

    def test() {
//try to investigate false response from SearchForm Class
        setup('vdiachuk', 'SearchForm multy time  search execution Unit test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test searchForm search form multytime',
                 "tfsTcIds:265471", 'logLevel:info'])

        tryLoad('homepage')
        def searchWords = ["case","top","table","sdfsdf", "qwerty", "poiuytr", "1234567890", "zxcvbnm1", "qmwnenrb", "[].;,.", "ASDFGHJ"]
        SearchForm searchForm = new SearchForm()

        //checking each word - 10 times
        for(String searchWord in searchWords){
            for(int attempt in 0..9){
                log "Attempt: $attempt for $searchWord"
                setLogLevel ("debug")
                assert searchForm.search(searchWord)
                setLogLevel ("info")
            }
            log "=="
        }


    }
}
