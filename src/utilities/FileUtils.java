package utilities;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtils {
    public static Boolean isDir(String dir) {
        Path path  = new File(dir).toPath();
        if (!Files.exists(path)) return false;
        else return Files.isDirectory(path);
    }

    public static String getFldrName(String filename) {
        String fldrName;
        if(filename.endsWith("/")|| filename.endsWith("\\\\")){
            filename = filename.substring(0, filename.lastIndexOf("\\")-1);
        }

        if(filename.contains("/")){
            String[] temp = filename.split("/");
            fldrName = temp[temp.length-1];
        }else{
            String[] temp = filename.split("\\\\");
            fldrName = temp[temp.length-1];
        }
        return fldrName;
    }



    public static String getFileName(String s){
        String[] temp  =s.split("\\.");
        return temp[0];
    }

    public static String readFileAsString(File f)throws Exception {
        return new String(Files.readAllBytes(Path.of(f.getAbsolutePath())));
    }

}
