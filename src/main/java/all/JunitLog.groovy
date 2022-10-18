package all

/**
 * 		JUnit Log Generator
 * 		Based on above.Run data
 * 		@author akudin
 */
class JunitLog {

    static proName = ''
    static proValue = ''
    static property = "<property name=\"${ -> proName }\" value=\"${ -> proValue }\"/>"
    static properties = ''

    static disabled = ''
    static errors = ''
    static failures = ''
    static tsName = ''
    static tests = ''
    static totaltime = ''
    static hostname = ''
    static id = ''
    static skippedQty = ''
    static timestamp = ''
    static packageName = ''
    static header = """<?xml version="1.0" encoding="UTF-8"?>

		<testsuites disabled="${ -> disabled }"
					errors="${ -> errors }"
					failures="${ -> failures }"
					name="${ -> tsName }"
					tests="${ -> tests }"
					time="${ -> totaltime }">
		
		<testsuite name="${ -> tsName }"
				   tests="${ -> tests }"
				   disabled="${ -> disabled }"
				   errors="${ -> errors }"
				   failures="${ -> failures }"
				   hostname="${ -> hostname }"
				   id="${ -> id }"
				   package="${ -> packageName }"
				   skipped="${ -> skippedQty }"
				   time="${ -> totaltime }"
				   timestamp="${ -> timestamp }">
		
		<properties>
		${ -> properties }</properties>
"""

    static skipped = '<skipped message="%message" />'
    static error = '<error message="Script Error" type="Error">%description</error>'
    static failure = '<failure message="Product Issue" type="Issue">%description</failure>'

    static tcName = ''
    static assertions = ''
    static className = ''
    static status = ''
    static tcTime = ''
    static testcase = """
<testcase name="${ -> tcName }"
	      assertions="${ -> assertions }"
	      classname="${ -> className }"
	      status="${ -> status }"
	      time="${ -> tcTime }">
${ -> skipped }${ -> error }${ -> failure }</testcase>"""
    static testcases = ''

    static footer = """
</testsuite>

</testsuites>
"""

    // Add property
    synchronized static addProperty(String name, String value) {
        proName = name
        proValue = value
        properties += property + '\n'
    }


    // Add testcase
    synchronized static addTestcase(id, data) {
        tcName = id.toString()
        assertions = (data.checksCount / data.tcIds.size()).toInteger()
        className = data.project
        tcTime = (((new Date().getTime() - data.startTime) / 1000) / data.tcIds.size()).toString()
        if (data.checksCount == 0 && !data.reports) {
            status = 'Error'
            error = error.replace('%description', 'Nothing was checked in the test') + '\n'
        } else {
            error =''
        }
        if (data.reports) {
            status = 'Blocked'
            skipped = skipped.replace('%message',
                 (data.reports.findAll{ it.tcId==id }.collect{it.msg}.join('; ')).toPrettyString().replace('"', '')) + '\n'
        } else {
            skipped = ''
        }
        if (data.issues) {
            status = 'Failed'
            failure = failure.replace('%description',
                 (data.issues.findAll{ it.tcId==id }.collect{it.msg}.join('; ')).toPrettyString().replace('"', '')) + '\n'
        } else {
            failure = ''
        }
        if (!error && !failure && !skipped) {
            status = 'Passed'
        }
        testcases += testcase + '\n'
    }


    // Generate and save JUnit log
    // (!) Based on current script RUN data
    // (!) One script report only
    // Saves to ...
    synchronized static save(String fileName) {

        def data = run().getInfo()

        // creating properties
        addProperty('OS', data.os)
        addProperty('Environment', data.envName)
        addProperty('Browser', data.browser)
        addProperty('Run Host', data.runHost)
        addProperty('Script Name', data.scriptName)
        addProperty('Script Author', data.scriptAuthor)

        // creating header
        def currentTime = new Date().getTime()
        disabled = data.reportsCount.toString()
        errors = (!data.scriptError).toString().replace('true', '0').replace('false', '1')
        failures = data.issuesCount.toString()
        tsName = data.scriptName
        tests = data.tcIds.size().toString()
        totaltime = ((currentTime - data.startTime) / 1000).toString()
        hostname = data.runHost
        id = data.runId
        skippedQty = data.reportsCount.toString()
        timestamp = currentTime.toString()
        packageName = data.scriptName

        // adding testcases
        data.tcIds.each { addTestcase(it, data) }

        // result
        def res = header + testcases + footer

        run().log res

        // saving
        def path = "${System.getProperty("user.dir").toString()}/reports/$fileName"
        run().log("Saving JUnit log to: $path")
        def newFile = new File(path)
        newFile.write(res)
    }


}
