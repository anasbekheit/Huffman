package collections.huffman.mappers;

import collections.huffman.trees.HuffmanNode;
import collections.huffman.trees.SimpleHuffmanNode;

public final class NodeMapper {
    public static SimpleHuffmanNode to(HuffmanNode node){
        if(node ==null)
            throw  new IllegalArgumentException("Cannot convert null to tree");

        SimpleHuffmanNode shn = new SimpleHuffmanNode(node.data);
        return shn;
    }
    public static HuffmanNode to(SimpleHuffmanNode  node) throws Exception {
        if(node ==null)
            throw  new IllegalArgumentException("Cannot convert null to tree");

        throw new Exception("Converter node implemented yet");
    }


}
