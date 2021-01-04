package files.huffmanfile;

import com.company.HuffmanEnc;
import io.vavr.control.Either;

import java.io.File;
import java.util.ArrayList;

import static files.FileUtils.readFileAsString;

public final class HuffmanFileBuilder {
    HuffmanHeader header;
    Either<HuffmanFile[], String> data;
    String name ="default";

    private HuffmanFileBuilder() {
    }

    public static HuffmanFileBuilder aHuffmanFile() {
        return new HuffmanFileBuilder();
    }

    public HuffmanFileBuilder fromFiles(ArrayList<File> files) throws Exception {
        ArrayList<HuffmanFile> HFL = new ArrayList<>();
        for (File f:
             files) {
                HFL.add(
                        new HuffmanFileBuilder()
                                        .fromFile(f)
                                        .build()
                        );
        }

        header  = new HuffmanHeaderBuilder()
                                    .fromFiles(HFL)
                                    .withFileName(name)
                                    .build();
        this.data = Either.left(HFL.toArray(HuffmanFile[]::new));
        return this;
    }

    public HuffmanFileBuilder fromFile(File file) throws Exception {
        data = Either.right(readFileAsString(file));
        HuffmanEnc encoder =  new HuffmanEnc();
        encoder.compress(data.get());
        header = new HuffmanHeaderBuilder()
                                        .fromFile(file,encoder)
                                        .build();
        return this;
    }

    public HuffmanFileBuilder withName(String s){
        name  = s;
        return this;
    }

    public HuffmanFile build() {
        HuffmanFile huffmanFile = new HuffmanFile();
        huffmanFile.setHeader(header);
        huffmanFile.setData(data);
        return huffmanFile;
    }
}
