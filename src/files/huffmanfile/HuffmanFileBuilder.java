package files.huffmanfile;

import io.vavr.control.Either;

public final class HuffmanFileBuilder {
    HuffmanHeader header;
    Either<HuffmanFile[], String> data;

    private HuffmanFileBuilder() {
    }


    public HuffmanFileBuilder withHeader(HuffmanHeader header) {
        this.header = header;
        return this;
    }

    public HuffmanFileBuilder withData(Either<HuffmanFile[], String> data) {
        this.data = data;
        return this;
    }


    public HuffmanFile build() {
        HuffmanFile huffmanFile = new HuffmanFile(null);
        huffmanFile.setHeader(header);
        huffmanFile.setData(data);
        return huffmanFile;
    }
}
