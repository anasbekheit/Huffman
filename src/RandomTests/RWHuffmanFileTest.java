package RandomTests;

import files.huffmanfile.HuffmanFile;

import java.io.IOException;

public class RWHuffmanFileTest {
    public static void main(String[] args){
        try {
            HuffmanFile hf;
            hf = HuffmanFile.fromFile("a.huff");

            System.out.println(hf.getData().get());
//            for (HuffmanFile h :
//                    hf.getData().getLeft()) {
//                System.out.println(h.getData().get());
//            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
