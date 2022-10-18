package above.allrun.helpers

import above.Run

/**
 *      Screenshot Storage Base on FileStorage
 */

class StorageScreenshots {

    final static Run r = run()
    final static String secondLevelStorageFolder = 'screenshots'
    final StorageAllFiles storage = new StorageAllFiles()

    final static devImageUrl = 'https://internalassets.dev.webstaurantstore.com/qa-technical-storage/screenshots/'

    /** Get image URL */
    synchronized static String getDevImageUrl(long id) {
        return "${devImageUrl}${id}.png"
    }


    /** Delete old screenshots */
    void deleteOldScreenshots() {
        long timeNow = r.getRealTimeStamp()

        // getting file list
        List<String> files =
                new StorageAllFiles().fileList('screenshots').assets
                        .findAll{ it.type == 'file' }.collect{ it.name }

        r.log "Files total: ${files.size()}"

        // deleting outdated screenshot files
        int cnt = 0
        for (file in files) {
            if (!file.endsWith('.png')) continue
            long ssId = Long.valueOf(file.split('\\.')[0])
            if (ssId > timeNow - 259200000) continue // 3 days
            delete(ssId)
            cnt++
        }

        // deleting missed records
        r.dbExecute("DELETE FROM Stat_Images WHERE id < $timeNow", 'qa')

        r.log "Deleted files: $cnt"

        r.cleanLog()
    }


    /** Save All Screenshots After Test Class Finished */
    void saveAllScreenshots() {
        int tidx = -1
        for (msg in r.info.testcases.tests) {
            tidx++
            for (type in ['checks', 'reports']) {
                int idx = -1
                for (item in msg[type]) {
                    if (item.screenshot && (item.screenshot instanceof String) && item.screenshot != '') {
                        idx++
                        r.info.testcases.tests[tidx][type][idx].screenshot = save(item.screenshot)
                        r.log "Screenshot: ${StorageScreenshots.getDevImageUrl(r.info.testcases.tests[tidx][type][idx].screenshot)}"
                    }
                }
            }
        }
    }


    /** Delete Screenshot */
    boolean delete(long id) {
        if (storage.delete(secondLevelStorageFolder, id.toString() + '.png')) {
            int att = 3
            while (att > 0) {
                att--
                if (r.dbExecute("DELETE FROM Stat_Images WHERE id = $id;", 'qa')) {
                    break
                } else {
                    if (att == 0) return false
                }
            }
            return true
        } else {
            return false
        }
    }


    /** Save Screenshot */
    long save(String filePath) {
        if (!filePath) return 0

        long result
        int att = 3 // handling unreliable id
        while (att > 0) {
            result = r.getRealTimeStamp() // unreliable unique id
            att--
            if (storage.upload(filePath, secondLevelStorageFolder, result.toString() + '.png')) {
                att = 3
                while (att > 0) {
                    att--
                    try {
                        r.dbExecute("INSERT INTO Stat_Images (id, image_data) VALUES ($result, '');", 'qa')
                        break
                    } catch (e) {
                        r.log e.getMessage(), r.console_color_yellow
                        sleep(500)
                    }
                }
                break
            } else if (att == 0) {
                r.log 'Can not save screenshot'
                return 0
            }
        }

        return result
    }

}
