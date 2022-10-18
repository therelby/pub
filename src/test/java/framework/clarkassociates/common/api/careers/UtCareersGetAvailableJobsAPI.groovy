package framework.clarkassociates.common.api.careers

import above.RunWeb
import clarkassociates.common.api.careers.getavailablejobs.GetAvailableJobsAPI

class UtCareersGetAvailableJobsAPI extends RunWeb {

    static void main(String[] args) {
        new UtCareersGetAvailableJobsAPI().testExecute([

                browser      : 'chrome',
                remoteBrowser: false,
                //  browserVersionOffset: -1,   // use specific browser version
                environment  : 'dev',        // use specific environment
                runType      : 'Debug',//'Regular'          //Debug
                /*   parallelThreads: 1,
               runType: 'Regular' ,
               browserVersionOffset: -1   */
        ])
    }

    def test() {
        final int PBI = 658966
        setup([
                author  : 'vdiachuk',
                title   : 'Careers Get Available jobs List API | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'ca',
                project : 'Clark Associates',
                keywords: 'careers get job api',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])

        GetAvailableJobsAPI getAvailableJobsAPI = new GetAvailableJobsAPI()
      //  log getAvailableJobsAPI.getResult()
        assert getAvailableJobsAPI.getResult() as boolean
        assert getAvailableJobsAPI.getStatusCode() == 200

    }
}