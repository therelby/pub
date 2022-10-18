package above.jobs.cleanup

import above.Run
import above.allrun.helpers.StorageScreenshots

class ScreenshotsOldDelete extends Run {

    static void main(String[] args) {
        new ScreenshotsOldDelete().testExecute()
    }

    void test() {
        setup([ author: 'akudin', title: 'Deleting Old Screenshots From File Storage' ])
        new StorageScreenshots().deleteOldScreenshots()
    }

}
