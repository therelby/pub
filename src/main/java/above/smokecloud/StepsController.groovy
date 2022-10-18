package above.smokecloud

import wsssmoke.StepLevel
import wsssmoke.element.compareproductelement.SmCompareProductElement


/**
 *      Smoke Cloud Testing Controller
 */

abstract class StepsController extends UrlController {

    /** Step Execution */
    def stepExecute(String product, String url, String step, Map options) {

        return

        if (product == 'wss') {

            switch (step) {
            //
            // HEADER
            //
                case 'header-account':
                    stepProcessor(new wsssmoke.header.topmenu.SmAccountTop(), options, url, step)
                    break
                case 'header-cart':
                    stepProcessor(new wsssmoke.header.topmenu.SmCartTop(), options, url, step)
                    break
                case 'header-chat-now':
                    stepProcessor(new wsssmoke.header.topmenu.SmChatNow(), options, url, step)
                    break
                case 'header-logo-element':
                    stepProcessor(new wsssmoke.header.logo.SmLogo(), options, url, step)
                    break
                case 'header-main-menu':
                    stepProcessor(new wsssmoke.header.mainmenu.SmMainMenu(), options, url, step)
                    break
                case 'header-main-menu-sub-menu':
                    stepProcessor(new wsssmoke.header.mainmenu.SmSubMenu(), options, url, step)
                    break
                case 'header-search-form':
                    stepProcessor(new wsssmoke.header.searchform.SmSearchForm(), options, url, step)
                    break
                    //
                    // HTTP
                    //
                case 'http-request-headers':
                    stepProcessor(new wsssmoke.http.SmHttpRequestHeader(), options, url, step)
                    break
                    //
                    // HTML
                    //
                case 'html-a-element':
                    stepProcessor(new wsssmoke.html.element.SmLinkAElement(), options, url, step)
                    break
                case 'html-element-essential':
                    stepProcessor(new wsssmoke.html.element.SmEssentialElement(), options, url, step)
                    break
                case 'html-header-element':
                    stepProcessor(new wsssmoke.html.header.SmHeaderElement(), options, url, step)
                    break
                case 'html-image-element':
                    stepProcessor(new wsssmoke.html.image.SmImageElement(), options, url, step)
                    break
                case 'html-script-element':
                    stepProcessor(new wsssmoke.html.element.SmScriptElement(), options, url, step)
                    break
                case 'html-svg-element':
                    stepProcessor(new wsssmoke.html.image.SmImageSvgElement(), options, url, step)
                    break
                    //
                    // FOOTER
                    //
                case 'footer-copyright':
                    stepProcessor(new wsssmoke.footer.copyright.SmCopyright(), options, url, step)
                    break
                case 'footer-credit-card-icons':
                    stepProcessor(new wsssmoke.footer.creditcardicon.SmCreditCardIcon(), options, url, step)
                    break
                case 'footer-feedback-form':
                    stepProcessor(new wsssmoke.footer.feedbackform.SmFeedbackForm(), options, url, step)
                    break
                case 'footer-food-resources':
                    stepProcessor(new wsssmoke.footer.foodresources.SmFoodResources(), options, url, step)
                    break
                case 'footer-mailing-list-subscription':
                    stepProcessor(new wsssmoke.footer.mailinglist.SmMailingList(), options, url, step)
                    break
                case 'footer-menu':
                    stepProcessor(new wsssmoke.footer.footermenu.SmFooterMenu(), options, url, step)
                    break
                case 'footer-social-icon':
                    stepProcessor(new wsssmoke.footer.socialicon.SmSocialIcon(), options, url, step)
                    break
                case 'footer-text-module':
                    stepProcessor(new wsssmoke.footer.textmodule.SmFooterTextModule(), options, url, step)
                    break

                    //
                    // ELEMENTS - test steps for Common Elements that can be found on different pages but not belongs to Footer or Header
                    //
                case 'element-breadcrumb':
                    stepProcessor(new wsssmoke.element.SmBreadcrumb(), options, url, step)
                    break
                case 'element-compare-product-mode':
                    stepProcessor(new SmCompareProductElement(), options, url, step)
                    break
                case 'element-filter':
                    stepProcessor(new wsssmoke.element.SmFilterElement(), options, url, step)
                    break
                case 'element-pagination':
                    stepProcessor(new wsssmoke.element.SmPaginationElement(), options, url, step)
                    break
                case 'element-product-view':
                    stepProcessor(new wsssmoke.element.SmProductViewElement(), options, url, step)
                    break
                case 'element-sort-by':
                    stepProcessor(new wsssmoke.element.SmSortByElement(), options, url, step)
                    break
                case 'element-top-products-module':
                    stepProcessor(new wsssmoke.element.SmTopProductsModule(), options, url, step)
                    break

                    //
                    // PAGE SPECIFIC
                    //
                case 'page-specific-help-center':
                    stepProcessor(new wsssmoke.page.helpcenter.SmHelpCenter(), options, url, step)
                    break
                case 'page-specific-home-page-banner':
                    stepProcessor(new wsssmoke.page.home.SmBannerHomePage(), options, url, step)
                    break
                case 'page-specific-registration':
                    stepProcessor(new wsssmoke.page.register.SmRegisterPage(), options, url, step)
                    break
                case 'page-specific-request-quote':
                    stepProcessor(new wsssmoke.page.requestquote.SmRequestQuote(), options, url, step)
                    break
                case 'page-specific-shop-all':
                    stepProcessor(new wsssmoke.page.shopall.SmShopAll(), options, url, step)
                    break
                case 'page-specific-policy':
                    stepProcessor(new wsssmoke.page.policy.SmPolicyPage(), options, url, step)
                    break
                case 'page-specific-search-no-result':
                    stepProcessor(new wsssmoke.page.searchpage.SmSearchNoResult(), options, url, step)
                    break
                case 'page-specific-search-with-result':
                    stepProcessor(new wsssmoke.page.searchpage.SmSearchWithResult(), options, url, step)
                    break
                case 'page-specific-rewards':
                    stepProcessor(new wsssmoke.page.reward.SmRewardsPage(), options, url, step)
                    break
                case 'page-specific-pdp-social':
                    stepProcessor(new wsssmoke.page.pdp.SmPDPSocialButton(), options, url, step)
                    break
                case 'page-specific-pdp-top-block':
                    stepProcessor(new wsssmoke.page.pdp.SmPDPTopBlock(), options, url, step)
                    break
                case 'page-specific-pdp-rating':
                    stepProcessor(new wsssmoke.page.pdp.SmPDPRating(), options, url, step)
                    break
                case 'page-specific-pdp-leave-review':
                    stepProcessor(new wsssmoke.page.pdp.SmPDPLeaveReview(), options, url, step)
                    break
                case 'page-specific-pdp-shipping':
                    stepProcessor(new wsssmoke.page.pdp.SmPDPShipping(), options, url, step)
                    break
                case 'page-specific-pdp-present-review':
                    stepProcessor(new wsssmoke.page.pdp.SmPDPPresentReview(), options, url, step)
                    break

                case 'page-specific-about-us':
                    stepProcessor(new wsssmoke.page.aboutus.SmAboutUsPage(), options, url, step)
                    break
                case 'page-specific-scholarship':
                    stepProcessor(new wsssmoke.page.scholarship.SmScholarshipPage(), options, url, step)
                    break
                    //
                    // Sample/sandbox step
                    //
                    // case 'sample/sandbox':
                    //    stepProcessor(new wsssmoke.SmSample(), options, url, step)
                    // break
                default:
                    throw new Exception("Unknown step for WSS product - [$step]")
                    break
            }

        } else {
            throw new Exception("Bad product [$product]")
        }

    }

