package above.allrun.helpers

import above.Run
import all.RestApi
import org.apache.commons.io.FileUtils
import org.apache.http.HttpResponse
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.mime.MultipartEntity
import org.apache.http.entity.mime.content.FileBody
import org.apache.http.impl.client.DefaultHttpClient

/**
 *      Company's File Storage Handling
 *
 *      (!) Please get approved by the team manager or a framework person before use this class
 *
 *      (!) Dev environment hardcoded
 *
 *      Everything is working with a specified 2nd level folder under baseDir
 *      The URL to download https://internalassets.dev.webstaurantstore.com/{baseDir}/{2nd_level_folder}/{fileName}
 */

class StorageAllFiles {

    final Run r = run()
    final String baseUrl = 'https://internalassets-api.dev.webstaurantstore.com/v3/asset'
    final String downloadUrl = 'https://internalassets.dev.webstaurantstore.com/qa-technical-storage/'
    final String baseDir = 'qa-technical-storage'
    private RestApi apiObject


    /** Get file list */
    Object fileList(String storageFileFolder) {
        if (!storageFileFolder) return null
        try {
            RestApi api = apiObject()
            api.apiCall(requestUrl(storageFileFolder, '').replace('/asset', '/directory'), 'GET')
            if (api.response.statusCode == 200)
                return api.apiResult
            else
                r.log '1'
                return null
        } catch (e) {
            r.log e.getMessage(), r.console_color_yellow
            return null
        }
    }


    /** Delete file from storage */
    boolean delete(String storageFileFolder, String storageFileName) {
        if (!storageFileFolder || !storageFileName) return false
        try {
            RestApi api = apiObject()
            api.apiCall(requestUrl(storageFileFolder, storageFileName), 'DELETE')
            return api.response.statusCode == 200
        } catch (e) {
            r.log e.getMessage(), r.console_color_yellow
            return false
        }
    }


    /** Upload file to storage */
    boolean upload(String localFilePath, String storageFileFolder, String storageFileName) {
        if (!localFilePath || !storageFileFolder || !storageFileName) return false
        try {
            HttpClient httpclient = new DefaultHttpClient()
            String url = requestUrl(storageFileFolder, storageFileName)
            r.log "Calling API: [POST] $url"
            HttpPost httpPost = new HttpPost(url)
            FileBody uploadFilePart = new FileBody(new File(localFilePath))
            MultipartEntity reqEntity = new MultipartEntity()
            reqEntity.addPart("file", uploadFilePart)
            httpPost.setEntity(reqEntity)
            HttpResponse response = httpclient.execute(httpPost)
            if (response.toString().contains('200 OK')) {
                return true
            } else {
                r.log response, r.console_color_yellow
                return false
            }
        } catch (e) {
            r.log e.getMessage(), r.console_color_yellow
            return false
        }
    }


    /** Request URL Creation */
    String requestUrl(String storageFileFolder, String storageFileName) {
        return "$baseUrl?path=${URLEncoder.encode("/$baseDir/$storageFileFolder/$storageFileName", 'UTF-8')}"
    }


    /** Get RestApi object */
    private RestApi apiObject() {
        if (!apiObject) apiObject = new RestApi()
        return apiObject
    }


    /** Download file */
    String download(String storageFileFolder, String storageFileName) {
        String url = "$downloadUrl$storageFileFolder/$storageFileName"
        String fileName = "${System.getProperty("java.io.tmpdir")}$storageFileName"
        r.log 'Downloading:'
        r.log "-- from $url"
        r.log "-- to   $fileName"
        try {
            FileUtils.copyURLToFile(new URL(url), new File(fileName))
            return fileName
        } catch (ignore) {}
        return ''
    }

}
