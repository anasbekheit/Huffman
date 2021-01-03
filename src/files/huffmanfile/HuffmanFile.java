package files.huffmanfile;


import collections.huffman.trees.HuffmanNode;
import collections.huffman.trees.TreeMapper;
import com.company.HuffmanEnc;
import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.DefaultBitInput;
import com.github.jinahya.bit.io.StreamByteInput;
import io.vavr.control.Either;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

public class HuffmanFile {

    HuffmanHeader header;
    public Either<HuffmanFile[],String> data;


    public HuffmanFile(BitInput bitInputStream) {
        header = HuffmanHeader.read(bitInputStream);
        HuffmanEnc encoder =  new HuffmanEnc();
        try {
        if(header.isFile()) {
                data = Either.right(
                        encoder.decompress(bitInputStream,
                                header.getSimpleHuffmanTree().getRoot(),
                                header.getFile_sizes()[0]
                        )
                );
        }else
            data = Either.left(new HuffmanFile[]{});
        } catch (IOException e) {
            e.printStackTrace();
        }
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
