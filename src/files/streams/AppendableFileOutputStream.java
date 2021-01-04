package files.streams;

import java.io.*;

public class AppendableFileOutputStream extends FileOutputStream {

    private boolean isAppend = false;
    private String path;

    public AppendableFileOutputStream(String name) throws FileNotFoundException {
        super(name);
        path = name;
    }

    public AppendableFileOutputStream(String name, boolean append) throws FileNotFoundException {
        super(name, append);
        path =name;
        isAppend =append;
    }

    public AppendableFileOutputStream(File file) throws FileNotFoundException {
        super(file);
        path = file.getPath();
    }

    public AppendableFileOutputStream(File file, boolean append) throws FileNotFoundException {
        super(file, append);
        path =file.getPath();
        isAppend = append;
    }

    public AppendableFileOutputStream(FileDescriptor fdObj) {
        super(fdObj);
        throw new UnsupportedOperationException();
    }

    public AppendableFileOutputStream appendMode() throws IOException {
        if (!isAppend){
            isAppend = true;
            this.close();
            return new AppendableFileOutputStream(path,isAppend);
        }
        return this;
    }

    public AppendableFileOutputStream overwriteMode() throws IOException {
        if (isAppend){
            isAppend = false;
            this.close();
            return new AppendableFileOutputStream(path,isAppend);
        }
        return this;
    }

}
