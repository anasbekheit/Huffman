package examples;

import utilities.HuffmanFileUtils;

public class FolderCompressionExample {
    public static void main(String[] args){
        try {
            HuffmanFileUtils.compressFolder("D:\\Users\\polit\\Projects\\Java\\University\\Algorithms\\Huffman\\TestFldr");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
