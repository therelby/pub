package all

/**
 * Looped chain handling
 * @author akudin
 */

class LoopedChain {

	def list = []
	def size = 0
	def pointer = -1

	// Class constructor sets list
	LoopedChain(list) {
		if (list == []) {
			throw new Exception("The list is empty")
		}
		this.list = list
		size = list.size()
	}


	// Get next in loop
	def getNext() {
		if (pointer == size-1) {
			pointer = 0
		} else {
			pointer++
		}
		return list[pointer]
	}

}
