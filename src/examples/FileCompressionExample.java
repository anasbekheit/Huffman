package examples;

import files.HuffmanFileUtils;

import java.nio.file.Paths;

public class FileCompressionExample {
    public static void main(String[] args){
        try {

            HuffmanFileUtils.compressFolder(
                    "D:\\Users\\polit\\Projects\\Java\\University\\Algorithms\\Huffman\\testfldr"
                    , Paths.get("D:\\Users\\polit\\Projects\\Java\\University\\Algorithms\\Huffman\\testfldr"));



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
