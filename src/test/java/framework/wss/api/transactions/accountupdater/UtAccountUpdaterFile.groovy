package framework.wss.api.transactions.accountupdater

import above.RunWeb
import all.TextFile
import wss.api.transactions.accountupdater.AccountUpdaterFile
import wss.creditcard.CreditCardGenerator
import wss.pages.account.PaymentPage
import wss.user.UserDetail
import wss.user.UserUtil
import wss.user.userurllogin.UserUrlLogin

class UtAccountUpdaterFile extends RunWeb {

    static void main(String[] args) {
        new UtAccountUpdaterFile().testExecute([:])
    }

    void test() {

        setup([
                author  : 'jglisson',
                title   : 'Account Updater File creator unit tests',
                PBI     : 594385,
                product : 'wss|dev',
                project : 'Automation Projects',
                keywords: 'account updater'
        ])


        AccountUpdaterFile accountUpdaterFile = new AccountUpdaterFile()

        /** In order to unit test we need a card of each type on a user account, so they are added*/
        def PBI = 594385
        Map cards = [:]
        UserUrlLogin userUrlLogin = new UserUrlLogin()

        for (cardType in accountUpdaterFile.cardTypes){
            String cardNumber
            if (cardType == 'Rewards') {cardNumber = '4418397890000019'} //not sure how to handle this
            else if (cardType == 'American Express') {cardNumber = CreditCardGenerator.getSpecialCard('Amex', '0')}
            else if (cardType == 'Mastercard') {cardNumber = CreditCardGenerator.getSpecialCard('MasterCard', '0')}
            else {cardNumber = CreditCardGenerator.getSpecialCard(cardType, '0')}
            cards[cardType] = cardNumber
        }
        closeBrowser()


        UserDetail userDetail = userUrlLogin.loginNewUser('Regular User', PBI)
        UserUtil.createUserWithRewardsSubscription(userDetail.detail.index.toInteger())

        PaymentPage paymentPage = new PaymentPage()
        paymentPage.navigate()

        /** Testing Add Line for Each card type */
        Map lineData = [:]
        for (card in cards){
            paymentPage.addNewCreditCard(card.key, card.value, '111', '12', '2025')
            waitForElementInvisible(paymentPage.addNewnameOnCardXpath)
            sleep(1000)
            //Update
            if (card.key != 'American Express'){
                lineData = accountUpdaterFile.addLine('Update', card.key, userDetail.detail.index, card.key)
                checkLineData(lineData, accountUpdaterFile, 'Update')
            }
            else {
                lineData = accountUpdaterFile.addLine('Update', card.key, userDetail.detail.index, card.key, true)
                checkLineData(lineData, accountUpdaterFile, 'Update')
                lineData = accountUpdaterFile.addLine('Update', card.key, userDetail.detail.index, card.key, false)
                checkLineData(lineData, accountUpdaterFile, 'Update')
            }
            //Expiration, no card type variants
            lineData = accountUpdaterFile.addLine('Expiration', card.key, userDetail.detail.index, card.key, null, true)
            checkLineData(lineData, accountUpdaterFile, 'Expiration')
            lineData = accountUpdaterFile.addLine('Expiration', card.key, userDetail.detail.index, card.key, null, false)
            checkLineData(lineData, accountUpdaterFile, 'Expiration')
            //delete
            if (card.key == 'Mastercard'){
                for (mcScenario in accountUpdaterFile.mastercardNOSubScenarios){
                    lineData = accountUpdaterFile.addLine('Delete', card.key, userDetail.detail.index, card.key, true, true, mcScenario)
                    checkLineData(lineData, accountUpdaterFile, 'Delete')
                    lineData = accountUpdaterFile.addLine('Delete', card.key, userDetail.detail.index, card.key, false, false, mcScenario)
                    checkLineData(lineData, accountUpdaterFile, 'Delete')
                }
            }
            else {
                lineData = accountUpdaterFile.addLine('Delete', card.key, userDetail.detail.index, card.key, true, true)
                checkLineData(lineData, accountUpdaterFile, 'Delete')
                lineData = accountUpdaterFile.addLine('Delete', card.key, userDetail.detail.index, card.key, false, false)
                checkLineData(lineData, accountUpdaterFile, 'Delete')
            }
            //No change
            if (card.key == 'Visa' || card.key == 'Rewards'){
                lineData = accountUpdaterFile.addLine('No Change', card.key, userDetail.detail.index, card.key, true, true, accountUpdaterFile.visaSubScenarios.first())
                checkLineData(lineData, accountUpdaterFile, 'No Change')
                lineData = accountUpdaterFile.addLine('No Change', card.key, userDetail.detail.index, card.key, false, false, accountUpdaterFile.visaSubScenarios.first())
                checkLineData(lineData, accountUpdaterFile, 'No Change')
            }
            else if (card.key == 'Mastercard'){
                lineData = accountUpdaterFile.addLine('No Change', card.key, userDetail.detail.index, card.key, true, true, accountUpdaterFile.mastercardSubScenarios.first())
                checkLineData(lineData, accountUpdaterFile, 'No Change')
                lineData = accountUpdaterFile.addLine('No Change', card.key, userDetail.detail.index, card.key, false, false, accountUpdaterFile.mastercardSubScenarios.first())
                checkLineData(lineData, accountUpdaterFile, 'No Change')
            }
            else if (card.key == 'Discover'){
                lineData = accountUpdaterFile.addLine('No Change', card.key, userDetail.detail.index, card.key, true, true, accountUpdaterFile.discoverSubScenarios.first())
                checkLineData(lineData, accountUpdaterFile, 'No Change')
                lineData = accountUpdaterFile.addLine('No Change', card.key, userDetail.detail.index, card.key, false, false, accountUpdaterFile.discoverSubScenarios.first())
                checkLineData(lineData, accountUpdaterFile, 'No Change')
            }
            else {//amex
                lineData = accountUpdaterFile.addLine('No Change', card.key, userDetail.detail.index, card.key, true, true, accountUpdaterFile.americanExpressSubScenarios.first())
                checkLineData(lineData, accountUpdaterFile, 'No Change')
                lineData = accountUpdaterFile.addLine('No Change', card.key, userDetail.detail.index, card.key, false, false, accountUpdaterFile.americanExpressSubScenarios.first())
                checkLineData(lineData, accountUpdaterFile, 'No Change')
            }
        }

        AccountUpdaterFile accountUpdaterFile2 = new AccountUpdaterFile()

        List<Map> requestedCards = []
        requestedCards.add([scenario: 'Update', cardType: 'Visa', userIndex: userDetail.detail.index, cardName: 'Visa', provideExpiration: null, provideToken: null, subScenario: null ])
        requestedCards.add([scenario: 'Expiration', cardType: 'Mastercard', userIndex: userDetail.detail.index, cardName: 'Mastercard', provideExpiration: null, provideToken: false, subScenario: null ])
        requestedCards.add([scenario: 'Delete', cardType: 'Discover', userIndex: userDetail.detail.index, cardName: 'Discover', provideExpiration: true, provideToken: true, subScenario: null ])
        requestedCards.add([scenario: 'No Change', cardType: 'American Express', userIndex: userDetail.detail.index, cardName: 'American Express', provideExpiration: false, provideToken: false, subScenario: 'XF' ])

        List<Map> addedCards = accountUpdaterFile2.addMultipleLines(requestedCards)

        assert addedCards.size() == 4
        assert accountUpdaterFile2.lines.size() == 4
        log accountUpdaterFile2.lines
        for (addedCard in addedCards){
            log addedCard
            checkLineData(addedCard, accountUpdaterFile2, addedCard.scenario)
        }

        accountUpdaterFile2.saveFile()

        TextFile textFile = new TextFile(accountUpdaterFile2.fileLocation)
        assert textFile.getLinesCount() == 6


        /** Testing for validateLineOptions, tests all scenario combos, and invalid ones of each type */
        for (scenario in accountUpdaterFile.scenarios){
            if (scenario == 'No Change'){
                for (cardType in accountUpdaterFile.cardTypes){
                    assert !accountUpdaterFile.validateLineOptions(scenario, cardType, false, false, null)
                    assert !accountUpdaterFile.validateLineOptions(scenario, cardType, true, true, null)
                }
                for (subScenario in accountUpdaterFile.visaSubScenarios){
                    assert accountUpdaterFile.validateLineOptions(scenario, 'Visa', false, false, subScenario)
                    assert !accountUpdaterFile.validateLineOptions(scenario, 'Visa', false, false, 'TEST')
                    assert !accountUpdaterFile.validateLineOptions(scenario, 'TEST', false, false, subScenario)
                    assert accountUpdaterFile.validateLineOptions(scenario, 'Visa', true, true, subScenario)
                    assert !accountUpdaterFile.validateLineOptions(scenario, 'Visa', true, true, 'TEST')
                    assert !accountUpdaterFile.validateLineOptions(scenario, 'TEST', true, true, subScenario)
                    assert !accountUpdaterFile.validateLineOptions(scenario, 'Visa', true, null, subScenario)
                    assert !accountUpdaterFile.validateLineOptions(scenario, 'Visa', null, true, subScenario)
                }
                for (subScenario in accountUpdaterFile.mastercardSubScenarios){
                    assert accountUpdaterFile.validateLineOptions(scenario, 'Mastercard', false, false, subScenario)
                    assert !accountUpdaterFile.validateLineOptions(scenario, 'Mastercard', false, false, 'TEST')
                    assert !accountUpdaterFile.validateLineOptions(scenario, 'TEST', false, false, subScenario)
                    assert accountUpdaterFile.validateLineOptions(scenario, 'Mastercard', true, true, subScenario)
                    assert !accountUpdaterFile.validateLineOptions(scenario, 'Mastercard', true, true, 'TEST')
                    assert !accountUpdaterFile.validateLineOptions(scenario, 'TEST', true, true, subScenario)
                    assert !accountUpdaterFile.validateLineOptions(scenario, 'Mastercard', true, null, subScenario)
                    assert !accountUpdaterFile.validateLineOptions(scenario, 'Mastercard', null, true, subScenario)
                }
                for (subScenario in accountUpdaterFile.discoverSubScenarios){
                    assert accountUpdaterFile.validateLineOptions(scenario, 'Discover', false, false, subScenario)
                    assert !accountUpdaterFile.validateLineOptions(scenario, 'Discover', false, false, 'TEST')
                    assert !accountUpdaterFile.validateLineOptions(scenario, 'TEST', false, false, subScenario)
                    assert accountUpdaterFile.validateLineOptions(scenario, 'Discover', true, true, subScenario)
                    assert !accountUpdaterFile.validateLineOptions(scenario, 'Discover', true, true, 'TEST')
                    assert !accountUpdaterFile.validateLineOptions(scenario, 'TEST', true, true, subScenario)
                    assert !accountUpdaterFile.validateLineOptions(scenario, 'Discover', true, null, subScenario)
                    assert !accountUpdaterFile.validateLineOptions(scenario, 'Discover', null, true, subScenario)
                }
                for (subScenario in accountUpdaterFile.americanExpressSubScenarios){
                    assert accountUpdaterFile.validateLineOptions(scenario, 'American Express', false, false, subScenario)
                    assert !accountUpdaterFile.validateLineOptions(scenario, 'American Express', false, false, 'TEST')
                    assert !accountUpdaterFile.validateLineOptions(scenario, 'TEST', false, false, subScenario)
                    assert accountUpdaterFile.validateLineOptions(scenario, 'American Express', true, true, subScenario)
                    assert !accountUpdaterFile.validateLineOptions(scenario, 'American Express', true, true, 'TEST')
                    assert !accountUpdaterFile.validateLineOptions(scenario, 'TEST', false, true, subScenario)
                    assert !accountUpdaterFile.validateLineOptions(scenario, 'American Express', true, null, subScenario)
                    assert !accountUpdaterFile.validateLineOptions(scenario, 'American Express', null, true, subScenario)
                }
            }
            else {
                for (cardType in accountUpdaterFile.cardTypes){
                    if (scenario == 'Delete' && cardType == 'Mastercard'){
                        assert accountUpdaterFile.validateLineOptions(scenario, cardType, false, false, 'CONTAC')
                        assert accountUpdaterFile.validateLineOptions(scenario, cardType, false, false, 'UNKNWN')
                        assert !accountUpdaterFile.validateLineOptions(scenario, cardType, false, false, null)
                        assert accountUpdaterFile.validateLineOptions(scenario, cardType, true, true, 'CONTAC')
                        assert accountUpdaterFile.validateLineOptions(scenario, cardType, true, true, 'UNKNWN')
                        assert !accountUpdaterFile.validateLineOptions(scenario, cardType, true, true, null)
                        assert !accountUpdaterFile.validateLineOptions(scenario, cardType, null, true, 'CONTAC')
                        assert !accountUpdaterFile.validateLineOptions(scenario, cardType, true, null, 'UNKNWN')
                    }
                    else if (scenario == 'Delete'){
                        assert accountUpdaterFile.validateLineOptions(scenario, cardType, false, false, null)
                        assert accountUpdaterFile.validateLineOptions(scenario, cardType, true, true, null)
                        assert !accountUpdaterFile.validateLineOptions(scenario, cardType, null, false, null)
                        assert !accountUpdaterFile.validateLineOptions(scenario, cardType, true, null, null)
                    }
                    else if (scenario == 'Update') {
                        if (cardType == 'American Express'){
                            assert !accountUpdaterFile.validateLineOptions(scenario, cardType, null, false, null)
                            assert accountUpdaterFile.validateLineOptions(scenario, cardType, true, null, null)
                        }
                        else {
                            assert accountUpdaterFile.validateLineOptions(scenario, cardType, null, null, null)
                            assert !accountUpdaterFile.validateLineOptions(scenario, cardType, true, null, null)
                            assert accountUpdaterFile.validateLineOptions(scenario, cardType, false, null, null)
                        }
                    }
                    else { //Expiration scenario
                        assert accountUpdaterFile.validateLineOptions(scenario, cardType, false, false, null)
                        assert accountUpdaterFile.validateLineOptions(scenario, cardType, true, true, null)
                        assert !accountUpdaterFile.validateLineOptions(scenario, cardType, false, null, null)
                        assert accountUpdaterFile.validateLineOptions(scenario, cardType, null, true, null)
                    }

                }
                assert !accountUpdaterFile.validateLineOptions(scenario, 'TEST', false, false, null)
                assert !accountUpdaterFile.validateLineOptions(scenario, 'TEST', false, false, null)
            }

        }
        for (cardType in accountUpdaterFile.cardTypes){
            assert !accountUpdaterFile.validateLineOptions('TEST', cardType, false, false, null)
            assert !accountUpdaterFile.validateLineOptions('TEST', cardType, false, false, null)
        }


    }

