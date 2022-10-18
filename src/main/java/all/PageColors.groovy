package all

import javax.imageio.ImageIO

/**
 * 		Picture Color Analyzer Visual Testing Tools
 * 		(!) Chrome browser only
 * 		@author akudin
 */
class PageColors {

	// Images folder
	static final String tmpFileDir = System.getProperty("java.io.tmpdir").toString()
	// Image data
	static img = null


	/**
	 * Verify if color present for the page element
	 * (!) Returns true if at least one pixel contains the requested color
	 * (!) Large screenshots scanning takes a lot of time
	 * @param hexRgbColor - HTML style color code like '#00A900'
	 * @return - true/false if hexRgbColor provided
	 *         - list of maps sorted by count in opposite order, like:
	 *           [[color: 113245, count: 12], [color: 968793, count: 45], ... , [color: 16777215, count: 91]]
	 *           (!) decimal values of colors
	 *           So here you are hex to decimal example:
	 *           Integer color = Integer.parseInt(hexRgbColor.replace('#', ''), 16)
	 */
	synchronized static checkElementForColor(String xpath, String hexRgbColor = '') {

		run().log("Checking colors of element: $xpath")
		def fileName = takeScreenshot(xpath)
		def result = getColorsWithOptionalCheck(fileName, hexRgbColor)
		new File(fileName).delete()

		return result
	}


	/**
	 * Checks if defined color is primary (appeared for most pixels) for the page element
	 * (!) Large screenshots scanning takes a lot of time
	 * @param hexRgbColor - HTML style color code like '#00A900'
	 * @param ignoreColor - (white by default) provide null if there is no dominating background color
	 * @return true/false
	 */
	synchronized static checkElementForPrimaryColor(String xpath, String hexRgbColor) {

		run().log("Checking colors of element: $xpath")
		def fileName = takeScreenshot(xpath)
		def colors = getColorsWithOptionalCheck(fileName)
		new File(fileName).delete()

		// checking the primary color
		def decColor = Integer.parseInt(hexRgbColor.replace('#', ''), 16)
		if (decColor == colors.last().color || (colors.size() > 1 && colors[colors.size()-2].color == decColor)) {
			return true
		} else {
			return false
		}
	}


	/** Get most common color for element */
	synchronized static String getMostCommonColorForElement(String xpath) {
		def colors = getColorsForElement(xpath)
		if (colors) {
			return colors.last().color
		} else {
			return null
		}
	}


	/**
	 * Get colors for element
	 * @return - list of maps sorted by count in opposite order, like:
	 *           [[color: 'FF00AA', count: 12], [color: 'FFEECC', count: 45], ... , [color: '00DD00', count: 91]]
	 */
	synchronized static List getColorsForElement(String xpath) {
		def fileName = takeScreenshot(xpath)
		def colors = getColorsWithOptionalCheck(fileName)
		new File(fileName).delete()
		if (colors) {
			colors.eachWithIndex { it, idx ->
				colors[idx].color = getHexStringColor(it.color)
			}
			return colors
		}
		return null
	}


	/** Get hex string color like 'FFEEDD' from it's Integer value */
	synchronized static String getHexStringColor(Integer integerColor) {
		String color = Integer.toHexString(integerColor).toUpperCase()
		if (color.length() < 6) {
			color = '0' * (6-color.length()) + color
		}
		return color
	}


	// Get colors with optional check for a color present
	synchronized private static getColorsWithOptionalCheck(String fileName, String stopOnHexRgbColor = '') {

		if (loadPicture(fileName)) {
			run().log("Picture is loaded")
			def stopColor = null
			if (stopOnHexRgbColor) {
				stopColor = Integer.parseInt(stopOnHexRgbColor.replace('#', ''), 16)
				run().log("Target color: $stopOnHexRgbColor ($stopColor)")
			}

			run().log("Image size: ${img.getWidth()}x${img.getHeight()} pixels")
			run().log("Scanning pixel colors...")
			def colors = []
			def c = 0
			def idx = 0
			for (x in (0..img.getWidth()-1)) {
				for (y in (0..img.getHeight()-1)) {

					// scanning colors
					c = img.getRGB(x, y) & 0xFFFFFF
					if (stopOnHexRgbColor) {
						// looking for defined color
						if (c == stopColor) {
							return true
						}
					} else {
						// storing colors
						idx = colors.findIndexOf{ it.color == c }
						if (idx < 0) {
							colors << [color: c, count: 1]
						} else {
							colors[idx].count += 1
						}
					}
				}
			}

			// got all colors
			if (stopOnHexRgbColor) {
				run().log("Target color not found")
				return false
			} else {
				return colors.sort { a, b -> a.count <=> b.count }
			}
		}

		// file loading issue
		run().log("Can't load the picture")
		return false
	}


	// Load picture
	synchronized private static loadPicture(String fileName) {

		run().log("Loading picture $fileName ...")
		File file = new File(fileName)
		if (!file.exists()) {
			return false
		}

		img = ImageIO.read(file)
		return true
	}


	// Screenshot taking
	synchronized private static takeScreenshot(String xpath) {
		run().log("Taking screenshot...")
		def fileName = tmpFileDir + UUID.randomUUID().toString().replaceAll('-', '') + '.png'
		ScreenCapture.elementScreenshot(xpath, fileName)
		return fileName
	}

}
