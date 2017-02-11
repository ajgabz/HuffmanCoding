package huffman;

public abstract class HuffmanNode implements Comparable<HuffmanNode> {
	
	/**
	 * The character frequency (or number of occurrences) associated with this node.
	 */
	private final int frequency;
	
	/**
	 * Constructs a new HuffmanNode with an associated positive (non-zero) frequency
	 * @param frequency
	 */
	public HuffmanNode(int frequency) {
		if (frequency < 1) {
			throw new IllegalArgumentException("A HuffmanNode cannot have a frequency less than one.");
		} else {
			this.frequency = frequency;
		}
	}
	
	/**
	 * Returns the frequency associated with this HuffmanNode
	 * @return frequency, an positive integer
	 */
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
	
	/**
	 * The abstract method for node printing
	 */
	public abstract String toString();
	
}
