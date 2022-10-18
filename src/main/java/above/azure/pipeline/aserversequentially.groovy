package above.azure.pipeline

import above.Execute

/**
 *      Automation Framework Server Executor - SEQUENTIALLY
 *      -- (!) any changes are impacting to all our testing
 */

println '\nSequential Pipeline Executor\n'

Map<String,String> env = System.getenv()
println 'Got Server Runs params:'
println "- SERVER_RUNS_CLASS_NAME:  ${env['SERVER_RUNS_CLASS_NAME']}"
println "- SERVER_RUNS_ENVIRONMENT: ${env['SERVER_RUNS_ENVIRONMENT']}"
println "- SERVER_RUNS_ONCE:        ${env['SERVER_RUNS_ONCE']}"

// creating params
Map params = [
        parallelThreads: 1,
        environment: env['SERVER_RUNS_ENVIRONMENT'],
]
if (env['SERVER_RUNS_ONCE'] == "1") {
    params.executeMode = 'OnceOnServer'
}
println 'Created suite params:'
println params

// creating instances
String[] classes = env['SERVER_RUNS_CLASS_NAME'].split(',')
List tests = []
for (cl in classes) {
    tests << Eval.me("return new ${cl}()")
}

// executing sequentially
Execute.suite(
        params,
        tests
)
