package all

import java.sql.ResultSet
import java.sql.ResultSetMetaData
import java.sql.Statement

/**
 * 		SQL Server Database Data Selecting Without Db/JDBC/SQL Stuff Handling
 * 		@author akudin
 */
class DbTools {


	/**
	 * Calls .runSet for a set with variable declarations
	 * @params - see .runSet
	 * @returns - .runSet result
	 */
	static runSetWithDeclarations(queries, Integer selectIndex = null, connectionName = 'wss', multipleResultSetSupport = false) {

		// Checking queries
		List<String> qs
		if (queries instanceof List) {
			qs = queries
		} else {
			qs = queries.split(';')*.trim()
			qs -= ''
		}
		if (!qs) { return null }

		// saving index
		def idxValue = qs[selectIndex]

		// looking for declarations
		def decs = [] // query or queries with declaration
		def vars = [] // declarated vars
		def uvrs = [] // other used vars
		qs.each {
			if (it ==~ '(?is).*declare @.*') {
				decs << it
				def v = it.split('[ ,.=()-+!#$%^&*/<>{}\']+').findAll { it.startsWith('@') }.unique()*.toUpperCase()
				if (!v) { throw new Exception("DatabaseSelectTools.runSetWithDeclarations: got bad declaration [$it]") }
				vars << [v[0]]
				uvrs << v - v[0]
			}
		}
		if (!decs) { throw new Exception("DatabaseSelectTools.runSetWithDeclarations: There are no declarations in the queries") }
		qs -= decs
		qs -= ''

		// repairing index
		qs.eachWithIndex { q, i ->
			if (q == idxValue) {
				selectIndex = i
			}
		}

		// grouping one level related declarations
		// (!) works for only one relation
		for (i in 1..decs.size()-1) {
			if (!decs[i]) { continue }
			def cur = decs[i].toUpperCase()
			for (ii in 0..i-1) {
				//println ">>>>>> $i/$ii --- ${uvrs[i]} --- ${vars[ii]}"
				if (uvrs[i].intersect(vars[ii])) {
					decs[ii] += '\n' + decs[i]
					vars[ii] += vars[i]
					vars[ii] = vars[ii].unique()
					uvrs[ii] += uvrs[i]
					uvrs[ii] = uvrs[ii].unique()
					decs[i] = ''
				}
			}
		}

		// adding declarations to each involved query in the set
		qs.eachWithIndex { q, i ->
			def cur = q.toUpperCase()
			def ds = ''
			decs.eachWithIndex { d, idx ->
				if (all.Data.isListInString(cur, vars[idx])) {
					ds += decs[idx] + '\n'
				}
			}
			if (ds) {
				qs[i] = ds + qs[i]
			}
			//println qs[i]
			//println '----------------------------------------------------------------------'
		}

		return runSet(qs, selectIndex, connectionName, multipleResultSetSupport)
	}


	/**
	 * Run bunch of SQL queries
	 * @param queries - list of queries or GString with queries separated by ;
	 * @param selectIndex - index of the resulting select query in the list
	 * @return .selectAll of the resulting select query
	 */
	static runSet(queries, Integer selectIndex = null, connectionName = 'wss', multipleResultSetSupport = false) {

		// Checking queries
		List<String> qs
		if (queries instanceof List) {
			qs = queries
		} else {
			qs = queries.split(';')*.trim()
			qs -= ''
		}
		if (!qs) { return null }

		// handling select queries
		List<Integer> selects = []
		for (int i = qs.size() - 1; i >= 0; i--) {
			/** Checks current index via regex
			 * (?is) = case insensitive and makes dots match new lines
			 * .* = match any number of characters
			 * 'select ' = match select followed by space
			 * .* = match any number of characters
			 * 'from ' = match from followed by space
			 * .* = match any number of characters
			 */
			if (qs[i] ==~ "(?is).*select .*from.*") {
				selects << i
			}
		}
		if (!selectIndex && selects) {
			selectIndex = selects[0]
		} else if (!selectIndex && !selects) {
			throw new Exception("DatabaseSelectTools.runSet: Can't find the resulting select query in the set")
		}

		// executing
		def res = null
		def sql = new Db(connectionName)
		qs.eachWithIndex { q, i ->
			//println q
			//println "---------------------------------------------"
			if (i != selectIndex) {
				if (selects.contains(i)) {
					sql.updateQuery(q, true)
				} else {
					sql.updateQuery(q)
				}
			} else {
				// the result point
				res = selectAll(q, connectionName, multipleResultSetSupport, sql)
			}
		}
		return res
	}


	/**
	 * Improved case of .selectToListOfMaps
	 * - the difference is all the columns that present in the select result going to be loaded automatically without fieldList provided
	 *
	 * (!) DEPRECATED
	 * Please use direct dbSelect() framework method
	 *
	 */
	@Deprecated
	static selectAll(String query, String connectionName = 'wss', Boolean multipleResultSetSupport = false, connection = null) {
		return selectToListOfMaps(query, null, connectionName, multipleResultSetSupport, connection)
	}


