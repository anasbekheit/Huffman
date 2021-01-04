package formats;

import collections.extended.ExBitSet;
import collections.huffman.trees.HuffmanNode;
import collections.huffman.trees.HuffmanTree;
import io.vavr.Tuple2;

public class Main {
    public static void main(String[]args){
        byte[] ab2  = new byte[]{0,0,0,'a',0,0,'b','c',0,0,'d','e','f','g','h'};
        //byte[] abc  = new byte[]{0,'f',0,0,0,'c','d',0,'e','a','b'};
        HuffmanTree tree = new HuffmanTree();

        HuffmanNode root = tree.constructBt(ab2, ab2.length);

        Tuple2<ExBitSet,Integer> res = tree.serializeLvlOrder();
        ExBitSet bs = res._1;
        Integer size = res._2;

        for (int i = 0; i < size; i++) {
            System.out.print(bs.get(i)?"1":"0");
        }
    }
}
