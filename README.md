# HuffmanCoding
This is a partial implementation of the [Huffman compression algorithm](https://en.wikipedia.org/wiki/Huffman_coding) in Java.

The Java implementation, utilizing Google's Guava library, constructs a Huffman Tree for Unicode data.

The key file here is "HuffmanEncoder.java," which is a singleton factory that produces a Huffman tree.  It can either
produce a tree from a Multiset of character-frequencies or directly from a file or string, via Java's own BufferedReader.
