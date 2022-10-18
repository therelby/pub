package all

import groovy.io.FileType

/**
 * 		Download folder handling
 * 		- folder path
 * 		- file existing
 * 		- folder cleaning for defined file name
 * 		@author akudin
 */
 class DownloadFolder {

	// Download folder for current user
	static folderPath = System.getProperty("user.home") + "\\Downloads\\"


	// Wait for file downloading
	// - breaks when file become exists
	// - doesn't guarantee file is downloaded at final
	synchronized static waitForFileDownloading(String fileName, int seconds) {
		def file = new File(folderPath + fileName)
		seconds *= 10
		while (seconds > 0) {
			if (file.exists()) {
				return
			}
			Thread.sleep(100)
			seconds--
		}
	}


	// Check file existence
	 synchronized static isFileExists(String name) {
		return (new File(folderPath + name)).exists()
	}


	// Delete typical files from download folder
	// for example: for file.txt will be deleted file.txt, file (1).txt, file (2).txt ...
	// (!) Use it before file download to get the download folder clear
	 synchronized static deleteFiles(String name, String ext = '') {
		if (ext == '' && name.contains('.')) {
			def fl = name.split('\\.')
			name = fl[0]
			ext = '.' + fl[1]
		}
		def dir = new File(folderPath)
		dir.eachFileRecurse (FileType.FILES) { file ->
			if ((ext == '' && file.getName().startsWith(name)) ||
			(ext != '' && file.getName().startsWith(name) && file.getName().endsWith(ext))) {
				file.delete()
				if (file.exists()) {
					Thread.sleep(1500)
					file.delete()
				}
				assert !file.exists() // catch not deleted file
			}
		}
	}

}
