package com.company;

import collections.huffman.trees.HuffmanNode;
import formats.Utils;

import java.util.*;

public class HuffmanEnc {
    public String encodedData;
    public HuffmanNode root;
    public short num_entries;
    public byte paddedZeros;
    public Map<Character,String> hashTable;
    public int [] freq;

      public HuffmanEnc(String encodedData,int[] freq) {
        this.encodedData = encodedData;
        this.root=HuffmanEnc.buildHuffmanTree(freq);
    }
    public HuffmanEnc(){}

    public HuffmanEnc(int[] freq,byte[] encodedData,int stringLength){
        this.freq=freq;
        this.root=buildHuffmanTree(freq);
        this.encodedData= Utils.getBytesAsString(encodedData,stringLength);

    }

    public void compress(String data){
        this.freq=buildFreqArray(data);
        this.root=buildHuffmanTree(freq);
        buildCodes();
        this.encodedData=generateEncodedData(data);
        this.paddedZeros = (byte) (8* Utils.getStringAsBytes(this.encodedData).length- this.encodedData.length());

    }

    public String decompress (String encodedData){
        final StringBuilder resultBuilder=new StringBuilder();
        HuffmanNode current=this.root;
        for(int i =0 ;i<encodedData.length();){
            while(!current.isLeaf()){
                char bit=encodedData.charAt(i);
                if (bit == '1')
                    current=current.rightChild;
                else if (bit=='0')
                    current=current.leftChild;
                else
                    throw new IllegalArgumentException("Invalid bit: "+bit);

                i++;
            }
            resultBuilder.append(current.data);
            current=this.root;
        }
        return resultBuilder.toString();
    }

    private void buildCodes() {
        this.hashTable= new HashMap<>();
        buildCodesHelper(this.root,"");
    }

    private void buildCodesHelper(HuffmanNode root, String s) {
        if(!root.isLeaf()){
            buildCodesHelper(root.leftChild,s+'0');
            buildCodesHelper(root.rightChild,s+'1');
        }
        else{
            this.num_entries++;
            this.hashTable.put(root.data,s);
        }
    }

    public static HuffmanNode buildHuffmanTree(int[] freq) {
        final PriorityQueue<HuffmanNode> pq=new PriorityQueue<>();
        for(char i=0 ;i<freq.length;i++){
            if(freq[i]>0){
                pq.add(new HuffmanNode(i,freq[i],null,null));

            }
        }
        while (pq.size()>1){
            final HuffmanNode left = pq.poll();
            final HuffmanNode right=pq.poll();
            assert right != null;
            final HuffmanNode parent= new HuffmanNode('\0', (short)(left.frequency+ right.frequency), left,right );
            pq.add(parent);

        }
        return pq.poll();
    }

    private static int[] buildFreqArray(String data) {
        int[] freq= new int[256];
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
}
