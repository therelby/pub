package all

/**
 * 		Files Handling
 * 		@author akudin
 */
class Files {
	
	
	// Get project folder
	synchronized static getProjectFolder() {
		return System.getProperty("user.dir").toString() + '/'
	}
	
	
	// Write text to a file
	synchronized static writeText(String text, String filePathAndName, useProjectFolder = true) {
		
		if (useProjectFolder) {
			filePathAndName = getProjectFolder() + filePathAndName
		}
		
		run().log("Saving text to: $filePathAndName ...")
		try {
			def newFile = new File(filePathAndName)
			newFile.write(text)
			return true
		} catch(e) {
			println e
			return false
		}
	}
	
	
}
