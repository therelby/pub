package above.azure

import above.report.ReportSender
import all.DbTools
import all.VariableStorage
import java.awt.Font

/**
 * TFS Stuff Statictics 
 * @author akudin
 */
class AzureDevOpsStats {

	// Reports mail list
	static sendTo = ['akudin', 'jeroberts']
	// Linked data
	static notLinkedAll = DbTools.selectAll(AzureDevOpsProjects.notLinkedTcsQuery, 'tfs')
	static notLinkedTcs
	// year data type
	static yt


	// Get report
	synchronized static getReport(tcs, String project) {

		// ignoring empty projects
		if (!tcs) { return '' }

		// getting notLinkedTcs
		if (!notLinkedTcs) {
			notLinkedTcs = notLinkedAll.collect{it.WorkItemId.toInteger()}.unique().sort()
		}

		// getting years type
		for (t in tcs) {
			if (t.createDate) {
				if (t.createDate instanceof Date) { yt=0; break }
				if (t.createDate instanceof String) { yt=1; break }
			}
		}

		def users = ''
		def noSteps = ''
		def u = (tcs.collect{it.createdBy} + tcs.collect{it.assignedTo}).unique().sort()
		for (it in u) {

			if (!it) { continue }

			users += AzureDevOpsStatsTemplates.userRow
					.replace('%name', it)
					.replace('%created', tcs.findAll{item->item.createdBy==it}.size().toString())
					.replace('%assigned', tcs.findAll{item->item.assignedTo==it}.size().toString())

			def tcIds = tcs.findAll{item->item.assignedTo==it && item.steps!=true}
			if (tcIds) {

				def tcText = ''
				tcIds.each { tcText += "<a href=\"${AzureDevOpsTestcase.getTfsUrl(it.id.toInteger(), it.project)}\">${it.id}</a> " }

				noSteps += AzureDevOpsStatsTemplates.noStepsRow
						.replace('%name', it)
						.replace('%count', tcs.findAll{item->item.createdBy==it && item.steps!=true}.size().toString())
						.replace('%tcs', tcText)
			}
		}

		// no steps and not linked intersections
		def nsnl
		def ns = tcs.findAll{ it.steps != true }
		if (ns) {
			def inter = ns.collect{it.id.toInteger()}.intersect(notLinkedTcs)
			if (inter) {
				nsnl = inter.size().toString()
			} else {
				nsnl = '0'
			}
		} else {
			nsnl = '0'
		}

		def notLinked
		if (!project.startsWith('ALL')) {
			notLinked = notLinkedAll.findAll{it.ProjectName==project}.size().toString()
		} else {
			notLinked = notLinkedAll.size().toString()
		}

		// checking duplicated titles
		def d1 = []
		def d9 = []
		def dup100 = 0
		//def dup90 = 0
		def prjs = tcs.collect{it.project}.unique().sort()
		for (p in prjs) {
			println "LOOKING FOR DUPS -> $p"

			def ttls = tcs.findAll{it.project==p}
			def last = 0
			for (t in ttls) {

				// 100%
				def list100 = tcs.findAll{
					it.project==p && it.systemState!=null && !it.deleted && it.id != t.id && it.title &&
							!t.dups.contains(it.id) && it.title == t.title}
				dup100 += list100.size()
				list100.each {
					it.dups << t.id
					d1 << [
						a: t,
						b: it
					]
				}

			}
		}

		// dups
		def dups = ''
		for (user in u) {

			def txt = ''
			def found = false

			d1.each {
				if (it.a.assignedTo == user) {
					found = true
					txt += """
<tr>
<td style=\"width: 70;\">
<a href=\"${AzureDevOpsTestcase.getTfsUrl(it.a.id.toInteger(), it.a.testProject)}\">${it.a.id}</a><br />
<a href=\"${AzureDevOpsTestcase.getTfsUrl(it.b.id.toInteger(), it.b.testProject)}\">${it.b.id}</a>
</td>
<td>
${it.a.titlePrint}<br />
${it.b.titlePrint}
</td>
</tr>
"""
				}
			}

			if (found) {
				dups +=  AzureDevOpsStatsTemplates.userDups.replace('%u_name', user).replace('%dups', txt)
			}
		}


		// no updates count
		def noUpdates
		def updated
		if (yt == 0) {
			noUpdates = tcs.findAll{!it.lastUpdateDate || it.lastUpdateDate.format('yyyy').toInteger() < (new Date().format('yyyy').toInteger() - 1)}.size()
			updated = tcs.findAll{it.lastUpdateDate && it.lastUpdateDate.format('yyyy').toInteger() >= (new Date().format('yyyy').toInteger() - 1)}.size()
		} else {
			noUpdates = tcs.findAll{!it.lastUpdateDate || it.lastUpdateDate[0..3].toInteger() < (new Date().format('yyyy').toInteger() - 1)}.size()
			updated = tcs.findAll{it.lastUpdateDate && it.lastUpdateDate[0..3].toInteger() >= (new Date().format('yyyy').toInteger() - 1)}.size()
		}

		return AzureDevOpsStatsTemplates.body
				.replace('%project', project)
				.replace('%date', new Date().format('MM.dd.yyyy'))
				.replace('%total', tcs.size().toString())
				.replace('%100dup', dup100.toString())
				.replace('%ran', tcs.findAll{it.lastStatusUpdate&&common.DateTools.dateFromYmd(it.lastStatusUpdate)>new Date('6/28/2020')}.size().toString())
				.replace('%no_updates', noUpdates.toString())
				.replace('%updated', updated.toString())
				.replace('%automated', tcs.findAll{it.automated==true}.size().toString())
				.replace('%regression', tcs.findAll{it.regression==true}.size().toString())
				.replace('%closed', tcs.findAll{it.systemState=='Closed'}.size().toString())
				.replace('%no_steps', ns.size().toString())
				.replace('%no_st_not_linked', nsnl)
				.replace('%not_linked', notLinked)
				.replace('%deleted', tcs.findAll{it.deleted==true || it.systemState==null}.size().toString())
				.replace('%years1', getYearsChart('Created by Years', 'createDate', tcs))
				.replace('%years2', getYearsChart('Updated by Years', 'lastUpdateDate', tcs))
				.replace('%users_counts', users)
				.replace('%users_no_steps', noSteps)
				.replace('%users_dups', dups)
	}


