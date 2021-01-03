package RandomTests;

import files.huffmanfile.HuffmanFile;

import java.io.FileNotFoundException;

public class HuffmanFileDecodeTest {
    public static void main(String[] args){
        try {
            //HuffmanFile[]
            HuffmanFile hf  = HuffmanFile.fromFile("D:/Users/polit/Projects/Java/University/Algorithms/HuffmanCoding/src/test/resources/myfile.out");
            //System.out.println(hf.data.get());
            for (HuffmanFile h:
                 hf.data.getLeft()) {
                System.out.println(h.data.get());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
