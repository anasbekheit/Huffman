package collections.huffman.trees;


public class SimpleHuffmanNode extends AbstractNode<SimpleHuffmanNode> {
    public NodeType type = NodeType.HuffNode;

    public SimpleHuffmanNode(char item)
    {
        super(item);
        if((byte)item !=0)
            type = NodeType.CharacterNode;

        left = right = null;
    }

}
