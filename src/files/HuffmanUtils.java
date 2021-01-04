package files;

import files.huffmanfile.HuffmanFileBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

import static files.FileUtils.getFldrName;
import static files.FileUtils.isDir;

public class HuffmanUtils {
    public static void compressFile(File file) throws Exception {

        HuffmanFileBuilder.aHuffmanFile()
                                        .fromFile(file)
                                        .build()
                                        .toFile();

    }

    public static void compressFolder(String fldrPath) throws Exception {
        if(!isDir(fldrPath))
            throw new IllegalArgumentException("Path is not a directory");

        Path sourceFolderPath = Paths.get(fldrPath);
        String fldrName  = getFldrName(fldrPath);
        ArrayList<File>  files = new ArrayList<>();

        Files.walkFileTree(sourceFolderPath, new SimpleFileVisitor<Path>() {
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if(!Files.isDirectory(file)){
                    files.add(file.toFile());
                }
                return FileVisitResult.CONTINUE;
            }
        });

        HuffmanFileBuilder.aHuffmanFile()
                                        .fromFiles(files)
                                        .build()
                                        .toFile(fldrName+".huff");
    }


}
