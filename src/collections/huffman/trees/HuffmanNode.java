package collections.huffman.trees;


public class HuffmanNode extends    AbstractNode<HuffmanNode>
                               implements Comparable<HuffmanNode>    {

    public NodeType type = NodeType.HuffNode;
    private int freq = 0;


    public HuffmanNode(char item)
    {
        super(item);
        if((byte)item !=0)
            type = NodeType.CharacterNode;

        left = right = null;
    }

    public HuffmanNode(char item,
                       int i,
                       HuffmanNode left,
                       HuffmanNode right) {
        super(item);
        if((byte)item !=0)
            type = NodeType.CharacterNode;
        this.freq =i;
        this.left =left;
        this.right =right;
    }



    int count() {
        HuffmanNode right = getRight();
        HuffmanNode left = getLeft();
        int c = 1;
        if ( right != null ) c += right.count();
        if ( left != null ) c += left.count();
        return c;
    }


    public boolean isLeaf(){
        return this.left==null && this.right==null;
    }

    public int getFreq() {
        return freq;
    }

    public void setFreq(int freq) {
        this.freq = freq;
    }

    @Override
    public int compareTo(HuffmanNode o) {
        int res     =           Integer.compare(this.freq,o.freq);
        return res != 0 ? res : Integer.compare(this.data,o.data);
    }
}
