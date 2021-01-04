package RandomTests;

import com.github.jinahya.bit.io.*;
import files.huffmanfile.HuffmanHeader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class UsingBitIO {
    public static void main(String[] args) throws IOException {

        StreamByteInput bitInput = new StreamByteInput(new FileInputStream("myfile1.out"));
        final BitInput bitInput2 = new DefaultBitInput(bitInput);

        StreamByteInput bitInput1 = new StreamByteInput(new FileInputStream("Test.io"));
        final BitInput bitInput22 = new DefaultBitInput(bitInput1);

        HuffmanHeader header = HuffmanHeader.read(bitInput2);
        System.out.println(header.toString());
        header.write(new DefaultBitOutput(new StreamByteOutput(new FileOutputStream("FolderTest.io"))));

        ///HuffmanHeader header2 = HuffmanHeader.read(bitInput22);
        ///System.out.println(header2.toString());

    }
}
