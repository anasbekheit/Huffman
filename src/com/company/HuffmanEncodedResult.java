package com.company;

import collections.huffman.trees.HuffmanNode;

public class HuffmanEncodedResult {
    final String encodedData;
    final HuffmanNode root;

    HuffmanEncodedResult(final String encodedData, final HuffmanNode root) {
        this.encodedData = encodedData;
        this.root = root;
    }

}