package above.allrun

import above.allrun.helpers.RunDbQueryBuilder
import all.Json
import com.microsoft.sqlserver.jdbc.SQLServerDriver
import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement

/**
 *      DB Connection Low-level (JDBC) stuff
 */
abstract class DbRunDbConnection extends ZaRunStop {

    synchronized final static ThreadLocal<Map> sqlConnections = new ThreadLocal<Map>()
    final databaseConfig = Json.getData(new File(projectPath + 'configdatabase.json').text)
    Boolean dbEnvironmentDepending = false
    String dbDefaultConnectionName = '' // one of configdatabase.json 'connection' names


    /**
     * Set DB name
     * @param connectionName -- from /configdatabase.json
     */
    void dbUsing(String connectionName) {
        xCheckSetup()
        logDebug("Setting dbUsing: $connectionName")
        dbDefaultConnectionName = connectionName
    }


    /** Execute one or several of any kind of queries */
    boolean dbExecute(String sqlQuery, String connectionName = dbDefaultConnectionName) {
        Connection conn = dbConnect(connectionName)
        if (!conn) return null
        PreparedStatement stmt = conn.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)
        dbConsole('dbExecute:', sqlQuery)
        try {
            stmt.execute()
            return true
        } catch (e) {
            dbException(e)
            return false
        }
    }


    /** Execute UPDATE query */
    boolean dbUpdate(String updateQuery, String connectionName = dbDefaultConnectionName) {
        return dbExecute(updateQuery, connectionName)
    }


    /** Execute built UPDATE query based on provided data */
    boolean dbUpdateData(String tableName, String whereExpression, Map updateData, String connectionName = dbDefaultConnectionName) {
        return dbExecute(new RunDbQueryBuilder().updateQuery(tableName, whereExpression, updateData), connectionName)
    }


    /** UPDATE returning affected rows number */
    int dbUpdateAffected(String updateQuery, String connectionName = dbDefaultConnectionName) {
        Connection conn = dbConnect(connectionName)
        if (!conn) return null
        PreparedStatement stmt = conn.prepareStatement(updateQuery, Statement.RETURN_GENERATED_KEYS)
        dbConsole('dbUpdateAffected:', updateQuery)
        try {
            stmt.executeUpdate()
            return stmt.getUpdateCount()
        } catch (e) {
            dbException(e)
            return 0
        }
    }


    /** Execute built UPDATE query based on provided data returning affected rows number */
    int dbUpdateDataAffected(String tableName, String whereExpression, Map updateData, String connectionName = dbDefaultConnectionName) {
        return dbUpdateAffected(new RunDbQueryBuilder().updateQuery(tableName, whereExpression, updateData), connectionName)
    }


    /** Execute INSERT query */
    Integer dbInsert(String insertQuery,
                     String connectionName = dbDefaultConnectionName,
                     boolean hideAndIgnoreException = false) {
        Connection conn = dbConnect(connectionName)
        if (!conn) return null
        PreparedStatement stmt = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)
        dbConsole('dbInsert:', insertQuery)
        try {
            int affectedRows = stmt.executeUpdate()
            if (affectedRows == 0) return 0
            try {
                ResultSet rs = stmt.getGeneratedKeys()
                if (rs.next())
                    return rs.getInt(1)
                else
                    return 0
            } catch (ignored) {
                return 0
            }
        } catch (e) {
            if (!hideAndIgnoreException) dbException(e)
            return 0
        }
    }


    /** Execute INSERT query ignoring ALL exceptions */
    int dbInsertIgnore(String insertQuery, String connectionName = dbDefaultConnectionName) {
        return dbInsert(insertQuery, connectionName, true)
    }


    /** Execute built INSERT query based on provided data */
    int dbInsertData(String tableName, Map insertData, String connectionName = dbDefaultConnectionName) {
        return dbInsert(new RunDbQueryBuilder().insertQuery(tableName, insertData), connectionName)
    }

    /** Execute built INSERT query based on provided data ignoring ALL exceptions */
    int dbInsertDataIgnore(String tableName, Map insertData, String connectionName = dbDefaultConnectionName) {
        return dbInsert(new RunDbQueryBuilder().insertQuery(tableName, insertData), connectionName, true)
    }


    /** Execute SELECT query */
    ResultSet dbLowSelect(String selectQuery, String connectionName = dbDefaultConnectionName) {
        Connection conn = dbConnect(connectionName)
        if (!conn) return null
        Statement stmt = conn.createStatement()
        dbConsole('dbLowSelect:', selectQuery)
        try {
            ResultSet result = stmt.executeQuery(selectQuery)
            return result
        } catch (e) {
            dbException(e)
            return null
        }
    }


    /** Execute SELECT query returning several results */
    Statement dbLowSelectMultiple(String selectQuery, String connectionName = dbDefaultConnectionName) {
        Connection conn = dbConnect(connectionName)
        if (!conn) return null
        Statement stmt = conn.createStatement()
        dbConsole('dbLowSelectMultiple:', selectQuery)
        boolean result
        try {
            result = stmt.execute(selectQuery)
            if (result)
                return stmt
            else
                return null
        } catch (e) {
            dbException(e)
            return null
        }
    }


    /** Execute set of queries but one SELECT with result in that */
    ResultSet dbLowSelectMultipleOneResult(String selectQuery, String connectionName = dbDefaultConnectionName) {
        Connection conn = dbConnect(connectionName)
        if (!conn) return null
        Statement stmt = conn.createStatement()
        dbConsole('dbLowSelectMultipleOneResult:', selectQuery)
        boolean results
        try {
            results = stmt.execute(selectQuery)
            if (!results) results = stmt.getMoreResults()
            if (results)
                return stmt.getResultSet()
            else
                return null
        } catch (e) {
            dbException(e)
            return null
        }
    }


    /** Execute stored procedure */
    ResultSet dbLowStoredProcedure(String query, String connectionName = dbDefaultConnectionName) {
        if (!query.trim().toUpperCase().startsWith('EXEC')) {
            throw new Exception("dbStoredProcedure: Query must starts with 'EXEC'")
        }
        Connection conn = dbConnect(connectionName)
        if (!conn) return null
        PreparedStatement ps = conn.prepareStatement("SET NOCOUNT ON; $query")
        ps.setEscapeProcessing(true)
        ps.setQueryTimeout(60)
        try {
            return ps.executeQuery()
        } catch (e) {
            dbException(e)
            return null
        }
    }


    /** DB queries execution methods console out */
    private void dbConsole(String title, String query) {
        logDebug title
        logDebug query
        if (logLevel == 'info' && (!xDbHideInfoLogging() || xDbInfoLoggingForce())) {
            String msg = query.replace('\n', ' ').trim()
            while (msg.contains('  ')) { msg = msg.replace('  ', ' ') }
            if (msg.length() > logLineLength * 2) {
                msg = msg[0..logLineLength * 2 - 1]
            }
            log msg
        }
    }


    /** Connection */
    Connection dbConnect(String connectionName = dbDefaultConnectionName) {
        xCheckSetup()
        if (!connectionName)
            throw new Exception('dbConnect: connectionName must be provided for a db method or previously set by dbUsing()')

        // WSS environments depending override
        if (dbEnvironmentDepending && xTestConcept() == 'web' && webProject.testEnv == 'test' && (connectionName == 'wss' || connectionName == 'wss-ro')) {
            connectionName = 'wss-ro-test'
            log "Db: connectionName changed to [$connectionName] based on dbEnvironmentDepending = true"
        }

        // getting connections list from storage or creating a new empty list
        Map<String, Connection> connections = sqlConnections.get()
        if (!connections) connections = [:]

        // reusing saved connection
        if (connections.containsKey(connectionName)) {
            if (connections[connectionName] && !connections[connectionName].closed) {
                logDebug "dbConnect: Re-using saved connection: $connectionName"
                return (Connection) connections[connectionName]
            } else if (connections[connectionName]) {
                // removing existing but closed connection
                connections[connectionName] = null
                connections.remove(connectionName)
            }
        }

        // creating new connection
        String connectionString
        if (databaseConfig.containsKey(connectionName))
            connectionString = databaseConfig[connectionName].connection
        else
            throw new Exception("dbConnect: unknown DB connection name '$connectionName'")

        log "DB: Connecting to: $connectionString"
        try {
            DriverManager.registerDriver(new SQLServerDriver())
            DriverManager.setLoginTimeout(10)
            connections[connectionName] = DriverManager.getConnection(connectionString)
            sqlConnections.set(connections)
            return (Connection)connections[connectionName]
        } catch (e) {
            dbException(e)
            return null
        }
    }


    /** Closing connection */
    void dbClose(String connectionName = dbDefaultConnectionName) {
        Map<String, Connection> connections = sqlConnections.get()
        if (connections[connectionName] && !connections[connectionName].closed) {
            try { connections[connectionName].close() } catch (ignored) {}
            connections[connectionName] = null
            connections.remove(connectionName)
        }
        sqlConnections.set(connections)
    }
    /** Old stuff compatibility */
    void killDbConnection(String connectionName) {
        dbClose(connectionName)
    }


    /** Closing all connections */
    void dbCloseAll() {
        Map<String, Connection> connections = sqlConnections.get()
        if (!connections) { return }
        connections.keySet().each {
            if (!connections[it].closed) {
                try { connections[it].close() } catch (ignored) {}
            }
            connections[it] = null
        }
        sqlConnections.set(null)
    }


    /** Handling DB exceptions */
    private void dbException(Exception e) {
        if (!runDebug)
            addIssueTrackerEvent("dbException: ${e.getMessage()}", e)
        else
            throw new Exception(e)
    }

}
