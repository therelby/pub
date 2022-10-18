package above.allrun.helpers

import above.Run

/**
 *      Database INSERT/UPDATE Query Builder
 */

class RunDbQueryBuilder {

    Run r = run()


    /** Data to DB Query String Converter */
    String dataToQueryString(Object data) {
        if (data instanceof String) {
            if (!data.startsWith('='))
                return (String) "'${r.dbString(data)}'"
            else
                return (String) data.replaceFirst('=', '')
        } else if (data instanceof Date) {
            return (String) "'${data.format('yyyy-MM-dd')}'"
        } else {
            return data.toString()
        }
    }


    /** INSERT Query Builder */
    String insertQuery(String tableName, Map insertData) {

        String fields = ''
        for (field in insertData.keySet()) {
            if (fields != '') fields += ', '
            fields += field
        }

        String values = ''
        for (field in insertData.keySet()) {
            if (values != '') values += ', '
            values += dataToQueryString(insertData[field])
        }

        return "INSERT INTO $tableName ($fields) VALUES ($values);"
    }


    /** UPDATE Query Builder */
    String updateQuery(String tableName, String whereExpression, Map updateData) {

        if (!whereExpression)
            throw new Exception('whereExpression can not be empty for the table data safety reasons')

        String values = ''
        for (field in updateData.keySet()) {
            if (values != '') values += ', '
            values += "$field = ${dataToQueryString(updateData[field])}"
        }

        return "UPDATE $tableName SET $values WHERE $whereExpression;"
    }

}
