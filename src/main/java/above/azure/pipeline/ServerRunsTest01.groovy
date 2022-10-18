package above.azure.pipeline

import above.RunWeb

class ServerRunsTest01 extends RunWeb {

    void test() {

        setup('akudin', "Server Runs Test 01",
                ["product:wss", "tfsProject:Automation%20Projects",
                 'keywords:platform test', "PBI:456468", 'logLevel:info'])

        for (i in 1..1) {
            log '111111111111111111111111111111'
            sleep(3000)
            //tryLoad()
            //log(getTitle(), console_color_green)
        }
    }

}
