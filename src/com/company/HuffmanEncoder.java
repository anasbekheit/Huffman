package com.company;

import java.io.File;
import org.apache.commons.io.FileUtils;
import java.io.IOException;
import java.util.*;

public class HuffmanEncoder {


  /***DONE***/  public HuffmanEncodedResult compress (final String data){
        final int[] freq=buildFreqArray(data);
        final Node root=buildHuffmanTree(freq);
        final Map<Character,String> codes=buildCodes(root);

    return new HuffmanEncodedResult(generateEncodedData(data,codes),root);
    }

 /***DONE***/   private static String generateEncodedData(final String data,final Map<Character,String>codes){
        final StringBuilder builder= new StringBuilder();
        for(final char character:data.toCharArray()){
            builder.append(codes.get(character));
        }
        return builder.toString();
    }
   /*** DONE***/ public String decompress (final HuffmanEncodedResult result){
        final StringBuilder resultBuilder=new StringBuilder();
        Node current= result.root;
        for(int i =0 ;i<result.encodedData.length();){
            while(!current.isLeaf()){
                char bit=result.encodedData.charAt(i);
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
            current=result.root;
        }
        return resultBuilder.toString();
    }


    static class HuffmanEncodedResult{
        final String encodedData;
        final Node root;
    HuffmanEncodedResult(final String encodedData,final Node root){
        this.encodedData=encodedData;
        this.root=root;
    }

    }

    /*** DONE***/ static class Node implements Comparable<Node>{
         final char character;
         final int frequency;
         final Node leftChild,rightChild;

        Node(char character, int frequency,Node leftChild,Node rightChild) {
            this.character = character;
            this.frequency = frequency;
            this.leftChild=leftChild;
            this.rightChild= rightChild;
        }
        boolean isLeaf(){
            return this.leftChild==null && this.rightChild==null;
        }


        @Override
        public int compareTo(final Node o) {
            final int result=Integer.compare(this.frequency,o.frequency);
            return result!=0? result:Integer.compare(this.character,o.character);
        }
    }

   /***DONE***/ private static int [] buildFreqArray(final String data){
        final int[] freq=new int[256];
        for(char character :data.toCharArray()){
            freq[character]++;
        }
        return freq;
    }
    private static Node buildHuffmanTree(int[] freq){
        final PriorityQueue<Node> pq=new PriorityQueue<>();
        for(char i=0 ;i<freq.length;i++){
            if(freq[i]>0){
            pq.add(new Node(i,freq[i],null,null));
            }
        }
        while (pq.size()>1){
            final Node left = pq.poll();
            final Node right=pq.poll();
        final Node parent= new Node('\0', left.frequency+ right.frequency,left,right );
        pq.add(parent);

        }
        return pq.poll();
    }

   /***DONE***/ private static Map<Character,String> buildCodes(final Node root){
        final Map<Character,String> hashTable= new HashMap<>();
        buildCodesHelper(root,"",hashTable);
        return hashTable;

    }

    /***DONE***/private static void buildCodesHelper(final Node root,final String s,final Map<Character,String> hashMap){
        if(!root.isLeaf()){
            buildCodesHelper(root.leftChild,s+'0',hashMap);
            buildCodesHelper(root.rightChild,s+'1',hashMap);
        }
        else{
            hashMap.put(root.character,s);
        }

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
    public static String reverseString(String str){
        StringBuilder sb=new StringBuilder(str);
        sb.reverse();
        return sb.toString();
    }
    public static LinkedList<Node> getNodes(HuffmanEncodedResult huffmanEncodedResult){
        LinkedList<Node> nodes= new LinkedList<>();
        getNodesHelper(huffmanEncodedResult.root,nodes);
        return nodes;
    }

    private static void getNodesHelper(Node root, LinkedList<Node> nodes) {
        if(root.isLeaf()){
            nodes.add(root);
        }
        getNodesHelper(root.leftChild,nodes);
        getNodesHelper(root.rightChild,nodes);

    }

    public static void main(String[] args){

       String test="abbccccddddddeeeeeeeeffffffffffffffffffgggggggggggggggggggggggggggggggggggggghhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii";
        HuffmanEncoder encoder=new HuffmanEncoder();
        HuffmanEncodedResult encodedResult=encoder.compress(test);

        System.out.println("Encoded Message  : "+encodedResult.encodedData);
        System.out.println("Byte array MSG   : "+encoder.getBytesAsString(encoder.getStringAsBytes(encodedResult.encodedData),encodedResult.encodedData.length()));
        System.out.print("Message In Decima: ");
        for(byte byt : encoder.getStringAsBytes(encodedResult.encodedData)){
            System.out.print(reverseString(Integer.toBinaryString((byt & 0xFF) + 0x100).substring(1)));


        }
        System.out.println("\nENCODED LENGTH: "+encodedResult.encodedData.length()+" \nBytearr LENGTH: "+encoder.getBytesAsString(encoder.getStringAsBytes(encodedResult.encodedData),encodedResult.encodedData.length()).length());
      //  System.out.println("\nByte As String: "+encoder.getBytesAsString(encoder.getStringAsBytes(encodedResult.encodedData)));
        System.out.println("\nDecoded Message: "+encoder.decompress(encodedResult));
        System.out.println("Previous Length in Bytes: "+test.length());
        System.out.println("Cuurent Length in Bytes: "+encoder.getStringAsBytes(encodedResult.encodedData).length);
        System.out.println("Appended Zeros: "+(8*encoder.getStringAsBytes(encodedResult.encodedData).length-encodedResult.encodedData.length()));
        HuffmanEncodedResult trial= new HuffmanEncodedResult(encoder.getBytesAsString(encoder.getStringAsBytes(encodedResult.encodedData),encodedResult.encodedData.length()), encodedResult.root);
        System.out.println("TRIAL Test Print: "+encoder.decompress(trial));
       // HuffmanEncoder encoder=new HuffmanEncoder();
       // byte[] bytes= new byte[1];
       // String test=encoder.getBytesAsString(bytes);
       // System.out.println(test);

   /*     byte[] testawy = new byte[1];

        {
            try {
                testawy = FileUtils.readFileToByteArray(new File("test.txt"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }




    */
    }
    
}
/*** REMAINING : SAVE & READ FROM FILES(padded zeros,huffman tree)
                ACCOUNTING FOR APPENDED ZEROS IN DECOMPRESSING
 ***/