package framework.all

import above.RunWeb
import all.Db
import all.DbTools
import grails.async.Promises
import above.allrun.helpers.RunReferenceStorage

class UtDbMultithreading extends RunWeb {

    /**
     * Set to true to keep the table after the test is over
     */
    def keepTable = true

    // Test
    def test() {

        setup('kyilmaz', 'UtDbMultithreading',
                ['product:wss', 'tfsProject:Automation Projects',
                 'keywords:unit test', "PBI:0", 'logLevel:info'])
        RunReferenceStorage.add(run())
        testTitle
        for (i in 0..10) {
            testEmptyTable()
            testExistingTable()
        }
        if (!keepTable) {
            dropTable()
        }
    }

    /**
     * Clears table at start to guarantee an insert and not an update
     * @return
     */
    def testEmptyTable() {
        dropTable()
        createTable()
        def data = [
                [id:1, any_string:'test1', any_bool:true],
                [id:2, any_string:'test2', any_bool:false],
                [id:3, any_string:'test3', any_bool:true],
        ]
        testConcurrentUpdate(data)
    }

    /**
     * Just different from testEmpty to see a change in values after update
     * @return
     */
    def testExistingTable() {
        def updatedData = [
                [id:1, any_string:'update1', any_bool:false],
                [id:2, any_string:'update2', any_bool:true],
                [id:3, any_string:'update3', any_bool:false],
        ]
        testConcurrentUpdate(updatedData)
    }

    def testConcurrentUpdate(data) {
        // Asynchronous updates
        def listPromise = []
        data.each { map ->
            log map
            def promise = Promises.task {
                upsert(map['id'], map['any_string'], map['any_bool'])
            }
            promise.onComplete {
                log ("Completed update for data: $map")
            }
            listPromise << promise
        }
        Promises.waitAll(listPromise)
        // Synchronous select and checking
        sleep(1000)
        def dbData = select()
        assert data == dbData
        log ("Data matches DB!", console_color_green)
    }

    def select() {
        return DbTools.selectAll("SELECT * FROM unit_test.$testTitle", 'qa')
    }

    def upsert(int id, String anyString, Boolean anyBool) {
        def query = """\
                BEGIN TRY
                 INSERT INTO unit_test.${testTitle}
                VALUES
                    ('$id', '$anyString', '$anyBool')
                 
                END TRY
                 
                BEGIN CATCH

                    UPDATE unit_test.${testTitle}
                       SET any_string = '$anyString', any_bool = '$anyBool'
                     WHERE id = '$id';
                 
                END CATCH
                """
        Db db = new Db('qa')
        db.updateQuery(query)
    }

    def clearTable() {
        def query = """\
            DELETE FROM unit_test.${testTitle} WHERE id IS NOT NULL;
            """
        Db db = new Db('qa')
        db.updateQuery(query)
        sleep(2000)
    }

    def createTable() {
        def query = """\
                CREATE TABLE unit_test.${testTitle}
                (
                    [id] int NOT NULL PRIMARY KEY, -- Primary Key column
                    [any_string] nvarchar(max),
                    [any_bool] bit
                );
            """
        Db db = new Db('qa')
        db.updateQuery(query)
        sleep(2000)
    }

    def dropTable() {
        def query = "DROP TABLE IF EXISTS unit_test.${testTitle};"
        Db db = new Db('qa')
        db.updateQuery(query)
    }
}