	// Scan project for planes, suites, and testcases and get its statistics
	synchronized static scanProject(String project) {

		def prj = project.replace(' ', '%20')

		// project
		println("========================================================")
		println("========================================================")
		run().log("Poject: $project")
		println("========================================================")
		println("========================================================")

		// testcases
		def tcs = []

		// getting all plans
		def plans = AzureDevOpsTestPlans.getAllPlans(prj)
		//common.Print.nicePrint(plans)
		/*
		 if (!plans) {
		 throw new Exception("FAIL!")
		 }
		 return []
		 */

		// plans
		for (pl in plans.value) {

			// plan tcs count
			def plTcs = 0
			def outTcs = 0
			def plNoSteps = 0

			// getting suites
			def suites = AzureDevOpsTestPlans.getSuites(pl.id, prj)

			// suites
			for (su in suites.value) {

				plTcs += su.testCaseCount

				println project + ' // ' + pl.name + ' // ' + su.name

				def tcsList = AzureDevOpsTestPlans.getTcs(su.id, pl.id, prj)
				if (tcsList && tcsList.value) {

					for (tc in tcsList.value) {

						//print '.'

						//common.Print.nicePrint(tc)
						//println '- - - - - - - - - - - - - -'

						def details = AzureDevOpsTestPlans.apiCall(tc.testCase.url)

						/*
						 if (details) {
						 common.Print.nicePrint(details)
						 System.exit(0)
						 }*/

						def titlePrint = ''
						def title = ''
						def createdBy
						def assignedTo
						def systemState
						def automated = false
						def regression = false
						def createDate = null
						def steps = false
						def deleted = false
						def lastUpdateDate = null
						def lastStatusUpdate = null
						if (details && details.fields && details.fields["System.State"] != 'Closed') {

							// getting points
							def points = AzureDevOpsTestcase.getTcPoints(tc.testCase.id.toInteger(), prj)
							if (points) {
								def d = AzureDevOpsTestPlans.apiCall(points.points.last().url)
								if (d && d.lastResultDetails && d.lastResultDetails.dateCompleted) {
									lastStatusUpdate = new Date(d.lastResultDetails.dateCompleted[5..6] + '/' + d.lastResultDetails.dateCompleted[8..9] + '/' + d.lastResultDetails.dateCompleted[0..3])
								}
							}
							//println lastStatusUpdate

							// getting updates data
							def upd = AzureDevOpsWorkItems.getWorkItemUpdates(details.id, prj)
							//common.Print.nicePrint(upd)

							upd.value.each {
								if (it.fields) {
									if (it.fields["Microsoft.VSTS.TCM.Steps"] && it.fields["Microsoft.VSTS.TCM.Steps"].newValue) {
										steps = true
									}
									if (it.fields["Microsoft.VSTS.TCM.AutomationStatus"] && it.fields["Microsoft.VSTS.TCM.AutomationStatus"].newValue == 'Automated') {
										automated = true
									}
									if (it.fields["wss.qa.RegressionCandidate"] && it.fields["wss.qa.RegressionCandidate"].newValue == 'Yes') {
										regression = true
									} else {
										regression = false
									}
								}
							}

							titlePrint = details.fields["System.Title"]
							title = details.fields["System.Title"].replaceAll("[^a-zA-Z0-9]", "").replace(' ', '').toLowerCase()
							//println title
							createDate = new Date(details.fields["System.CreatedDate"][5..6] + '/' + details.fields["System.CreatedDate"][8..9] + '/' + details.fields["System.CreatedDate"][0..3])
							createdBy  = details.fields["System.CreatedBy"].displayName + ' ' + details.fields["System.CreatedBy"].uniqueName
							if (details.fields["System.AssignedTo"]) {
								assignedTo = details.fields["System.AssignedTo"].displayName + ' ' + details.fields["System.AssignedTo"].uniqueName
							} else {
								assignedTo = ''
							}
							systemState = details.fields["System.State"]
							lastUpdateDate = AzureDevOpsDataTools.getLastDate(upd)

						} else {
							createdBy = tc.pointAssignments.last().tester.displayName + ' ' + tc.pointAssignments.last().tester.uniqueName
							assignedTo = createdBy
							systemState = null
							steps = true
							if (!details) { deleted = true }
							lastUpdateDate = null
						}

						tcs << [
							id: tc.testCase.id,
							titlePrint: titlePrint,
							title: title,
							dups: [],

							project: project,
							//plan: pl.name,
							//suite: su.name,

							createDate: createDate,
							lastUpdateDate: lastUpdateDate,
							lastStatusUpdate: lastStatusUpdate,

							createdBy: createdBy,
							assignedTo: assignedTo,

							systemState: systemState,

							automated: automated,

							regression: regression,

							steps: steps,

							deleted: deleted
						]

						//common.Print.nicePrint(tcs.last())
					}
				}

				//println ''

			} // suites


		} // plans

		return tcs
	}


