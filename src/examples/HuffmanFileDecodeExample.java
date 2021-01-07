package examples;

import files.huffmanfile.HuffmanFile;

import java.io.FileNotFoundException;

public class HuffmanFileDecodeExample {
    public static void main(String[] args){
        try {
            //HuffmanFile[]
            HuffmanFile hf  = HuffmanFile.fromFile("D:\\Users\\polit\\Projects\\Java\\University\\Algorithms\\Huffman\\testfldr\\testfldr.huff");

            for (HuffmanFile h:
                 hf.getData().getLeft()) {
                System.out.println(h.getHeader().toString());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
