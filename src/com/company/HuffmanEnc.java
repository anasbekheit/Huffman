package com.company;

import java.util.*;

public class HuffmanEnc {
      String encodedData;
      Node root;
      short num_entries;
      byte paddedZeros;
      Map<Character,String> hashTable;
      short [] freq;
    public HuffmanEnc(String encodedData,short[] freq) {
        this.encodedData = encodedData;
        this.root=HuffmanEnc.buildHuffmanTree(freq);
    }
    public HuffmanEnc(){}

    public void compress(String data){
        this.freq=buildFreqArray(data);
        this.root=buildHuffmanTree(freq);
        buildCodes();
        this.encodedData=generateEncodedData(data);
        this.paddedZeros= (byte) (8*this.getStringAsBytes(this.encodedData).length- this.encodedData.length());

    }
    public String decompress (String encodedData){
        final StringBuilder resultBuilder=new StringBuilder();
        Node current=this.root;
        for(int i =0 ;i<this.encodedData.length();){
            while(!current.isLeaf()){
                char bit=this.encodedData.charAt(i);
                if (bit == '1') {
                    current=current.rightChild;

                }else if (bit=='0'){
                    current=current.leftChild;

                }
                else{
                    throw new IllegalArgumentException("Invalid bit: "+bit);
                }
                i++;
            }
            resultBuilder.append(current.character);
            current=this.root;
        }
        return resultBuilder.toString();
    }

    private void buildCodes() {
        this.hashTable= new HashMap<>();
        buildCodesHelper(this.root,"");
    }

    private void buildCodesHelper(Node root, String s) {
        if(!root.isLeaf()){
            buildCodesHelper(root.leftChild,s+'0');
            buildCodesHelper(root.rightChild,s+'1');
        }
        else{
            this.num_entries++;
            this.hashTable.put(root.character,s);
        }
    }

    public static Node buildHuffmanTree(short[] freq) {
        final PriorityQueue<Node> pq=new PriorityQueue<>();
        for(char i=0 ;i<freq.length;i++){
            if(freq[i]>0){
                pq.add(new Node(i,freq[i],null,null));

            }
        }
        while (pq.size()>1){
            final Node left = pq.poll();
            final Node right=pq.poll();
            assert right != null;
            final Node parent= new Node('\0', (short)(left.frequency+ right.frequency), left,right );
            pq.add(parent);

        }
        return pq.poll();
    }

    private static short[] buildFreqArray(String data) {
        short[] freq= new short[256];
        for(char c :data.toCharArray()){
            freq[c]++;
        }
        return freq;
    }

    private String generateEncodedData(final String data){
        final StringBuilder builder= new StringBuilder();
        for(final char character:data.toCharArray()){
            builder.append(this.hashTable.get(character));
        }
        return builder.toString();
    }

    public byte[] getStringAsBytes(String encoded){
        BitSet bitSet = new BitSet(encoded.length());
        int bitcounter = 0;
        for(Character c : encoded.toCharArray()) {
            if(c.equals('1')) {
                bitSet.set(bitcounter);
            }
            bitcounter++;
        }
        return Arrays.copyOf(bitSet.toByteArray(),(bitcounter+7)/8);
    }

    public String getBytesAsString(byte[] bytes,int stringLength){
        BitSet set= BitSet.valueOf(bytes);
        String binaryString = "";
        for(int i = 0; i < (set.length()); i++) {
            if(set.get(i)) {
                binaryString += "1";
            } else {
                binaryString += "0";
            }
        }
        int completer= stringLength-set.length();
        for(int i=0;i<completer;i++){
            binaryString+="0";
        }
        return binaryString;
    }
   /* public Node[] getEntries(){
        Node[] nodes=new Node[num_entries];
        getEntriesHelper(this.root,nodes,0);
        return nodes;

    }
    private static int getEntriesHelper(Node root, Node[] nodes,int index) {


        if(root.isLeaf()){
            nodes[index++]=(root);
            return 1;

        }
       return getEntriesHelper(root.rightChild,nodes,index)+ getEntriesHelper(root.leftChild,nodes,index);


    }*/
}
