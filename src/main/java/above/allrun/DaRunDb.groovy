package above.allrun

import java.sql.ResultSet
import java.sql.ResultSetMetaData
import java.sql.Statement

/**
 *      SQL Server Database High-level Handling
 */
abstract class DaRunDb extends DbRunDbConnection {

    private boolean dbHideInfoLogging = false
    private boolean dbHideInfoLoggingOverride = false

    /** Console output */
    void dbInfoLoggingHide() {
        dbHideInfoLogging = true
    }
    void dbInfoLoggingNormal () {
        dbHideInfoLogging = false
    }
    boolean xDbHideInfoLogging() {
        return dbHideInfoLogging
    }
    void dbInfoLoggingForceOn() {
        dbHideInfoLoggingOverride = true
    }
    void dbInfoLoggingForceOff () {
        dbHideInfoLoggingOverride = false
    }
    boolean xDbInfoLoggingForce() {
        return dbHideInfoLoggingOverride
    }


    /** Select to List of Maps */
    List<Map> dbSelect(String selectQuery, String connectionName = dbDefaultConnectionName, boolean nullOnFail = false) {
        ResultSet res = dbLowSelect(selectQuery, connectionName)
        if (nullOnFail && res == null) return null
        if (!res) return []
        return dbResultSetToListOfMaps(res)
    }

    /** dbSelect that returning null on any exception */
    List<Map> dbSelectSafe(String selectQuery, String connectionName = dbDefaultConnectionName) {
        return dbSelect(selectQuery, connectionName, true)
    }

    /** Select multiple SELECT results to List of Lists of Maps */
    List<List<Map>> dbSelectMultiple(String query, String connectionName = dbDefaultConnectionName) {
        Statement stmt = dbLowSelectMultiple(query, connectionName)
        if (!stmt) {
            return []
        }
        List finalResult = []
        while (true) {
            ResultSet rs = stmt.getResultSet()
            finalResult << dbResultSetToListOfMaps(rs)
            if (!stmt.getMoreResults()) {
                break
            }
        }
        return finalResult
    }


    /** Result Set to List of Maps Convertor */
    List<Map> dbResultSetToListOfMaps(ResultSet rs) {
        ResultSetMetaData md = rs.getMetaData()
        int columns = md.getColumnCount()
        List list = []
        while (rs.next()) {
            Map row = [:]
            for (int i = 1; i <= columns; i++) {
                row.put(md.getColumnName(i), rs.getObject(i))
            }
            list << row
        }
        return list
    }


    /** DB INSERT/UPDATE String Preparation */
    String dbString(String text) {
        return text.replace("'", "''")
    }
}
