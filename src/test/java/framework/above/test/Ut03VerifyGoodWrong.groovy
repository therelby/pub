package framework.above.test

import above.ConfigReader
import above.Run
import above.types.IssueCategory

class Ut03VerifyGoodWrong extends Run {

    static void main(String[] args) {
        new Ut03VerifyGoodWrong().testExecute([
                runType: 'Regular'
        ])
    }

    void test() {

        setup([
                author: ConfigReader.get('frameworkDebugPerson'),
                title: 'UNIT TEST verify() Wrong 1',
                tfsProject: 'Webstaurant.StoreFront',
                keywords: 'framework unit test',
                PBI: 1, //logLevel: 'debug'
        ])

        log runRecordId


        // all wrong

        try {
            verify(true, [:])
            assert false
        } catch(e) {
            log e.getMessage()
        }


        try {
            verify(true, [title: ''])
            assert false
        } catch(e) {
            log e.getMessage()
        }


        try {
            verify(true, [title: 'Title'])
            assert false
        } catch(e) {
            log e.getMessage()
        }


        try {
            verify(true, [title: 'Title', details: [:]])
            assert false
        } catch(e) {
            log e.getMessage()
        }


        try {
            verify(true, [id: UUID.randomUUID().toString(), title: 'Title', details: [:]])
            assert false
        } catch(e) {
            log e.getMessage()
        }


        // all good

        try {
            verify(true, [id: UUID.randomUUID().toString(), title: 'Important Verify', details: [ one: 1 ]])
        } catch(ignore) {
            assert false
        }


        try {
            verify(true, [id: UUID.randomUUID().toString(), title: '"Check" Verify w Good Blocking', details: [ one: 1 ]], IssueCategory.PRODUCT_OTHER)
        } catch(ignore) {
            assert false
        }


        // wrong blocking issues provided

        try {
            verify(true, [id: UUID.randomUUID().toString(), title: '"Check" Verify w Wrong Blocking', details: [ one: 1 ]], IssueCategory.NOT_PROVIDED)
            assert false
        } catch(e) {
            log e.getMessage()
        }
    }

}
