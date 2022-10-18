package framework.rundesktop

import above.Execute

/**
 *      Run & Testing Concepts Unit Test
 */
Execute.suite([
        new LaunchSps('ttoanle-spsDailyRun-01'),
        new LaunchSps('ttoanle-spsDailyRun-02'),
        new LaunchSps('ttoanle-spsDailyRun-03'),
        new LaunchSps('ttoanle-spsDailyRun-04'),
    ], 1, true)