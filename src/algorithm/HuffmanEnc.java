package algorithm;

import collections.huffman.trees.HuffmanTree;
import collections.huffman.trees.NodeType;
import collections.huffman.trees.HuffmanNode;
import com.github.jinahya.bit.io.BitInput;

import java.io.IOException;
import java.util.*;

public class HuffmanEnc {
    public String encodedData;
    public HuffmanTree huffmanTree;
    public short num_entries;
    public Map<Character,String> hashTable;
    public int [] freq;


    public HuffmanEnc(){}


    public void compress(String data){
        this.freq   = buildFreqArray(data);
        huffmanTree = new HuffmanTree();
        huffmanTree.setRoot(buildHuffmanTree());
        buildCodes();
        this.encodedData=generateEncodedData(data);
    }

    public void compress(HuffmanTree tree,String s){
        huffmanTree = tree;
        buildCodes();
        this.encodedData =generateEncodedData(s);
    }

    public String decompress(BitInput bitInput,
                             HuffmanNode hroot,
                             long nbits) throws IOException {

        StringBuilder resultBuilder=new StringBuilder();
        HuffmanNode current=hroot;

        for(int i =0 ;i<nbits;){
            while(current.type != NodeType.CharacterNode){
                boolean bit=bitInput.readBoolean();
                if (bit)
                    current=current.getRight();
                else current=current.getLeft();
                i++;
            }
            resultBuilder.append(current.data);
            current=hroot;
        }
        return resultBuilder.toString();
    }



    private  HuffmanNode buildHuffmanTree() {
         PriorityQueue<HuffmanNode> pq=new PriorityQueue<>();
        for(char i=0 ;i<freq.length;i++){
            if(freq[i]>0){
                pq.add(new HuffmanNode(i,freq[i],null,null));

            }
        }

        while (pq.size()>1){
            HuffmanNode left = pq.poll();
            HuffmanNode right=pq.poll();

            assert right != null;
            HuffmanNode parent= new HuffmanNode('\0',
                                                (left.getFreq()+ right.getFreq()),
                                                left, right );
            pq.add(parent);

        }
        return pq.poll();
    }

    private void buildCodes() {
        this.hashTable= new HashMap<>();
        buildCodesHelper(huffmanTree.getRoot(),"");
    }

    private void buildCodesHelper(HuffmanNode root, String s) {
        if(!root.isLeaf()){
            buildCodesHelper(root.getLeft(),s+'0');
            buildCodesHelper(root.getRight(),s+'1');
        }
        else{
            this.num_entries++;
            this.hashTable.put(root.data,s);
        }
    }

    private  int[] buildFreqArray(String data) {
        int[] freq= new int[256];
        for(char c :data.toCharArray()){
            freq[c]++;
        }
        return freq;
    }

    private String generateEncodedData( String data){
         StringBuilder builder= new StringBuilder();
        for( char character:data.toCharArray()){
            builder.append(this.hashTable.get(character));
        }
        return builder.toString();
    }
}
