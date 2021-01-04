package RandomTests;

import files.HuffmanUtils;

public class FolderCompressionTest {
    public static void main(String[] args){
        try {
            HuffmanUtils.compressFolder("D:\\Users\\polit\\Projects\\Java\\University\\Algorithms\\Huffman\\testfldr");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
