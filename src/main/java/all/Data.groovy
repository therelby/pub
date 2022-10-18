package all

/**
 * List Based Data Structures Manipulation
 * @author akudin
 */

class Data {


	// Is String contains/equals at least one of values in List of strings
	// @param - strict = false -> contains
	//			strict = true  -> equals
	static isListInString(String text, List<String> strings, strict = false) {
		for (s in strings) {
			if (strict) {
				if (text == s) {
					return true
				}
			} else {
				if (text.contains(s)) {
					return true
				}
			}
		}
		return false
	}


	// Sort list of maps by a map key
	static sortListOfMaps(List listOfMaps, String key) {
		listOfMaps.sort { a,b ->
			a[key] <=> b[key]
		}
	}


	// Any complex or simple data comparing
	static isEqual(value1, value2) {
		return sort(value1) == sort(value2)
	}


	// All Lists sorting in any complex data
	// (!) recursion
	static sort(data, rev = false) {

		if (data instanceof List) {
			// sorting list
			if (!rev) {
				data.sort()
			} else {
				data.reverse()
			}
		} else if (data instanceof Map) {
			for (k in data.keySet()) {
				sort(data[k])
			}
		}

		return data
	}


}
