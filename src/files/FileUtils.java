package files;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtils {
    public static Boolean isDir(String dir) {
        Path path  = new File(dir).toPath();
        if (!Files.exists(path)) return false;
        else return Files.isDirectory(path);
    }
}
