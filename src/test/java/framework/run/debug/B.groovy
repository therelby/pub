package framework.run.debug

import above.allrun.helpers.StorageAllFiles
import above.azure.pipeline.ServerRunsQueue
import all.Data
import groovy.io.FileType
import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator

class B extends above.RunWeb {

    static void main(String[] args) {
        new B().testExecute([
                //browser: 'edge',
                remoteBrowser: true,
                //browserVersionOffset: -1,
                //runType: 'Regular',
                //environment: 'test'
        ])
    }


    void test() {

        //log testBrowserVersion

        setup([ author: 'akudin', title: 'Debug', PBI: 1, product: 'wss|dev,test',
                project: 'Webstaurant.StoreFront', keywords: 'debug', logLevel: 'info'])





        List<String> list = []

        def dir = new File("${System.getProperty("user.dir")}/src/main/java")
        dir.eachFileRecurse (FileType.FILES) { file ->
            list << file.path
        }

        Map cls = [:]
        for (file in list.reverse()) {
            if (file.endsWith('.groovy')) {
                String className = file.replace(System.getProperty("user.dir") + '\\src\\main\\java\\', '')
                        .replace('.groovy', '')
                        .replace('\\', '.')
                //log className
                cls.put(className, null)
            }
        }

        log cls.size()


    }



    void runTests() {

        List tests = dbSelect(
                "SELECT TOP(60) package + '.' + class_name AS class FROM ServerRuns_Classes WHERE author = 'ttoanle' AND run_nightly = 1 AND environment = 'dev'",
                'qa'
        ).collect { it.class }

        log tests.size()
        //log tests

        for (test in tests) {
            ServerRunsQueue.runTestOnServer(test, 'dev', true, [:], false, true)
        }

    }

}
