package all.util
/**
 *     Utility methods for text files
 *      Refactored from Katalon common.TextFile
 *     akudin vdiachuk
 *
 */
class TextFileUtil {
    def fileName = ''
    // File strings list
    def data = []

    // Class Constructor
    // reads file to data
    TextFileUtil(String filePath) {
        // set path
        fileName = filePath
        // open and read file
        def file = new File(filePath)
        if (!file.exists()) {
            throw new Exception("File not found: $filePath")
        }
        file.eachLine { line ->
            data << line.trim()
        }
    }

    // Get all the file strings as one text
    def getAsText() {
        def res = ''
        data.each { res += it + '\n' }
        return res
    }

    // Check if file contains a substring
    def contains(String txt) {
        for (str in data) {
            if (str.contains(txt)) {
                return true
            }
        }
        return false
    }

    // Save the file
    def saveFile() {
        File file = new File(fileName)
        data.each { file.write it }
    }

    // Get lines count
    def getLinesCount() {
        return data.size()
    }

}
