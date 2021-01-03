package collections.huffman.trees;

public final class TreeMapper {
    public static SimpleHuffmanTree to(HuffmanNode root){
        SimpleHuffmanTree  tree = new SimpleHuffmanTree();
        tree.setRoot(copyTree(root));
        return tree;
    }

    public static HuffmanNode to( SimpleHuffmanTree tree){
        throw new IllegalArgumentException("Not implemented yet");
    }

    private static SimpleHuffmanNode copyTree(HuffmanNode root)
    {
        if (root == null)
        {
            return null;
        }
        SimpleHuffmanNode newNode = new SimpleHuffmanNode(root.data);
        newNode.setLeft(copyTree(root.leftChild));
        newNode.setRight(copyTree(root.rightChild));
        return newNode;
    }

}
