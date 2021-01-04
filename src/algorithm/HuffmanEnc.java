package algorithm;

import collections.huffman.trees.HuffmanTree;
import collections.huffman.trees.NodeType;
import collections.huffman.trees.HuffmanNode;
import com.github.jinahya.bit.io.BitInput;

import java.io.IOException;
import java.util.*;

public class HuffmanEnc {
    private String encodedData;
    private HuffmanTree huffmanTree;
    private Map<Character,String> hashTable;
    private int [] freq;


    public HuffmanEnc(){}


    public void compress(String data){
        this.setFreq(buildFreqArray(data));
        setHuffmanTree(new HuffmanTree());
        getHuffmanTree().setRoot(buildHuffmanTree());
        buildCodes();
        this.setEncodedData(generateEncodedData(data));
    }

    public void compress(HuffmanTree tree,String s){
        setHuffmanTree(tree);
        buildCodes();
        this.setEncodedData(generateEncodedData(s));
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
        for(char i = 0; i< getFreq().length; i++){
            if(getFreq()[i]>0){
                pq.add(new HuffmanNode(i, getFreq()[i],null,null));

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
        this.setHashTable(new HashMap<>());
        buildCodesHelper(getHuffmanTree().getRoot(),"");
    }

    private void buildCodesHelper(HuffmanNode root, String s) {
        if(!root.isLeaf()){
            buildCodesHelper(root.getLeft(),s+'0');
            buildCodesHelper(root.getRight(),s+'1');
        }
        else{
            this.getHashTable().put(root.data,s);
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
            builder.append(this.getHashTable().get(character));
        }
        return builder.toString();
    }

    public String getEncodedData() {
        return encodedData;
    }

    public void setEncodedData(String encodedData) {
        this.encodedData = encodedData;
    }

    public HuffmanTree getHuffmanTree() {
        return huffmanTree;
    }

    public void setHuffmanTree(HuffmanTree huffmanTree) {
        this.huffmanTree = huffmanTree;
    }


    public Map<Character, String> getHashTable() {
        return hashTable;
    }

    public void setHashTable(Map<Character, String> hashTable) {
        this.hashTable = hashTable;
    }

    public int[] getFreq() {
        return freq;
    }

    public void setFreq(int[] freq) {
        this.freq = freq;
    }
}
