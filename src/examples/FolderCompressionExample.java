package examples;

import files.HuffmanFileUtils;

public class FolderCompressionExample {
    public static void main(String[] args){
        try {
            HuffmanFileUtils.compressFolder("D:\\Users\\polit\\Projects\\Java\\University\\Algorithms\\Huffman\\testfldr");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
