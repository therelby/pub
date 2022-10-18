package framework.wss.pages.element.userphotovideomodal

import above.RunWeb
import wss.pages.productdetail.modal.UserProductPhotoVideoModal

class UtUserPhotoVideoModal extends RunWeb {
    def test() {
        setup('vdiachuk', 'User Photo and Video(Submitted) Modal unit test  | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords: unit test modal user photo video submitted pdp  product detail page',
                 'PBI: 0',
                 'logLevel:info'])

        UserProductPhotoVideoModal userProductPhotoVideoModal = new UserProductPhotoVideoModal()
        // with modal
        tryLoad("https://www.dev.webstaurantstore.com/fineline-platter-pleasers-3601-cl-11-3-4-two-piece-clear-cake-stand-pack/9993601CL.html")


        List thumbnailElements = findElements("//div[@class='thumbs__item']")
        assert !userProductPhotoVideoModal.closeByX()
        assert !userProductPhotoVideoModal.isModalPresent()
        assert !userProductPhotoVideoModal.closeByClose()
        def modalData = userProductPhotoVideoModal.getCurrentModalData()
        assert modalData == [:]
//
        thumbnailElements.shuffled().getAt(0).click()
        assert userProductPhotoVideoModal.isModalPresent()
        assert userProductPhotoVideoModal.closeByX()
        assert !userProductPhotoVideoModal.closeByX()

        thumbnailElements.shuffled().getAt(0).click()
        assert userProductPhotoVideoModal.isModalPresent()
        assert userProductPhotoVideoModal.closeByClose()
        assert !userProductPhotoVideoModal.closeByClose()

        thumbnailElements.shuffled().getAt(0).click()
        assert userProductPhotoVideoModal.isModalPresent()
        assert userProductPhotoVideoModal.closeByClickingOutside()
        assert !userProductPhotoVideoModal.closeByClickingOutside()


        thumbnailElements.getAt(2).click()
        modalData = userProductPhotoVideoModal.getCurrentModalData()
        log  modalData
        assert modalData.getAt("caption") == "Great quality and easy to put together! This stand was awesome for this event! Happy I found them on this website and I will order again!"

        assert modalData['yellowStarsQuantity'] == 0
        assert modalData['textReview'] == ""
      //  log "video: " + modalData['video']


        tryLoad("https://www.dev.webstaurantstore.com/mercer-culinary-m60200bk1x-millennia-air-unisex-48-1x-customizable-black-short-sleeve-cook-shirt-with-traditional-buttons-and-full-mesh-back/47060200BK1X.html")
        List thumbnailElementsVideo = findElements("//div[@class='thumbs__item']")
        thumbnailElementsVideo.getAt(2).click()
        def modalVideoData = userProductPhotoVideoModal.getCurrentModalData()
        log  '' +modalVideoData
        assert modalVideoData.getAt("caption") == "Greatest chef shirt ever I love these the mesh back is great"
        assert modalVideoData['yellowStarsQuantity'] == 5
        assert modalVideoData['video'] == "https://cdn.webstaurantstore.com/videos/user-submit/47060200BKL_JQMNM4_tn.jpg"
    }
}