    private stepProcessor(StepLevel stepBase, Map options, String url, String step, Map params = null) {

        return

        // step stats init
        smokeCloudStatistics1(stepBase, step, url)

        // running test
        Exception result = null
        try {
            if (options.testLevel >= 1) {
                stepBase.basicCheck(url, params)
            }
            checkUrl(url)
            if (options.testLevel >= 2) {
                stepBase.mediumCheck(url, params)
            }
            checkUrl(url)
            if (options.testLevel == 3) {
                stepBase.highCheck(url, params)
            }
        } catch (Exception e) {
            String err = e.getMessage()
            if (err && err.contains('Smoke Cloud CALLBACK GOT STOP COMMAND')) {
                smokeCloudStopTesting = true
            } else if (err && err.contains('Automation Framework STOPPED the test because of too much fails came')) {
            } else if (err && err.contains('(!) FATAL: Smoke Cloud page loading handler')) {
                smokeCloudStopTesting = true
            } else {
                result = e
                StringWriter stringWriter = new StringWriter()
                PrintWriter printWriter = new PrintWriter(stringWriter)
                e.printStackTrace(printWriter)
                log(stringWriter.toString(), console_color_yellow)
                stringWriter = null
                printWriter = null
            }
        }

        // step stats final
        if (result) {
            runFailed = true
            scriptError = result.getMessage()
        }
        smokeCloudStatistics2()
        runFailed = false
        scriptError = ''

        stepBase = null
    }

}
