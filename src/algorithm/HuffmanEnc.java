package algorithm;

import collections.huffman.trees.HuffmanTree;
import collections.huffman.trees.NodeType;
import collections.huffman.trees.HuffmanNode;
import com.github.jinahya.bit.io.BitInput;
import utilities.Utils;

import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

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
        freq= new int[256];
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



    public void prettyPrint(){
        float compressionRatio = Arrays.stream(freq).sum()*8.0f/encodedData.length();

        StringBuilder  sb =  new StringBuilder("");

        Map<Integer, Integer> columnLengths = new HashMap<>();
        String[] c = {"Byte", "Code", "New Code"};


        for (Map.Entry<Character,String> en: hashTable.entrySet()) {
            for(int i = 0;i<3;i++) {
                Integer coli = columnLengths.getOrDefault(i,null);
                if (coli == null){
                    columnLengths.putIfAbsent(i, c[i].length());
                    coli = c[i].length();
                }
                int val = 0;

                switch (i){
                    case 0:
                        val =String.valueOf((byte) en.getKey().charValue()).length();
                        break;
                    case 1:
                        val =Utils.StrToBinStr(String.valueOf(en.getKey())).length();
                        break;
                    case 2:
                        val = en.getValue().length();
                        break;
                }
                if(val > coli)
                    columnLengths.put(i,val);
            }
        }

        columnLengths.forEach((key, value) -> sb.append("| %")
                                                .append(value)
                                                .append("s "));
        sb.append("|\n");


        System.out.printf("Compression Ratio: %.2f\n",compressionRatio);
        System.out.printf(sb.toString(),c[0],c[1],c[2]);

        hashTable.forEach((key, value) -> System.out.printf(
                sb.toString(),
                (byte) key.charValue(),
                Utils.StrToBinStr(String.valueOf(key)),
                value
        ));
    }
}
