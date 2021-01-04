package files.huffmanfile;

import collections.huffman.trees.HuffmanTree;

public final class HuffmanHeaderBuilder {
    private long num_of_files;
    private String fileName;
    private boolean huff_char_flag;
    private long file_size;
    private HuffmanTree huffmanTree;
    private boolean isFile;

    HuffmanHeaderBuilder() {
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

    public HuffmanHeaderBuilder withFile_size(long file_size) {
        this.file_size = file_size;
        return this;
    }

    public HuffmanHeaderBuilder withHuffmanTree(HuffmanTree huffmanTree) {
        this.huffmanTree = huffmanTree;
        return this;
    }


    public HuffmanHeaderBuilder asFile(boolean b) {
        this.isFile = b;
        return this;
    }

    public HuffmanHeader build() {
        HuffmanHeader huffmanHeader = new HuffmanHeader();
        huffmanHeader.setIsFile(isFile);
        huffmanHeader.setNum_of_files(num_of_files);
        huffmanHeader.setFileName(fileName);
        huffmanHeader.setHuff_char_flag(huff_char_flag);
        huffmanHeader.setNum_of_nodes(huffmanTree.nodeCount);
        huffmanHeader.setFile_size(file_size);
        huffmanHeader.setSimpleHuffmanTree(huffmanTree);
        return huffmanHeader;
    }
}
