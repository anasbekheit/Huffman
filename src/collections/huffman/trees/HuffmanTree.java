package collections.huffman.trees;

import collections.extended.ExBitSet;
import com.github.jinahya.bit.io.BitInput;
import io.vavr.Tuple2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import static formats.Utils.BitStr2BitSet;
import static formats.Utils.StrToBinStr;

public class HuffmanTree {

    static final int COUNT = 10;
    private HuffmanNode root;
    public int nodeCount;

    public HuffmanTree(BitInput bitInput,
                       int      numOfNodes,
                       int      lenofchar,
                       boolean  isSigned,
                       boolean  flag) throws IOException {
        root = null;
        HuffmanNode curr=null;
        int index=0;
        Queue<HuffmanNode> q=new LinkedList<>();

        for(int i=0;i<numOfNodes;i++){
            boolean readByte  = bitInput.readBoolean();
            byte val = (byte)(readByte ?1:0);

            if(readByte == flag) {
                val = bitInput.readByte(isSigned, lenofchar);
            }

            if(root ==null){
                root =new HuffmanNode((char)val);
                q.add(root);
                curr=q.peek();
                index=1;
            }else{
                if(index==1){
                    curr.setLeft(new HuffmanNode((char)val));
                    if(curr.getLeft().type !=NodeType.CharacterNode)
                        q.add(curr.getLeft());
                    index=2;

                }else if(index==2){
                    curr.setRight(new HuffmanNode((char)val));
                    if(curr.getRight().type !=NodeType.CharacterNode)
                        q.add(curr.getRight());
                    q.remove();
                    curr=q.peek();
                    index=1;
                }
            }
        }
    }

    public HuffmanTree(){}

    public HuffmanNode constructBt(byte[] arr, int n) {
        root = null;
        HuffmanNode curr=null;
        int index=0;
        Queue<HuffmanNode> q=new LinkedList<>();

        for(int i=0;i<n;i++){
            if(root ==null){
                root =new HuffmanNode((char)arr[i]);
                q.add(root);
                curr=q.peek();
                index=1;
            }else{
                if(index==1){
                    curr.setLeft(new HuffmanNode((char)arr[i]));
                    if(curr.getLeft().type !=NodeType.CharacterNode)
                        q.add(curr.getLeft());
                    index=2;

                }else if(index==2){
                    curr.setRight(new HuffmanNode((char)arr[i]));
                    if(curr.getRight().type !=NodeType.CharacterNode)
                        q.add(curr.getRight());
                    q.remove();
                    curr=q.peek();
                    index=1;
                }
            }
        }
        return root;
    }




    public HuffmanNode getRoot()
    {
        return root;
    }

    public void setRoot(HuffmanNode root) {
        if(root== null)
            throw new IllegalArgumentException("Cannot set Root Node to null");
        this.root = root;
    }


    void printInorder(HuffmanNode node) {
        if (node == null)
        {
            return;
        }
        printInorder(node.getLeft());
        if(node.type == NodeType.CharacterNode)
            System.out.print((char)node.data + " ");
        else
            System.out.print(" <HUFF> ");
        printInorder(node.getRight());
    }

    public String[] visitInOrder(){
        ArrayList<String> res  = new ArrayList<>();
        visitInOrderHelper(root,res);
        return res.toArray(new String[res.size()]);
    }

    private void visitInOrderHelper(HuffmanNode node,
                                    ArrayList<String> strlst) {
        if (node == null)
        {
            return;
        }
        visitInOrderHelper(node.getLeft(),strlst);
        if(node.type == NodeType.CharacterNode)
            strlst.add(node.data + "");
        else
            strlst.add("<HUFF>");
        visitInOrderHelper(node.getRight(),strlst);
    }



    public Tuple2<ExBitSet, Integer> serializeLvlOrder(){
        ExBitSet res = new ExBitSet();

        Queue<HuffmanNode> queue = new LinkedList<>();
        int nodeCount = 0;
        int[] len = new int[]{0};

        if(root == null)
            return new Tuple2<>(res,len[0]);

        queue.add(root);

        while (!queue.isEmpty()){
            HuffmanNode curr = queue.poll();
            nodeCount++;
            if (curr.type ==NodeType.HuffNode)
            {
                res.set(len[0]++,false);
            }
            else{
                res.set(len[0]++,true);

                int temp =len[0];
                res.appendNStartingFrom(
                        BitStr2BitSet(
                            StrToBinStr(String.valueOf(curr.data)),
                            len
                        ),temp,8
                );
            }

            if(curr.getLeft()!= null)
                queue.add(curr.getLeft());
            if(curr.getRight()!= null)
                queue.add(curr.getRight());
        }
        this.nodeCount = nodeCount;

        return new Tuple2<>(res,len[0]);
    }


    public void print2D()
    {
        print2DUtil(this.root, 0);
    }

    private void print2DUtil(HuffmanNode root, int space) {
        if (root == null) return;

        space += COUNT;
        print2DUtil(root.getRight(), space);

        System.out.print("\n");
        for (int i = COUNT; i < space; i++) System.out.print(" ");

        if(root.type ==NodeType.CharacterNode)
            System.out.print((char) root.data + " ");
        else
            System.out.print(" <HUFF> ");

        print2DUtil(root.getLeft(), space);
    }

}
