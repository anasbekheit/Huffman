package files.huffmanfile;


import com.company.HuffmanEnc;
import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.DefaultBitInput;
import com.github.jinahya.bit.io.StreamByteInput;
import files.FileUtils;
import io.vavr.control.Either;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class HuffmanFile {

    public HuffmanHeader header;
    public Either<HuffmanFile[],String> data;


    public HuffmanFile(BitInput bitInputStream) {
        header = HuffmanHeader.read(bitInputStream);
        HuffmanEnc encoder =  new HuffmanEnc();
        try {
        if(header.isFile()) {
                data = Either.right(
                        encoder.decompress(bitInputStream,
                                header.getSimpleHuffmanTree().getRoot(),
                                header.getFile_size()
                        )
                );
        }else
            data = Either.left(deserializeFiles(bitInputStream));
        } catch (IOException e) {
            System.out.println(header.getFileName());
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

    private HuffmanFile[] deserializeFiles(BitInput bitInput) throws IOException {
        long num_of_files = header.getNum_of_files();
        HuffmanFile[] res = new HuffmanFile[(int)num_of_files];
        for (int i = 0; i < num_of_files; i++) {
            res[i] = new HuffmanFile(bitInput);
            bitInput.align(1);
        }
        return res;
    }

    public void toFile(String filename){
        //determine if the given path is a file or directory
        if(FileUtils.isDir(filename)){

        }else{

        }
    }
}
