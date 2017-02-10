package huffman;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.PriorityQueue;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Multiset;

public class HuffmanEncoder {
	
	
	/**
	 * Indicates whether or not this Huffman tree encodes a trivial one character alphabet.
	 */
	private boolean hasTrivialTree = false;
	
	/**
	 * The root (or head) node of the (binary) Huffman Tree.
	 */
	private HuffmanInternalNode root;
	
	private BiMap<Character, String> codeMapping = HashBiMap.create();
	
	
	/**
	 * Constructs a Huffman code for the data contained in a non-empty  
	 * character stream.
	 * 
	 * @param sourceData - The character stream that we wish to compress.
	 * @throws IOException, IllegalArgumentException 
	 */
	public HuffmanEncoder(BufferedReader sourceData) throws IOException {
		
		Multiset<Character> charCounts = getCharFrequencies(sourceData);
		verifyValidAlphabet(charCounts);
		initiateTree(charCounts);
		
		
		traverseTree(this.root, "");
		System.out.println("String Representation of the Binary Tree:");
		printTree(this.root);
	}
	
	/**
	 * Constructs a Huffman code for a given (non-empty) alphabet with frequencies associated
	 * with each character.
	 * 
	 * @param characterCounts
	 */
	public HuffmanEncoder(Multiset<Character> characterCounts) {
		verifyValidAlphabet(characterCounts);
		initiateTree(characterCounts);
	}
	
	private void verifyValidAlphabet(Multiset<Character> characterCounts) {
		if (characterCounts == null) {
			throw new IllegalArgumentException("Cannot supply a null value for characterCounts.");
		} else if (characterCounts.size() == 0) {
			throw new IllegalArgumentException("Cannot compress an empty alphabet.");
		}
	}
	
	
	private void initiateTree(Multiset<Character> characterCounts) {
		if (characterCounts.size() == 1) {
			buildTrivialTree(characterCounts);
		} else {
			buildTree(characterCounts);
		}
	}
	
	private void buildTrivialTree(Multiset<Character> characterCounts) {
		HuffmanLeaf soleLeaf = null;
		
		for (char observedCharacter : characterCounts.elementSet()) {
			soleLeaf = new HuffmanLeaf(observedCharacter, characterCounts.count(observedCharacter));
		}
		
		this.root = new HuffmanInternalNode(soleLeaf);
		this.hasTrivialTree = true;
		
	}
		
	private void buildTree(Multiset<Character> characterCounts) {
		PriorityQueue<HuffmanNode> huffmanTree = new PriorityQueue<HuffmanNode>();
		
		//We first populate our Huffman Tree with singleton elements.
		for (char observedCharacter : characterCounts.elementSet()) {
			huffmanTree.add(new HuffmanLeaf(observedCharacter, characterCounts.count(observedCharacter)));
		}
		
		//Now, we apply the algorithm.
		while(huffmanTree.size() > 1) {
			HuffmanNode smallestOccurringNode = huffmanTree.poll();
			HuffmanNode nextSmallestOccurringNode = huffmanTree.poll();
			huffmanTree.offer(new HuffmanInternalNode(smallestOccurringNode, nextSmallestOccurringNode));
		}
		
		this.root = (HuffmanInternalNode) huffmanTree.peek();
		System.out.println("OK.");
	}
	
	private void traverseTree(HuffmanNode node, String bitPattern){
		if (node instanceof HuffmanInternalNode) {
			traverseTree( ((HuffmanInternalNode) node).getLeftChild(), bitPattern + "0");
			traverseTree( ((HuffmanInternalNode) node).getRightChild(), bitPattern + "1");
		} else if (node instanceof HuffmanLeaf) {
			System.out.println("Char: " + ((HuffmanLeaf) node).getChar() + " Bit Pattern: " + bitPattern);
		}
	}
	
	private void populateCodeTable(HuffmanNode node, StringBuffer bitPattern){
		if (node instanceof HuffmanInternalNode) {
			populateCodeTable( ((HuffmanInternalNode) node).getLeftChild(), bitPattern.append('0'));
			populateCodeTable( ((HuffmanInternalNode) node).getRightChild(), bitPattern.append('1'));
		} else if (node instanceof HuffmanLeaf) {
			this.codeMapping.put(((HuffmanLeaf) node).getChar(), bitPattern.toString());
		}
	}
	
	private void constructCodeTable(){
		if (hasTrivialTree) {
			char singleOccuringCharacter = ((HuffmanLeaf) this.root.getLeftChild()).getChar();
			this.codeMapping.put(singleOccuringCharacter, "0");
		} else {
			populateCodeTable(this.root, new StringBuffer());
		}
	}
	
	private void getCodeTable(){ }
	
	private void printTree(HuffmanNode node){
		if (node instanceof HuffmanInternalNode) {
			HuffmanInternalNode traversingNode = (HuffmanInternalNode) node;
			System.out.print(" (");
			printTree(traversingNode.getLeftChild());
			System.out.print(" ");
			printTree(traversingNode.getRightChild());
			System.out.print(") ");
		} else if (node instanceof HuffmanLeaf) {
			System.out.print("'" + ((HuffmanLeaf) node).getChar() + "'");
		} else {
			System.out.print("#");
		}
	}
	
	private StringBuffer printTreeTwo(HuffmanNode node, StringBuffer treeString){
		if (node instanceof HuffmanInternalNode) {
			HuffmanInternalNode traversingNode = (HuffmanInternalNode) node;
			treeString.append(" (");
			treeString.append(traversingNode.getLeftChild());
			treeString.append(" ");
			treeString.append(traversingNode.getRightChild());
			treeString.append(") ");
		} else if (node instanceof HuffmanLeaf) {
			treeString.append("'" + ((HuffmanLeaf) node).getChar() + "'");
		} else {
			treeString.append("#");
		}
		
		return treeString;
	}
	
	public String toString() {
		String tree = null;
		if (hasTrivialTree) {
			tree = "('" + ((HuffmanLeaf) this.root.getLeftChild()).getChar() + "' #)";
		} else {
			tree = printTreeTwo(this.root, new StringBuffer()).toString();
		}
		return tree;
	}
	
	private Multiset<Character> getCharFrequencies(BufferedReader sourceData) throws IOException {
		Multiset<Character> charCounts = HashMultiset.create();
			int currentChar = sourceData.read();
			while (currentChar != -1) {
				charCounts.add((char) currentChar);
				currentChar = sourceData.read();
			}
		return charCounts;
	}
	
	public static void main(String[] args) throws IOException {
		String source = "mississippi";
		StringReader sourceReader = new StringReader(source);
		BufferedReader testBuffer = new BufferedReader(sourceReader);
		HuffmanEncoder hotdog = new HuffmanEncoder(testBuffer);
		System.out.println("Done.");
	}
	
}
