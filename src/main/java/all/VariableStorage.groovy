package all

import above.ConfigReader

/**
 * 		Global Variable Storage
 * 		-- (!) names must start with your login, for example for akudin: akudin-SomeName
 * 		-- only login owner can create new names that starts with certain login
 * 		-- anybody can read and change (!) any variables
 * 		-- using login is not about permissions that's about getting unique names without conflicts between team folks
 *
 * 		@author akudin
 */
class VariableStorage {

	// Current host name
	final static private host = InetAddress.getLocalHost().toString().toLowerCase().replace('-lpt', '').split('/')[0]

	// Queries
	static private String querySelect = "SELECT value FROM Variable_Storage WHERE name ='%name'"
	static private String queryInsert = "INSERT INTO Variable_Storage (name, value, stable) VALUES ('%name', '%value', %stable)"
	static private String queryUpdate = "UPDATE Variable_Storage SET value='%value', stable=%stable WHERE name='%name'"
	static private String queryDelete = "DELETE FROM Variable_Storage WHERE name = '%name'"
	static private String queryClean = "DELETE FROM Variable_Storage WHERE stable=0"


	/** Get JSON text value as data */
	synchronized static getData(String name) {
		return Json.getData(get(name))
	}


	/** Set data value as JSON text */
	synchronized static setData(String name, value, Boolean permanent = false) {
		set(name, Json.getJson(value), permanent)
	}


	/** Get value */
	synchronized static get(String name) {
		checkName(name)
		def res = DbTools.selectToListOfMaps(querySelect.replace('%name', name), ['value'], 'qa')
		if (res) {
			return res[0].value
		} else {
			return null
		}
	}


	/**
	 * Set value
	 * (!) provide permanent param = true to avoid stored data deletion on the dev db refresh
	 */
	synchronized static set(String name, value, Boolean permanent = false) {

		if (value == null) { value = '' }

		String val = value.toString()
		if (val.length() > 50) { val = val[0..49] + '...' }
		run().log "VariableStorage SET: $name = ${val}"

		if (checkName(name)) {
			def res = DbTools.selectToListOfMaps(querySelect.replace('%name', name), ['value'], 'qa')
			def sql = new Db('qa')
			if (res) { 
				// updating existing variable
				sql.updateQuery(queryUpdate.replace('%name', name).replace('%value', value.toString().replace("'", "''"))
					.replace('%stable', permanent.toString().replace('false', '0').replace('true', '1')))
			} else { 
				// create new variable
				if (!name.toLowerCase().startsWith(host) && run().testAuthor != ConfigReader.get('frameworkDebugPerson')) {
					run().log("VariableStorage: You cannot create variable named [$name], please use $host-${name}", run().console_color_red)
					return false
				}
				sql.updateQuery(queryInsert.replace('%name', name).replace('%value', value.toString().replace("'", "''"))
					.replace('%stable', permanent.toString().replace('false', '0').replace('true', '1')))
			}
			return true
		} else {
			return false
		}
	}


	/** Delete name */
	synchronized static delete(String name) {
		if (checkName(name)) {
			new Db('qa').updateQuery(queryDelete.replace('%name', name))
			return true
		} else {
			return false
		}
	}


	/** Check name */
	synchronized private static checkName(String name) {
		if (!name) {
			run().log("VariableStorage: Wrong name [$name]")
			return false
		}
		return true
	}
	
	
	/**
	 * Cleaning
	 * Usually uses after the dev db refresh
	 * (!) set 'YOUR_LOGIN-clean' variable to 'ok' before cleaning
 	 */
	synchronized static clean() {
		if (get("$host-clean") == 'ok') {
			new Db('qa').updateQuery(queryClean)
			delete("$host-clean")
		} else {
			throw new Exception("Irresponsible VariableStorage cleaning has canceled")
		}
	}

}
