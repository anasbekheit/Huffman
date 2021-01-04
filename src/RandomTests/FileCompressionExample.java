package RandomTests;

import files.HuffmanUtils;

import java.io.File;

public class FileCompressionExample {
    public static void main(String[] args){
        try {
            HuffmanUtils.compressFile(
                    new File("D:\\Users\\polit\\Projects\\Java\\University\\Algorithms\\Huffman\\testfldr\\a.txt")
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
