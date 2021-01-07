package examples;

import utilities.HuffmanFileUtils;

import java.io.File;
import java.nio.file.Paths;

public class FileCompressionExample {
    public static void main(String[] args){
        try {

            HuffmanFileUtils.compressFile(
                    new File("D:\\Users\\polit\\Projects\\Java\\University\\Algorithms\\Huffman\\TestFldr\\a.txt")
                    , Paths.get("D:\\Users\\polit\\Projects\\Java\\University\\Algorithms\\Huffman\\TestFldr"));



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
