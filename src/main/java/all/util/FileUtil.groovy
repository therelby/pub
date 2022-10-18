package all.util

import above.RunWeb

/**
 *     Utility methods for  files
 *      Refactored from Katalon common.Files
 *     akudin vdiachuk
 **/
class FileUtil {

    // Write text to a file
    synchronized static boolean writeText(String text, String filePathAndName, useProjectFolder = true) {
        RunWeb r = run()
        try {
            if (useProjectFolder) {
                filePathAndName = r.projectPath + filePathAndName
            }
            r.log("Saving text to: $filePathAndName ...")

            def newFile = new File(filePathAndName)
            newFile.write(text)
            return true
        } catch (e) {
            r.log("Can not write text to file:$e")
            return false
        }
    }

}
