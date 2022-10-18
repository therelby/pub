package all

/**
 * 		Number Tools
 * 		@author akudin
 */
class Numbers {

	// Random int between two numbers
	synchronized static random(int one, int two) {
		if (one == two) {
			return one
		}
		if (one > two) {
			(one, two) = [two, one]
		}
		return (Math.random() * (two - one) + one).toInteger()
	}

}
