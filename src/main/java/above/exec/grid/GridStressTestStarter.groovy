package above.exec.grid

import above.RunWeb
import above.azure.pipeline.ServerRunsQueue

class GridStressTestStarter extends RunWeb {

    static void main(String[] args) {
        new GridStressTestStarter().testExecute([ runType: 'Regular' ])
    }

    int parallelTasks = 32

    void test() {

        setup([ author: 'akudin', product : 'wss', project: 'Webstaurant.StoreFront',
                title: 'Grid Stress Task Starter', keywords: 'debug', PBI: 1, logLevel: 'info' ])

        log "Starting tasks: $parallelTasks"

        for (i in new int[parallelTasks]) ServerRunsQueue.runTestOnServer('above.exec.grid.GridStressTestTask', 'dev')

    }

}
