package utilities;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.DefaultBitInput;
import com.github.jinahya.bit.io.StreamByteInput;
import files.huffmanfile.HuffmanFile;
import files.huffmanfile.HuffmanFileBuilder;
import files.huffmanfile.HuffmanHeader;
import utilities.FileUtils;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

import static utilities.FileUtils.getFldrName;
import static utilities.FileUtils.isDir;
import static utilities.FileUtils.getFileName;

public class HuffmanFileUtils {




    public static void compressFile(File file) throws Exception {

        HuffmanFileBuilder.aHuffmanFile()
                                        .fromFile(file)
                                        .build()
                                        .toFile(getFileName(file.toString())+".huff");
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
                                        .withName(fldrName)
                                        .fromFiles(files)
                                        .build()
                                        .toFile(destination.toString()+"\\"+ fldrName+".huff");

    }

    public static void compressFolder(String fldrPath) throws Exception {
        compressFolder(fldrPath, Path.of(fldrPath));
    }



    private static void decompressRecursively(HuffmanFile hf, String path) throws IOException {
        if(hf.getHeader().isFile()){
            FileWriter fileWritter =  new FileWriter(path+"/"+hf.getHeader().getFileName());
            fileWritter.write(hf.getData().get());
            fileWritter.close();
        }else{
            Path folderPath  = Paths.get(path+ "/"+hf.getHeader().getFileName());

            Files.createDirectory(folderPath);

            for (HuffmanFile hf1:
                    hf.getData().getLeft()) {
                decompressRecursively(hf1, folderPath.toString());
            }
        }
    }

    public static void decompressionFromHuffmanFile(File file) throws IOException {
        HuffmanFile hf = HuffmanFile.fromFile(file.getPath());
        decompressRecursively(hf, file.getParent());
    }


}
