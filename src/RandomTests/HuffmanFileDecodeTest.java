package RandomTests;

import files.huffmanfile.HuffmanFile;

import java.io.FileNotFoundException;

public class HuffmanFileDecodeTest {
    public static void main(String[] args){
        try {
            //HuffmanFile[]
            HuffmanFile hf  = HuffmanFile.fromFile("D:/Users/polit/Projects/Java/University/Algorithms/HuffmanCoding/src/test/resources/myfile1.out");
            //System.out.println(hf.data.get());
            for (HuffmanFile h:
                 hf.getData().getLeft()) {
                System.out.println(h.getData().get());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
