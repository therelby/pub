package camp.element

import above.RunWeb
import camp.common.page.addcandidate.CampAddCandidatePage
import camp.common.page.element.CampFileUpload
import camp.test.page.addcandidatepage.HpAddCandidatePage

class UtCampFileUpload extends RunWeb {
    static void main(String[] args) {
        new UtCampFileUpload().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    CampAddCandidatePage addCandidatePage
    HpAddCandidatePage hpAddCandidatePage
    CampFileUpload fileUpload

    void test() {
        setup([
                author  : 'jreumont',
                title   : 'UtCampFileUpload',
                PBI     : 0,
                product : 'camp',
                project : 'Automation.Framework',
                keywords: 'unit test',
                logLevel: 'info'
        ])

        addCandidatePage = new CampAddCandidatePage()
        hpAddCandidatePage = new HpAddCandidatePage()
        hpAddCandidatePage.setAddCandidatePage(addCandidatePage)
        fileUpload = new CampFileUpload()

        hpAddCandidatePage.loginAndNavigate()

        assert fileUpload.isUploadButtonPresent()
        assert fileUpload.getUploadButtonLabel() == "Upload"
        assert fileUpload.isUploadSecondaryTextPresent()
        assert fileUpload.getUploadSecondaryText() == addCandidatePage.getFormInputPlaceholders().resumeUpload
        assert fileUpload.setUploadFile(hpAddCandidatePage.pdfFile)

        def tempArr = []
        tempArr = hpAddCandidatePage.pdfFile.split(/[\\]/)
        def resumeFileName = tempArr[tempArr.size() - 1]
        resumeFileName = resumeFileName.replace("/t", "")

        assert fileUpload.isUploadedFilePresent(resumeFileName)
        assert fileUpload.isUploadRemoveFileButtonPresent()
        assert fileUpload.clickUploadRemoveFileButton()
        assert fileUpload.waitForUploadRemoveFileButtonClear()
        assert !fileUpload.isUploadedFilePresent(resumeFileName)

        closeBrowser()
    }
}
