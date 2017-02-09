package huffman;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

public class HuffmanEncoder {
	private BufferedReader sourceData;
	private HuffmanInternalNode root;
	
	public HuffmanEncoder(BufferedReader sourceData) {
		this.sourceData = sourceData;
		buildTree(getCharFrequencies());
		traverseTree(this.root, "");
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
	
	
	private Multiset<Character> getCharFrequencies() {

		Multiset<Character> charCounts = HashMultiset.create();
		try {
			int currentChar = this.sourceData.read();
			while (currentChar != -1) {
				charCounts.add((char) currentChar);
				currentChar = this.sourceData.read();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return charCounts;
	}
	
	public static void main(String[] args) {
		String source = "aaaaaaaaaaaaaaaaaaaaaaaabbbbbbbbbbbbccccccccccddddddddeeeeeeee";
		StringReader sourceReader = new StringReader(source);
		BufferedReader testBuffer = new BufferedReader(sourceReader);
		HuffmanEncoder hotdog = new HuffmanEncoder(testBuffer);
		System.out.println("Done.");
	}
	
}
