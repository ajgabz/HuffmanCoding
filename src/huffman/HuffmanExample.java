package huffman;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

/**
 * This code is just a small example of the HuffmanEncoder.
 * @author aarongaba
 *
 */
public class HuffmanExample {

	public static void main(String[] args) throws IOException {
		//Construct our example data
		String source = "she sells seashells by the seashore";
		StringReader sourceReader = new StringReader(source);
		BufferedReader testBuffer = new BufferedReader(sourceReader);
		
		//Create our Huffman tree 
		HuffmanInternalNode huffTree = HuffmanEncoder.getEncoder().encodeData(testBuffer);
		
		//Print our Huffman tree to Std-Out
		System.out.println(huffTree.toString());

	}

}
