package above.web

import above.RunWeb
/**
 *     File Downloading, Uploading. File readers.
 *      @vdiacuk @kyilmaz  @micurtis
 */
class FuncFile {

    RunWeb r = run()
    // WebDriver driver = r.getWebDriver() (!) getting driver inside method if needed - to avoid null if driver reinstating


    /**
     *      Download file from url
     *      url - link to file; fileName - desired file name to save in,
     *      if fileName not set or, null trying to parse fileName from url
     *
     *      return path to file
     */
    String downloadFile(String url, String fileName = null) {
        try {
            r.logDebug("Loading file from url: $url")
            if (!fileName) {
                fileName = url.split('/').last().trim()
            }
            String path = System.getProperty("java.io.tmpdir").toString() + fileName
            r.log "Saving file to path: $path"
            new File(path).withOutputStream { out ->
                out << new URL(url).openStream()
            }
            return path
        } catch (e) {
            r.addIssueTrackerEvent("Can not download file: $fileName, from url: $url", e)
            return null
        }

    }

}


