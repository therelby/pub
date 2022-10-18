package all

/**
 * 		Time functions
 * 		@author akudin
 */
class Time {


	// Pause in sec
	synchronized static pause(sec) {
		Thread.sleep(sec * 1000)
	}


	// Visual timeout in sec
	synchronized static visualTimeout(sec) {
		while (sec > 0) {
			Thread.sleep(999)
			print "$sec "
			sec--
			if (sec % 10 == 0) {
				println ''
			}
		}
		println 'Done'
	}

}
