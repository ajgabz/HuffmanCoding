package huffman;

/**
 * In a Huffman tree, internal nodes never hold encoded characters themselves.
 * But rather, they hold references to other internal nodes or leaves (where the characters
 * are stored).
 * 
 * @author aarongaba
 *
 */
public class HuffmanInternalNode extends HuffmanNode {

	/**
	 * The child references of this internal node in a Huffman Tree.
	 */
	private final HuffmanNode leftChild;
	private final HuffmanNode rightChild;
	
	/**
	 * Constructs a typical internal node in a Huffman Tree,
	 * whereby a non-null left and right child are needed.
	 * 
	 * This node's frequency is the sum of the left and right child's respective frequencies.
	 * 
	 * @param left - The left child of this HuffmanInternalNode
	 * @param right - The right child of this HuffmanInternalNode
	 */
	public HuffmanInternalNode(HuffmanNode left, HuffmanNode right) {
		super(left.getFreq() + right.getFreq());
		this.leftChild = left;
		this.rightChild = right;
	}
	
	/**
	 * Constructs an atypical internal node in a Huffman Tree.
	 * This is a special constructor used solely for the creation 
	 * of a Huffman Tree encoding a one-character alphabet.
	 * 
	 * Its single child will be made its left child and the other child reference will be null.
	 * 
	 * @param node - The single (left) child associated with this HuffmanInternalNode
	 */
	public HuffmanInternalNode(HuffmanNode node) {
		super(node.getFreq());
		this.leftChild = node;
		this.rightChild = null;
	}
	
	/**
	 * @return the leftChild of this node
	 */
	public HuffmanNode getLeftChild() {
		return leftChild;
	}

	/**
	 * @return the rightChild of this node
	 */
	public HuffmanNode getRightChild() {
		return rightChild;
	}
	
	
	/**
	 * Produces a String representation of this internal node
	 * The representation is as follows: (open parenthesis) [LEFT CHILD] [RIGHT CHILD] (closed parenthesis)
	 * A null child is denoted by an octothrope (#) symbol
	 */
	public String toString() {
		//We use a StringBuffer to ease the cost of all the String concatenation we're doing.
		StringBuffer treeString = new StringBuffer();
		
		//Mark the start of this internal node
		treeString.append("("); 
		
		//Print the left child if it exists; if not, print the default null child symbol '#'
		if (this.leftChild != null) {
			treeString.append(this.leftChild.toString());
		} else {
			treeString.append("#");
		}
		
		//Space between children to ease reading
		treeString.append(" ");
		
		//Print the right child if it exists; if not, print the default null child symbol '#'
		if (this.leftChild != null) {
			treeString.append(this.rightChild.toString());
		} else {
			treeString.append("#");
		}
		
		//Mark the end of this internal node
		treeString.append(") ");
		
		return treeString.toString();
	}

}
