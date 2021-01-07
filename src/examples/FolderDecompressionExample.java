package examples;

import utilities.HuffmanFileUtils;

import java.io.File;
import java.io.IOException;

public class FolderDecompressionExample {
    public static void main(String[] args){
        try {
            HuffmanFileUtils.decompressionFromHuffmanFile(new File("D:\\Users\\polit\\Projects\\Java\\University\\Algorithms\\Huffman\\TestFldr\\TestFldr.huff"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}