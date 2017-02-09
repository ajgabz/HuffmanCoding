package huffman;

public class HuffmanInternalNode extends HuffmanNode {

	private HuffmanNode leftChild;
	private HuffmanNode rightChild;
	
	public HuffmanInternalNode(HuffmanNode left, HuffmanNode right) {
		super(left.getFreq() + right.getFreq());
		this.leftChild = left;
		this.rightChild = right;
	}
	
	/**
	 * @return the leftChild
	 */
	public HuffmanNode getLeftChild() {
		return leftChild;
	}

	/**
	 * @return the rightChild
	 */
	public HuffmanNode getRightChild() {
		return rightChild;
	}

}
