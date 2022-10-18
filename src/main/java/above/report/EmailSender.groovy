package above.report

import javax.mail.*
import javax.mail.internet.*

/**
 * Email sender
 * (!) Works only on DevOps (TFS) servers
 * (!) Will not works locally even with debug-off profile
 * @author akudin
 */

class EmailSender {

	// Server params
	def host = 'prod.mta.backends.tech'
	def port = '25'

	// Domain
	def emailDomain = ReportSender.emailDomain

	/**
	 * Send multiple part HTML with in-line images
	 * (!) Works on DevOps servers only
	 * - images should be provided with source paths like <img src="c:/users/user/image.jpeg" />
	 * - cid: value will be replaced to unique id of embedded image data
	 */
	def send(String from, String to, String subject, String content) {

		def urls = []
		def ids = []

		// getting first url
		def parts = content.split('<img src="')
		if (parts.size() > 1) {
			for (i in 1..parts.size()-1) {
				// adding url
				urls << parts[i].split('"')[0].toString()
				// creating id for current image
				ids << UUID.randomUUID().toString().replace('-', '')
				// replacing image url to id
				content = content.replace('<img src="' + urls.last(), '<img src="cid:' + ids.last().toString())
				run().log 'Replacing image:'
				run().log urls.last() + ' -> ' + ids.last().toString()
				// correcting wrong data
				urls[urls.size()-1] = urls.last().replace('\u202a', '')
			}
		}

		// creating multipart stuff
		MimeMultipart body = new MimeMultipart("related")
		MimeBodyPart html = new MimeBodyPart()
		html.setText(content, "UTF-8", "html")
		body.addBodyPart(html)

		// adding images
		if (urls) {
			for (i in 0..(urls.size()-1)) {
				MimeBodyPart imagePart = new MimeBodyPart()
				imagePart.attachFile(urls[i])
				imagePart.setContentID("<${ids[i]}>")
				imagePart.setDisposition(MimeBodyPart.INLINE)
				body.addBodyPart(imagePart)
			}
		}

		// sending
		sendToSever(from, to, subject, body)
	}


	/**
	 * Send simple HTML body mail
	 * (!) Works on DevOps servers only 
	 */
	def sendToSever(String from, String to, String subject, content) {

		// no domain?
		if (!to.contains('@')) {
			to += emailDomain
		}

		// Setup properties
		Properties props = System.getProperties()
		props.put("mail.smtp.user", to)
		props.put("mail.smtp.host", host)
		props.put("mail.smtp.port", port)

		// Setup message
		MimeMessage message = new MimeMessage(Session.getDefaultInstance(props))
		message.setFrom(new InternetAddress(from))
		message.addRecipients(Message.RecipientType.TO, new InternetAddress(to))
		message.setSubject(subject)
		message.setContent(content, 'text/html;charset=utf-8')

		// Send email
		Transport.send(message, to, '')
	}

}
