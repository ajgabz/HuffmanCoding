package huffman;

/**
 * In a Huffman tree, the encoded characters are held as the leaves of the tree.
 * @author aarongaba
 *
 */
public class HuffmanLeaf extends HuffmanNode {

	/**
	 * The character encoded by this HuffmanNode (leaf).
	 */
	private final char encodedCharacter;
	
	/**
	 * Constructs a new leaf for a Huffman Tree
	 * @param encodedCharacter - The character encoded by the tree
	 * @param frequency - The frequency (or number of occurrences) associated with the character
	 */
	public HuffmanLeaf(char encodedCharacter, int frequency) {
		super(frequency);
		this.encodedCharacter = encodedCharacter;
	}
	
	/**
	 * Returns the character associated with this leaf node
	 * @return encodedCharacter
	 */
	public char getChar() {
		return this.encodedCharacter;
	}
	
	/**
	 * Produces a String representation of this leaf node
	 * The representation is as follows: (single quote) character (single quote)
	 * Ex: Leaf node encodes the character 'n', then the string representation is: 'n'
	 */
	public String toString() {
		String leafRepresentation = "'" + this.encodedCharacter + "'";
		return leafRepresentation;
	}

}
