package all

/**
 * HTML Tools
 * @author akudin
 */
class Html {

	// Decoding string with stuff like &nbsp;
	def static decode(String text) {
		def res = (new XmlSlurper().parseText("<wsstag>${text}</wsstag>")).toString()
		return res.replace('<wsstag>', '').replace('</wsstag>', '').replace('  ', ' ').trim()
	}

}
