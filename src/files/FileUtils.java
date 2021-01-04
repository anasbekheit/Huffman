package files;

import io.vavr.Tuple2;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtils {
    public static Boolean isDir(String dir) {
        Path path  = new File(dir).toPath();
        if (!Files.exists(path)) return false;
        else return Files.isDirectory(path);
    }

    public static Long NoDirFiles(String dir){
        String[] res = new File(dir).list();
        return res!=null?Long.valueOf(res.length):Long.valueOf(0);
    }

    public static String getFldrName(String filename) {
        String fldrName;
        if(filename.contains("/")){
            String[] temp = filename.split("/");
            fldrName = temp[temp.length-1];
        }else{
            String[] temp = filename.split("\\\\");
            fldrName = temp[temp.length-1];
        }
        return fldrName;
    }

    public static Tuple2<String,String> getFileNameAndExtension(String str){
        String[] temp = str.split("\\.");
        return new Tuple2<>(temp[temp.length-2],temp[temp.length-1]);
    }

}
