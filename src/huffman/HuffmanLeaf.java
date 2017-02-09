package huffman;

public class HuffmanLeaf extends HuffmanNode {

	private char encodedCharacter;
	
	public HuffmanLeaf(char encodedCharacter, int frequency) {
		super(frequency);
		this.encodedCharacter = encodedCharacter;
	}
	
	public char getChar() {
		return this.encodedCharacter;
	}

}
