package files.huffmanfile;

import collections.huffman.trees.HuffmanTree;
import algorithm.HuffmanEnc;

import java.io.File;
import java.util.ArrayList;

public final class HuffmanHeaderBuilder {
    private long num_of_files;
    private String fileName;
    private boolean huff_char_flag;
    private long file_size;
    private HuffmanTree huffmanTree;
    private boolean isFile;

    HuffmanHeaderBuilder() {}

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
        huffmanHeader.setNum_of_nodes(huffmanTree == null ? 0 : huffmanTree.getNodeCount());
        huffmanHeader.setFile_size(file_size);
        huffmanHeader.setHuffmanTree(huffmanTree);
        return huffmanHeader;
    }

    public HuffmanHeaderBuilder fromFiles(ArrayList<HuffmanFile> hfs){
        isFile = false;
        num_of_files =hfs.size();
        huff_char_flag =true;
        file_size = hfs.stream()
                       .map(o->o.header.getFile_size())
                       .reduce(0L,  Long::sum);

        return this;
    }


    public HuffmanHeaderBuilder fromFile(File  file, HuffmanEnc encoder){
        isFile = true;
        num_of_files = 1;
        huff_char_flag = true;
        String[] temp =file.getName().split("\\.");
        fileName = temp[0];
        huffmanTree = encoder.getHuffmanTree();
        file_size = encoder.getEncodedData().length();
        return this;
    }
}
