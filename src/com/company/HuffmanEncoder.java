package com.company;

import collections.huffman.trees.HuffmanNode;

import java.util.*;

import static formats.StringUtils.*;

public class HuffmanEncoder {

    public HuffmanEncodedResult compress(final String data) {
        final int[] freq = buildFreqArray(data);
        final HuffmanNode root = buildHuffmanTree(freq);
        final Map < Character, String > codes = buildCodes(root);

        return new HuffmanEncodedResult(generateEncodedData(data, codes), root);
    }

    public String decompress(final HuffmanEncodedResult result) {
        final StringBuilder resultBuilder = new StringBuilder();
        HuffmanNode current = result.root;
        for (int i = 0; i < result.encodedData.length();) {
            while (!current.isLeaf()) {
                char bit = result.encodedData.charAt(i);
                if (bit == '1') {
                    current = current.rightChild;

                } else if (bit == '0') {
                    current = current.leftChild;

                } else {
                    throw new IllegalArgumentException("Invalid bit: " + bit);
                }
                i++;
            }
            resultBuilder.append(current.data);
            current = result.root;
        }
        return resultBuilder.toString();
    }

    public static LinkedList <HuffmanNode> getNodes(HuffmanEncodedResult huffmanEncodedResult) {
        LinkedList <HuffmanNode> nodes = new LinkedList < > ();
        getNodesHelper(huffmanEncodedResult.root, nodes);
        return nodes;
    }





    private static String generateEncodedData(final String data, final Map < Character, String > codes) {
        final StringBuilder builder = new StringBuilder();
        for (final char character: data.toCharArray()) {
            builder.append(codes.get(character));
        }
        return builder.toString();
    }

    private static int[] buildFreqArray(final String data) {
        final int[] freq = new int[256];
        for (char character: data.toCharArray()) {
            freq[character]++;
        }
        return freq;
    }

    private static HuffmanNode buildHuffmanTree(int[] freq) {
        final PriorityQueue <HuffmanNode> pq = new PriorityQueue < > ();
        for (char i = 0; i < freq.length; i++) {
            if (freq[i] > 0) {
                pq.add(new HuffmanNode(i, freq[i], null, null));
            }
        }
        while (pq.size() > 1) {
            final HuffmanNode left = pq.poll();
            final HuffmanNode right = pq.poll();
            final HuffmanNode parent = new HuffmanNode('\0', left.frequency + right.frequency, left, right);
            pq.add(parent);

        }
        return pq.poll();
    }

    private static Map<Character,String> buildCodes(final HuffmanNode root) {
        final Map < Character, String > hashTable = new HashMap < > ();
        buildCodesHelper(root, "", hashTable);
        return hashTable;

    }

    private static void buildCodesHelper(final HuffmanNode root, final String s, final Map <Character,String> hashMap) {
        if (!root.isLeaf()) {
            buildCodesHelper(root.leftChild, s + '0', hashMap);
            buildCodesHelper(root.rightChild, s + '1', hashMap);
        } else {
            hashMap.put(root.data, s);
        }

    }

    private static void getNodesHelper(HuffmanNode root, LinkedList <HuffmanNode> nodes) {
        if (root.isLeaf()) {
            nodes.add(root);
        }
        getNodesHelper(root.leftChild, nodes);
        getNodesHelper(root.rightChild, nodes);

    }



    public static void main(String[] args) {

        String test = "abbccccddddddeeeeeeeeffffffffffffffffffgggggggggggggggggggggggggggggggggggggghhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii";
        HuffmanEncoder encoder = new HuffmanEncoder();
        HuffmanEncodedResult encodedResult = encoder.compress(test);

        System.out.println("Encoded Message  : " + encodedResult.encodedData);
        System.out.println("Byte array MSG   : " + getBytesAsString(getStringAsBytes(encodedResult.encodedData, 0), encodedResult.encodedData.length()));
        System.out.print("Message In Decima: ");
        for (byte byt: getStringAsBytes(encodedResult.encodedData, 0)) {
            System.out.print(reverseString(Integer.toBinaryString((byt & 0xFF) + 0x100).substring(1)));


        }
        System.out.println("\nENCODED LENGTH: " + encodedResult.encodedData.length() + " \nBytearr LENGTH: " + getBytesAsString(getStringAsBytes(encodedResult.encodedData, 0), encodedResult.encodedData.length()).length());
        System.out.println("\nDecoded Message: " + encoder.decompress(encodedResult));
        System.out.println("Previous Length in Bytes: " + test.length());
        System.out.println("Cuurent Length in Bytes: " + getStringAsBytes(encodedResult.encodedData, 0).length);
        System.out.println("Appended Zeros: " + (8 * getStringAsBytes(encodedResult.encodedData, 0).length - encodedResult.encodedData.length()));
        HuffmanEncodedResult trial = new HuffmanEncodedResult(getBytesAsString(getStringAsBytes(encodedResult.encodedData, 0), encodedResult.encodedData.length()), encodedResult.root);
        System.out.println("TRIAL Test Print: " + encoder.decompress(trial));

    }

}