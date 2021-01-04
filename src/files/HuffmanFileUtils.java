package files;

import files.huffmanfile.HuffmanFileBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

import static files.FileUtils.getFldrName;
import static files.FileUtils.isDir;
import static files.FileUtils.getFileName;

public class HuffmanFileUtils {




    public static void compressFile(File file) throws Exception {

        HuffmanFileBuilder.aHuffmanFile()
                                        .fromFile(file)
                                        .build()
                                        .toFile();
    }

    public static void compressFile(File src,Path destination) throws Exception {
        if(!isDir(destination.toString()))
            throw new IllegalArgumentException("Destination is not a not a directory.");

        HuffmanFileBuilder.aHuffmanFile()
                .fromFile(src)
                .build()
                .toFile(getFileName(src.toString())+".huff");
    }

    public static void compressFolder(String fldrPath,Path destination) throws Exception{
        if(!isDir(destination.toString()))
            throw new IllegalArgumentException("Destination is not a not a directory.");

        if(!isDir(fldrPath))
            throw new IllegalArgumentException("Path is not a directory");

        Path srcFlderPath      = Paths.get(fldrPath);
        String fldrName        = getFldrName(fldrPath);
        ArrayList<File>  files = new ArrayList<>();

        Files.walkFileTree(srcFlderPath, new SimpleFileVisitor<Path>() {
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if(!Files.isDirectory(file) && !file.getFileName().toString().contains(".huff")){
                        files.add(file.toFile());
                }
                return FileVisitResult.CONTINUE;
            }
        });

        HuffmanFileBuilder.aHuffmanFile()
                                        .fromFiles(files)
                                        .build()
                                        .toFile(destination.toString()+"\\"+ fldrName+".huff");

    }

    public static void compressFolder(String fldrPath) throws Exception {
        compressFolder(fldrPath, Path.of(fldrPath));
    }


}