	// Get all TFS stats
	synchronized static allProjectsStats(String continueAfter = '', String onlyThisProject = '', Boolean loadFromStorage = false) {

		def wait = false
		if (continueAfter) {
			wait = true
		}

		// total data
		for (it in AzureDevOpsProjects.names.sort()) {

			// checking project focus
			if (onlyThisProject && onlyThisProject != it) {
				continue
			}

			// passing if needed
			if (wait) {
				if (it == continueAfter) { wait = false }
				continue
			}

			// getting testcases data
			def tcs
			if (loadFromStorage) {
				println "Loading $it from VariableStorage"
				tcs = VariableStorage.getData("akudin-${it.replace(' ', '-')}")
			} else {
				tcs = scanProject(it)
			}
			//common.Print.nicePrint tcs

			// getting and sending report
			def body = getReport(tcs, it)
			if (body) {
				ReportSender.emailReport("${it.toUpperCase()} TFS Project Testcases Report", body, sendTo)
			}

			// saving to storage
			if (!loadFromStorage) {
				VariableStorage.setData("akudin-${it.replace(' ', '-')}", tcs)
			}
		}

		// total report
		def ttl = []

		for (it in AzureDevOpsProjects.names.sort()) {
			println "LOADING -> $it"
			def data = VariableStorage.getData("akudin-${it.replace(' ', '-')}")
			if (data) {
				ttl += data
			}
		}

		def body = getReport(ttl, 'ALL PROJECTS')
		ReportSender.emailReport("ALL PROJECTS TFS Project Testcases Report", body, sendTo)

		println "Done."
	}

}
