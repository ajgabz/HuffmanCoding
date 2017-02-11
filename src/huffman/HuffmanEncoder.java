package huffman;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.PriorityQueue;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

/**
 * The HuffmanEncoder class is a Singleton factory that produces Huffman Trees
 * for Unicode character data.
 * 
 * @author aarongaba
 *
 */
public class HuffmanEncoder {
	
	private static HuffmanEncoder encoder = new HuffmanEncoder();
	
	//Private constructor prevents any other class from instantiating.
	private HuffmanEncoder() { }
	
	/**
	 * Static 'instance' method
	 * @return
	 */
	public static HuffmanEncoder getEncoder() {
		return encoder;
	}
	
	/**
	 * Constructs a Huffman tree for the data contained in a non-empty character stream.
	 * 
	 * @param sourceData - The character stream that we wish to encode
	 * @return huffTree - A HuffmanInternalNode that marks the root node of the constructed Huffman tree.
	 * @throws IOException, IllegalArgumentException
	 */
	public HuffmanInternalNode encodeData(BufferedReader sourceData) throws IOException {
		//Produce a Multiset comprising of the frequency of each character in the char stream
		Multiset<Character> charCounts = getCharFrequencies(sourceData);
		
		//Check to see a valid character-frequency Multiset has been created
		verifyValidAlphabet(charCounts);
		
		//If the JVM has reached this point, meaning that it is a valid alphabet,
		//then construct the tree.
		return constructTree(charCounts);
	}
	
	/**
	 * Constructs a Huffman tree for a given (non-empty) alphabet with frequencies associated
	 * with each character.
	 * 
	 * @param characterCounts - A Multiset holding an alphabet to be encoded, along with
	 * the frequencies of each letter.
	 * 
	 * @return huffTree - A HuffmanInternalNode that marks the root node of the constructed Huffman tree.
	 * @throws IllegalArgumentException
	 */
	public HuffmanInternalNode encodeData(Multiset<Character> characterCounts) {
		//Check to see a valid character-frequency Multiset has been created
		verifyValidAlphabet(characterCounts);
		
		//If the JVM has reached this point, meaning that it is a valid alphabet,
		//then construct the tree.
		return constructTree(characterCounts);
	}
	
	/**
	 * Verifies that the Multiset representing an alphabet and its character frequencies
	 * is non-null and non-empty (i.e., contains at least one element).
	 * 
	 * @param characterCounts
	 * @throws IllegalArgumentException - If invalid alphabet, this exception is thrown.
	 */
	private void verifyValidAlphabet(Multiset<Character> characterCounts) {
		if (characterCounts == null) {
			throw new IllegalArgumentException("Cannot supply a null value for characterCounts.");
		} else if (characterCounts.size() == 0) {
			throw new IllegalArgumentException("Cannot compress an empty alphabet.");
		}
		// If we have reached this method without any exception being raised,
		// then we know that characterCounts is a non-null Multiset that contains at least
		// one element.
	}
	
	/**
	 * The parent method that constructs a Huffman Tree using the appropriate algorithm,
	 * which depends upon the length of the alphabet that we are encoding.
	 * 
	 * @param characterCounts
	 * @return constructedTree - A HuffmanInternalNode that marks the root node of the constructed Huffman tree.
	 */
	private HuffmanInternalNode constructTree(Multiset<Character> characterCounts) {
		//This method is only executed when we've ensured that characterCounts
		//is neither null or empty.  Thus, this Multiset contains at least one element.
		
		HuffmanInternalNode constructedTree = null;
		
		if (characterCounts.size() == 1) {
			constructedTree = buildTrivialTree(characterCounts);
		} else {
			constructedTree = buildTree(characterCounts); // characterCounts.size() > 1
		}
		
		return constructedTree;
	}
	
	/**
	 * Given a single-character alphabet, we run a special simplified version of the
	 * Huffman encoding algorithm to build this trivial tree.
	 * @param characterCounts
	 * @return - A HuffmanInternalNode that marks the root node of the constructed Huffman tree.
	 */
	private HuffmanInternalNode buildTrivialTree(Multiset<Character> characterCounts) {
		HuffmanLeaf soleLeaf = null;
		
		for (char observedCharacter : characterCounts.elementSet()) {
			soleLeaf = new HuffmanLeaf(observedCharacter, characterCounts.count(observedCharacter));
		}
		
		return new HuffmanInternalNode(soleLeaf);
	}
	
	/**
	 * Given a multi-character alphabet, we run the standard version of the Huffman encoding
	 * algorithm to build a full-fledge (or non-trivial) Huffman tree.
	 * 
	 * @param characterCounts
	 * @return A HuffmanInternalNode that marks the root node of the constructed Huffman tree.
	 */
	private HuffmanInternalNode buildTree(Multiset<Character> characterCounts) {
		//Create an empty Priority Queue
		PriorityQueue<HuffmanNode> huffmanTree = new PriorityQueue<HuffmanNode>();
		
		//We first populate our Huffman Tree with singleton elements.
		for (char observedCharacter : characterCounts.elementSet()) {
			huffmanTree.add(new HuffmanLeaf(observedCharacter, characterCounts.count(observedCharacter)));
		}
		
		//Now, we apply the algorithm.
		//Note that as a pre-condition for this method, characterCounts.size() > 1
		while(huffmanTree.size() > 1) {
			HuffmanNode smallestOccurringNode = huffmanTree.poll();
			HuffmanNode nextSmallestOccurringNode = huffmanTree.poll();
			huffmanTree.offer(new HuffmanInternalNode(smallestOccurringNode, nextSmallestOccurringNode));
		}
		
		//The last node in the priority queue is the root node to our Huffman tree.
		return (HuffmanInternalNode) huffmanTree.poll();
	}
	
	/**
	 * Constructs a Multiset comprising of this character data's alphabet and associated with
	 * each character is its frequency in the data.
	 * 
	 * Ex. "aaabbbbcc" becomes [('a',3),('b',4),('c',2)]
	 * 
	 * @param sourceData
	 * @return charCounts 
	 * @throws IOException
	 */
	private Multiset<Character> getCharFrequencies(BufferedReader sourceData) throws IOException {
		Multiset<Character> charCounts = HashMultiset.create();
		int currentChar = sourceData.read(); // Read first character in the buffer
		
		while (currentChar != -1) { // While currentChar is not EOF character 
			charCounts.add((char) currentChar); 
			currentChar = sourceData.read(); // Read next character in the buffer
		}
		
		sourceData.close(); // Close the reader
		return charCounts;
	}
	
}
