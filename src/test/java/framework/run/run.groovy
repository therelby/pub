package framework.run

import above.Execute

/**
 *      Run Basics Unit Test
 *      - also included all.Db, all.RestApi
 *      @author akudin
 */

// Azure API
Execute.suite([
        new Azure()
], 1, true)

// DEBUG
// sequential
Execute.suite([
        new WebConceptSetupAndLogger(),
        new DesktopConceptSetupAndLogger(),
        new WebConceptReportCheck()
], 1)


// REGULAR
// sequential
Execute.suite([
        new WebConceptSetupAndLogger(),
        new DesktopConceptSetupAndLogger(),
        new WebConceptReportCheck()
], 1, true)

// DEBUG
// parallel
Execute.suite([
        new WebConceptParallel().usingRemoteBrowser(),
        new WebConceptParallel().usingEnvironment('test').usingRemoteBrowser(),
        new WebConceptParallel().usingEnvironment('prod').usingRemoteBrowser(),
], 2)


// REGULAR
// parallel
Execute.suite([
        new WebConceptParallel().usingRemoteBrowser(),
        new WebConceptParallel().usingEnvironment('test').usingRemoteBrowser(),
        new WebConceptParallel().usingEnvironment('prod').usingRemoteBrowser(),
], 2, true)
