package examples;

import utilities.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Random {
    public static void main(String[] args) throws IOException {
        String path = new String("D:\\Users\\polit\\Projects\\Java\\University\\Algorithms\\Huffman\\testfldr\\testfldr.huff");
//        Path folderPath  = Paths.get(new File(path).getParent().toString()+"/"+"default");
//        System.out.println(folderPath);
//        Files.createDirectory(folderPath);

        System.out.println(new File(path).getAbsolutePath());

    }
}
