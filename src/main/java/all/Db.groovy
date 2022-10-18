package all

import above.Run
import java.sql.Connection
import java.sql.DriverManager
import java.sql.Statement
import java.sql.ResultSet
import com.microsoft.sqlserver.jdbc.SQLServerDriver

/**
 *      Database Connection and Low-level Operations
 */
class Db {

    private Run r
    private dbNickName
    private connectionString
    private Connection sql = null


    /** Class Constructor */
    Db(String connectionName) {
        r = run()
        // WSS environments depending override
        if (r.dbEnvironmentDepending && r.concept == 'web' && r.webProject.testEnv == 'test' && (connectionName == 'wss' || connectionName == 'wss-ro')) {
            connectionName = 'wss-ro-test'
            r.log "Db: connectionName changed to [$connectionName] based on dbEnvironmentDepending = true"
        }
        // selecting connecting string
        if (r.databaseConfig.containsKey(connectionName)) {
            this.dbNickName = connectionName
            this.connectionString = r.databaseConfig[connectionName].connection
        } else {
            def tempDatabaseConfig = Json.getData(new File(r.projectPath + 'configdatabase.json').text)
            if (tempDatabaseConfig.containsKey(connectionName)) {
                r.addIssueTrackerEvent("Could not access r.databaseConfig in Db.groovy. Found config on re-read")
                this.dbNickName = connectionName
                this.connectionString = tempDatabaseConfig[connectionName].connection
            } else {
                throw new Exception("(!) Unknown db name: $connectionName")
            }
        }
    }


    /** Establishing database connection */
    void connect() {

        closeConnection()

        Map connections = r.sqlConnections.get()
        if (!connections) {
            connections = [:]
        }
        if (dbNickName && connections.containsKey(dbNickName)) {
            r.logDebug "DB: Re-using saved: $connectionString"
            sql = connections[dbNickName]
            if (!sql || sql.closed) {
                r.log 'DB: Saved connection is closed'
                sql = null
            }
        }

        if (!sql) {
            r.log "DB: Connecting to: $connectionString"
            try {
                DriverManager.registerDriver(new SQLServerDriver())
                sql = DriverManager.getConnection(connectionString)
                if (dbNickName) {
                    connections[dbNickName] = sql
                    r.sqlConnections.set(connections)
                }
            } catch (e) {
                println e
                sql = null
            }
        }
    }


    /** Auto connecting to database */
    void checkConnection() {
        if (!sql || sql.isClosed()) {
            connect()
        }
    }


    /** Multiple queries but one SELECT with result set in a query handling */
    def selectMultiple(String selectQuery) {
        checkConnection()
        Statement sttmnt = sql.createStatement()
        r.logDebug 'DB select query:'
        r.logDebug selectQuery
        def results = sttmnt.execute(selectQuery)
        if (!results) {
            results = sttmnt.getMoreResults()
        }
        if (results) {
            return sttmnt.getResultSet()
        }
    }


    /** Executing SELECT queries */
    ResultSet selectQuery(String selectQuery) {
        checkConnection()
        Statement stmt = sql.createStatement()
        r.logDebug 'DB select query:'
        r.logDebug selectQuery
        if (r.logLevel == 'info') {
            String msg = selectQuery.replace('\n', ' ').trim()
            while (msg.contains('  ')) { msg = msg.replace('  ', ' ') }
            if (msg.length() > r.logLineLength * 2) {
                msg = msg[0..r.logLineLength * 2 - 1]
            }
            r.log msg
        }
        ResultSet result = stmt.executeQuery(selectQuery)
        return result
    }

    /** Executing SELECT query returning several results */
    Statement selectQueryMultipleResults(String selectQuery) {
        checkConnection()
        Statement stmt = sql.createStatement()
        r.logDebug 'DB select query:'
        r.logDebug selectQuery
        if (r.logLevel == 'info') {
            String msg = selectQuery.replace('\n', ' ').trim()
            while (msg.contains('  ')) { msg = msg.replace('  ', ' ') }
            if (msg.length() > r.logLineLength * 2) {
                msg = msg[0..r.logLineLength * 2 - 1]
            }
            r.log msg
        }
        boolean result = stmt.execute(selectQuery)
        if (result) {
            return stmt
        } else {
            return null
        }
    }


    /** Executing INSERT, UPDATE, and DELETE queries */
    def updateQuery(String sqlQuery, ignoreKeys = false) {

        checkConnection()

        Statement sttmnt = sql.createStatement()
        r.logDebug 'DB executing query:'
        r.logDebug sqlQuery
        /*
        if (sqlQuery.length() < 1000) {
            r.logDebug sqlQuery
        } else {
            r.logDebug "${sqlQuery[0..999]} ..."
        }*/
        if (r.logLevel == 'info') {
            String msg = sqlQuery.replace('\n', ' ').trim()
            while (msg.contains('  ')) { msg = msg.replace('  ', ' ') }
            if (msg.length() > r.logLineLength * 2) {
                msg = msg[0..r.logLineLength * 2 - 1]
            }
            r.log msg
        }

        int tries = 3
        while (tries > 0) {
            tries--
            try {
                sttmnt.executeUpdate(sqlQuery, Statement.RETURN_GENERATED_KEYS)
                break
            } catch (com.microsoft.sqlserver.jdbc.SQLServerException e) {
                String msg = e.getMessage()
                if (!msg.contains('Transaction (Process ID') || !msg.contains('was deadlocked on lock | communication buffer resources with another process')) {
                    throw e
                } else {
                    r.log "Db: Got $msg"
                    r.log "Tries left: $tries..."
                    sleep(Numbers.random(1, 3) * 1000)
                }
            }
        }

        if (sqlQuery.contains('INSERT INTO ') && !ignoreKeys) {
            try {
                ResultSet rs = sttmnt.getGeneratedKeys()
                if (rs.next()) {
                    return rs.getInt(1)
                }
            } catch (ignored) {}
        }
        return 0
    }


    /** Execute stored procedure */
    def storedProcedure(String query) {
        if (!query.toUpperCase().startsWith('EXEC')) {
            throw new Exception("Query must start with 'EXEC'")
        }
        checkConnection()
        def ps = sql.prepareStatement("SET NOCOUNT ON; $query")
        ps.setEscapeProcessing(true)
        ps.setQueryTimeout(60)
        try {
            return ps.executeQuery()
        } catch (ignored) {
            return null
        }
    }


    /** Closing connection */
    def closeConnection() {
        if (sql) {
            sql.close()
        }
        sql = null
    }

}
