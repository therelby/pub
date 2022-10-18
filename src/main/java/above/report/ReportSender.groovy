package above.report

import above.azure.AzureDevOpsApi

/**
 * RUN Reports Sender
 * @author akudin
 */
class ReportSender {

	// Email domain
	def static emailDomain = '@webstaurantstore.com'


	// Send report
	synchronized static void emailReport(String subject, String body, mailList = []) {

		// Email or save file
		if (run().isServerRun()) {

			// creating sender
			def e = new EmailSender('prod0')

			// sending to the team members
			run().log("Sending emails...")
			if (!mailList) {

				// default list
				AzureDevOpsApi.accessTokens.each { key, value ->
					//if (value?.token) {
						run().log("Sending to: ${key + emailDomain}")
						e.send(key + emailDomain, subject, body)
					//}
				}

			} else {

				// custom list
				mailList.each {
					run().log("Sending to: ${it + emailDomain}")
					e.send(it + emailDomain, subject, body)
				}

			}

		} else {
			// local machine -- saving instead sending
			run().log("Saving to local file...")
			def newFile = new File("C:\\Users\\${run().testAuthor}\\Documents\\${ -> subject.replace('/', '-').replace(':', '-')}.html")
			newFile.write(body)
		}

	}

}
