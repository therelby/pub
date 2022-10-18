package framework.run.debug

import above.Execute

List tst = []

for (int i = 1; i < 33; i++) {
        tst << new B()
}

Execute.suite(
        [
                remoteBrowser: true,
                browser: 'edge',
                //browserVersionOffset: -1,
                //environment: 'test',
                //runType: 'Regular',
                //parallelThreads: 1
        ],
        tst,
        16
)
