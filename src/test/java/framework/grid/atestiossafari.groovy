package framework.grid

import above.Execute
import framework.run.debug.B

Execute.suite(
        [
                browser: 'iossafari',
                environment: 'test'
        ],

        [
                new TcIosSafari(),
                new TcIosSafari(),
                new TcIosSafari(),
                new TcIosSafari()
        ]

)
