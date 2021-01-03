package RandomTests;

import files.huffmanfile.HuffmanFile;

import java.io.FileNotFoundException;

public class HuffmanFileDecodeTest {
    public static void main(String[] args){
        try {
            String decoded  = HuffmanFile.fromFile("D:/Users/polit/Projects/Java/University/Algorithms/HuffmanCoding/src/test/resources/myfile.out").data.get();
            System.out.println(decoded);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
