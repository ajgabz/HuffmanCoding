package huffman;

public abstract class HuffmanNode implements Comparable<HuffmanNode> {
	private int frequency;
	
	public HuffmanNode(int frequency) {
		if (frequency < 1) {
			throw new IllegalArgumentException("A HuffmanNode cannot have a frequency less than one.");
		} else {
			this.frequency = frequency;
		}
	}
	
	public int getFreq() {
		return this.frequency;
	}
	
	/**
	 * Compares two HuffmanNodes by their frequencies
	 * @param other The other HuffmanNode to compare against
	 * @return -1, if this HuffmanNode has a smaller frequency than other HuffmanNode;
	 * 	       0, if their frequencies are the same;
	 * 	       1, otherwise.
	 */
	public int compareTo(HuffmanNode other) {
		if (this.frequency < other.frequency) return -1;
		if (this.frequency > other.frequency) return 1;
		return 0;
	}
	
}
