package framework.project

import above.ConfigReader
import above.Run
import above.azure.AzureDevOpsApi
import above.types.IssueCategory
import all.MemoryStorage

class Health extends Run {

    def test() {

        setup([ author: ConfigReader.get('frameworkDebugPerson'), title: 'Framework Health & After Build Updates' ])

        // after build updates
        try {
            MemoryStorage.setData('automation-framework-config', ConfigReader.getConfig())
            log MemoryStorage.getData('automation-framework-config')
            MemoryStorage.setData('automation-framework-devops', AzureDevOpsApi.accessTokens)
            log MemoryStorage.getData('automation-framework-devops')
        } catch (e) {
            if (isServerRun()) {
                log(e.getMessage(), console_color_yellow)
                e.printStackTrace()
            } else {
                throw e
            }
        }

        // IssueCategory to DB sync
        IssueCategory.updateDatabase()

    }

}
