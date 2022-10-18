package framework.project

import above.RunWeb
import above.azure.AzureDevOpsTestcase
import above.report.EmailSender
import all.DbTools
import all.Html
import org.jsoup.Jsoup
import wss.search.SearchForm

/**
 *    Server Stuff Heath Checker
 */
class ProjectHeath extends RunWeb {

    static void main(String[] args) {
        new ProjectHeath().testExecute([ remoteBrowser: true ])
    }

    /**
     *      (!) NOT AN EXAMPLE
     *      (!) Newer use this code as example
     */
    def test() {

        setup('akudin', 'Project Heath Checker',
              ['product:wss', 'tfsProject:Webstaurant.StoreFront',
               'keywords:health', "PBI:1"])

        log 'Project Heath Checking...'

        log '--'
        log 'DATABASE'
        String data = DbTools.selectAll('SELECT TOP 1 id, run_id, test_class FROM Stat_Test_Runs ORDER BY id DESC;', 'qa')
        log "Random record selected form QA database:"
        logData data
        assert data

        log 'Checking all DB connections:'
        log databaseConfig
        //setLogLevel('debug')
        for (key in databaseConfig.keySet()) {
            if (key != 'tfs' && key != 'sps' && (isLocalRun() || !((String)(databaseConfig[key])).contains('integratedSecurity=true'))) {
                assert key
                log "-- $key ..."
                try {
                    log dbSelect("select top 1 name from sys.tables", key)
                } catch (e) {
                    log e.getMessage(), console_color_red
                }
                log "-- $key Done."
                log ''
            } else {
                log 'SKIPPED: '
                log databaseConfig[key]
                log ''
            }
        }
        //setLogLevel('info')

        log '--'
        log 'WEB'
        if (isLocalRun()) {
            log('Ignore red browser-related console spam', console_color_red)
        }
        tryLoad('homepage')
        assert getCurrentUrl() == getUrl('homepage')
        SearchForm.search('table')
        assert getPageSource().contains('table')
        closeBrowser()

        /**
        if (isServerRun()) {
            log '--'
            log 'EMAIL'
            new EmailSender().sendMultipart('akudin', 'Health Checker', 'Done')
        }*/

        log '--'
        log('Test Done', console_color_green) // never use console colors for regular testing
    }

}