	/** Select multiple SELECT results to List of List of Maps */
	static List<List<Map>> selectMultiple(String query, String connectionName = 'wss') {
		Db sql = new Db(connectionName)
		Statement stmt = sql.selectQueryMultipleResults(query)
		if (!stmt) {
			return null
		}
		List finalResult = []
		while (true) {
			ResultSet rs = stmt.getResultSet()

			ResultSetMetaData metadata = rs.getMetaData()
			List fieldList = []
			for (int i in (1..metadata.getColumnCount())) {
				fieldList << metadata.getColumnName(i)
			}

			List result = []
			while (rs.next()) {
				result << [:]
				fieldList.each {
					result.last()[it] = getFieldValue(it, rs, metadata)
				}
			}
			finalResult << result

			if (!stmt.getMoreResults()) {
				break
			}
		}
		return finalResult
	}


	/**
	 * Selects records by SQL query and put it to list of maps
	 * @param query - SELECT SQL query
	 * @param fieldsList like ['id', 'item_number'] that must be selecting in the query; can be null for all result columns getting
	 * @param connectionName for all.Db() class
	 * @return List of Maps like [['id': 1, 'item_number': 'SWF123RT'], ...]
	 *
	 * (!) DEPRECATED
	 * Please use direct dbSelect() framework method
	 *
	 */
	@Deprecated
	static selectToListOfMaps(String query, List<String> fieldList, String connectionName = 'wss',
							  Boolean multipleResultSetSupport = false, connection = null) {

		def sql
		if (!connection) {
			sql = new Db(connectionName)
		} else {
			sql = connection
		}

		// getting result set
		def res
		if (query.toUpperCase().startsWith('EXEC')) {
			res = sql.storedProcedure(query)
		} else {
			if (!multipleResultSetSupport) {
				res = sql.selectQuery(query)
			} else {
				res = sql.selectMultiple(query)
			}
		}

		// no results
		if (!res) {
			return []
		}

		// handling results
		def metadata = res.getMetaData()

		// creating field list
		if (fieldList == null) {
			fieldList = []
			for (int i in (1..metadata.getColumnCount())) {
				fieldList << metadata.getColumnName(i)
			}
		}

		// getting data
		def result = []
		while (res.next()) {
			result << [:]
			fieldList.each {
				result.last()[it] = getFieldValue(it, res, metadata)
			}
		}
		return result
	}



	/**
	 * Read field value
	 * @param fieldName
	 * @param selectResult
	 * @param metadata
	 * @return
	 */
	def static getFieldValue(fieldName, selectResult, metadata) {
		def result
		// looking for needed field name
		for (int i in (1..metadata.getColumnCount())) {
			// checking field name
			if (metadata.getColumnName(i) != fieldName) {
				continue // go to next
			}
			// picking field value
			switch (metadata.getColumnType(i)) {
				case -9:
				case -1:
				case 1:
				case 12:
					result = selectResult.getString(fieldName)
					break

				case 2:
				case 3:
				case 6:
				case 7:
				case 8:
					result = selectResult.getBigDecimal(fieldName)
					break

				case 4:
				case 5:
				case -5:
				case -6:
					result = selectResult.getInt(fieldName)
					break
				case -7:
					result = selectResult.getBoolean(fieldName)
					break
				case 91: // date
					result = selectResult.getDate(fieldName)
					break
				case 92: // time
					result = selectResult.getTime(fieldName)
					break
				case 93: // timestamp
					result = selectResult.getTimestamp(fieldName)
					break
				case 0:
				default:
					result = null
					break
			}
		}
		return result
	}
	/*
	 *
	 -7	BIT
	 -6	TINYINT
	 -5	BIGINT
	 -4	LONGVARBINARY
	 -3	VARBINARY
	 -2	BINARY
	 -1	LONGVARCHAR
	 0	NULL
	 1	CHAR
	 2	NUMERIC
	 3	DECIMAL
	 4	INTEGER
	 5	SMALLINT
	 6	FLOAT
	 7	REAL
	 8	DOUBLE
	 12	VARCHAR
	 91	DATE
	 92	TIME
	 93	TIMESTAMP
	 1111 	OTHER
	 */



	/**
	 * (!) Old stuff (!) Do not use, please
	 * Gets specified columns values to list
	 * @param query should have TOP 1 option
	 * @param fields list of columns
	 * @return list of values
	 */
	def static selectFields(String query, ArrayList fields, Boolean qaDatabase = false) {
		def sql = new Db(qaDatabase)
		def sqlResult = sql.selectQuery(query)
		def metadata = sqlResult.getMetaData()
		def result = []
		if (sqlResult != null) {
			// go to first record
			while (sqlResult.next()) {
				// run thru all needed columns
				fields.each {
					// run thru all columns in result
					for (int i in (1..metadata.getColumnCount())) {
						// needed column?
						//println metadata.getColumnName(i)
						if (metadata.getColumnName(i) != it) {
							continue // no
						}
						// yes
						//println metadata.getColumnType(i)
						switch (metadata.getColumnType(i)) {
							case -6:
								result << sqlResult.getInt(it)
								break
							case -9:
							case 1:
							case 12:
								result << sqlResult.getString(it).trim()
								break

							case 3:
							case -7:
								result << sqlResult.getBoolean(it)
								break
							case 6:
							case 8:
								result << sqlResult.getBigDecimal(it)
								break
						}
						break
					}
				}
			}
		} else {
			throw new Exception("No data selected\n$query")
		}
		return result
	}
}
