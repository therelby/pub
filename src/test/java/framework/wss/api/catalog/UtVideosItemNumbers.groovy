package framework.wss.api.catalog

import above.RunWeb
import wss.api.catalog.video.VideosItemNumbers

class UtVideosItemNumbers extends RunWeb {
    static void main(String[] args) {
        new UtVideosItemNumbers().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    void test() {
        setup([
                author  : 'kyilmaz',
                title   : 'UtVideosItemNumbers',
                PBI     : 0,
                product : 'wss',
                project : 'Automation.Framework',
                keywords: 'unit test',
                logLevel: 'info'
        ])
        testBasic()
    }

    void testBasic() {
        def call = new VideosItemNumbers('103BIBADAPT', [backFillRandomVideos: true])
        assert call.getResult()
    }
}
