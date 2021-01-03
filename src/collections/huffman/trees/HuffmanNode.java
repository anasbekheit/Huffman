package collections.huffman.trees;

public class HuffmanNode extends AbstractNode<HuffmanNode> implements  Comparable<HuffmanNode> {
    public final int frequency;
    public final HuffmanNode leftChild,rightChild;

    public HuffmanNode(char character,
                       int frequency,
                       HuffmanNode leftChild,
                       HuffmanNode rightChild) {
        super(character);
        this.frequency = frequency;
        this.leftChild=leftChild;
        this.rightChild= rightChild;
    }
    public boolean isLeaf(){
        return this.leftChild==null && this.rightChild==null;
    }


    @Override
    public int compareTo(final HuffmanNode o) {
        final int result=Integer.compare(this.frequency,o.frequency);

        return result!=0? result:Integer.compare(this.data,o.data);
    }
}
