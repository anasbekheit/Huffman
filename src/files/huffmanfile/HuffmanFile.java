package files.huffmanfile;


import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.DefaultBitInput;
import com.github.jinahya.bit.io.StreamByteInput;
import io.vavr.control.Either;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Optional;

public class HuffmanFile {

    HuffmanHeader header;
    Either<HuffmanFile[],String> data;


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

    public void toFile(String filename){

    }
}
