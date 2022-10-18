package framework.wss.api.catalogadmin

import above.RunWeb
import wss.api.catalogadmin.AdminPipeline

class UtAdminPipeline extends RunWeb {
    static void main(String[] args) {
        new UtAdminPipeline().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    void test() {
        setup([
                author  : 'kyilmaz',
                title   : 'UtAdminPipeline',
                PBI     : 0,
                product : 'wss',
                project : 'Automation.Framework',
                keywords: 'unit test',
                logLevel: 'info'
        ])
        testPreviewPage()
    }

    void testPreviewPage() {
        Map singleRequest = AdminPipeline.previewPageStatus("92236359")
        assert singleRequest.containsKey("92236359")
        Map listRequest = AdminPipeline.previewPageStatus(["123^g", "456^g"])
        assert listRequest.containsKey("123^g") && listRequest.containsKey("456^g")
        Map duplicatedRequest = AdminPipeline.previewPageStatus(["123^g", "123^g", "456^g"])
        assert duplicatedRequest == [:]
    }

}
