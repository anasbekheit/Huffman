package files.huffmanfile;

import collections.huffman.trees.SimpleHuffmanTree;

public final class HuffmanHeaderBuilder {
    private long num_of_files;
    private String fileName;
    private boolean huff_char_flag;
    private int num_of_nodes;
    private long file_size;
    private SimpleHuffmanTree simpleHuffmanTree;

    private HuffmanHeaderBuilder() {
    }

    public static HuffmanHeaderBuilder aHuffmanHeader() {
        return new HuffmanHeaderBuilder();
    }

    public HuffmanHeaderBuilder withNum_of_files(long num_of_files) {
        this.num_of_files = num_of_files;
        return this;
    }

    public HuffmanHeaderBuilder withFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public HuffmanHeaderBuilder withHuff_char_flag(boolean huff_char_flag) {
        this.huff_char_flag = huff_char_flag;
        return this;
    }

    public HuffmanHeaderBuilder withNum_of_nodes(int num_of_nodes) {
        this.num_of_nodes = num_of_nodes;
        return this;
    }

    public HuffmanHeaderBuilder withFile_size(long file_size) {
        this.file_size = file_size;
        return this;
    }

    public HuffmanHeaderBuilder withSimpleHuffmanTree(SimpleHuffmanTree simpleHuffmanTree) {
        this.simpleHuffmanTree = simpleHuffmanTree;
        return this;
    }

    public HuffmanHeader build() {
        HuffmanHeader huffmanHeader = new HuffmanHeader();
        huffmanHeader.setNum_of_files(num_of_files);
        huffmanHeader.setFileName(fileName);
        huffmanHeader.setHuff_char_flag(huff_char_flag);
        huffmanHeader.setNum_of_nodes(num_of_nodes);
        huffmanHeader.setFile_size(file_size);
        huffmanHeader.setSimpleHuffmanTree(simpleHuffmanTree);
        return huffmanHeader;
    }
}