    void checkLineData(Map lineData, AccountUpdaterFile accountUpdaterFile, String scenario){
        if (scenario == 'Update'){
            assert lineData.scenario == scenario
            assert lineData.oldToken != null
            assert lineData.newToken != null
            assert lineData.oldTokenId != null
            assert lineData.newTokenId != null
            assert lineData.newMask != null
            assert lineData.newExpiration != null
            assert lineData.oldExpiration != null
            assert accountUpdaterFile.lines.first().length() == 94
        }
        else if (scenario == 'Expiration'){
            assert lineData.scenario == scenario
            assert lineData.oldToken != null
            assert lineData.oldExpiration != null
            assert lineData.oldTokenId != null
            assert lineData.newTokenId == null
            assert lineData.newMask == null
            assert lineData.newExpiration != null
            assert lineData.newToken == null
            assert accountUpdaterFile.lines.first().length() == 94
        }
        else {//No Change and Delete data is the same
            assert lineData.scenario == scenario
            assert lineData.oldToken != null
            assert lineData.oldExpiration != null
            assert lineData.oldTokenId != null
            assert lineData.newTokenId == null
            assert lineData.newMask == null
            assert lineData.newExpiration == null
            assert lineData.newToken == null
            assert accountUpdaterFile.lines.first().length() == 94
        }

        assert accountUpdaterFile.getSubScenarioList('Visa') == accountUpdaterFile.visaSubScenarios
        assert accountUpdaterFile.getSubScenarioList('Rewards') == accountUpdaterFile.visaSubScenarios
        assert accountUpdaterFile.getSubScenarioList('Discover') == accountUpdaterFile.discoverSubScenarios
        assert accountUpdaterFile.getSubScenarioList('Mastercard') == accountUpdaterFile.mastercardSubScenarios
        assert accountUpdaterFile.getSubScenarioList('American Express') == accountUpdaterFile.americanExpressSubScenarios
        assert accountUpdaterFile.getSubScenarioList('TEST').size() == 0

        assert accountUpdaterFile.uploadFileToAssetServer() != ''
        assert accountUpdaterFile.deleteFileFromAssetServer()
    }



}


