package files.huffmanfile;


import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.DefaultBitInput;
import com.github.jinahya.bit.io.StreamByteInput;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Optional;

public class HuffmanFile {

    HuffmanHeader header;


    public HuffmanFile(BitInput bitInputStream) {
        header = HuffmanHeader.read(bitInputStream);
    }

    public static HuffmanFile fromFile(String filename) throws FileNotFoundException {
        return new HuffmanFile(
                new DefaultBitInput(
                        new StreamByteInput(
                                new FileInputStream(filename)
                        )
                ));
    }
}
