package files.huffmanfile;


import com.company.HuffmanEnc;
import com.github.jinahya.bit.io.*;
import files.AppendableFileOutputStream;
import io.vavr.control.Either;

import java.io.*;

public class HuffmanFile {

    HuffmanHeader header;
    Either<HuffmanFile[],String> data;


    public HuffmanFile(BitInput bitInputStream) {
        setHeader(HuffmanHeader.read(bitInputStream));
        HuffmanEnc encoder =  new HuffmanEnc();
        try {
        if(getHeader().isFile()) {
                setData(Either.right(
                        encoder.decompress(bitInputStream,
                                getHeader().getSimpleHuffmanTree().getRoot(),
                                getHeader().getFile_size()
                        )
                ));
        }else
            setData(Either.left(deserializeFiles(bitInputStream)));
        } catch (IOException e) {
            System.out.println(getHeader().getFileName());
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
        long num_of_files = getHeader().getNum_of_files();


        HuffmanFile[] res = new HuffmanFile[(int)num_of_files];
        for (int i = 0; i < num_of_files; i++) {
            res[i] = new HuffmanFile(bitInput);
            bitInput.align(1);
        }
        return res;
    }


    private void toFile(AppendableFileOutputStream outputStream) throws IOException {
        BitOutput bitOutput = new DefaultBitOutput(new StreamByteOutput(outputStream));
        getHeader().write(bitOutput);

        if(this.getData().isRight()){
            HuffmanEnc encoder = new HuffmanEnc();
            encoder.compress(getHeader().getSimpleHuffmanTree(), this.getData().get());

            for (char b:
                    encoder.encodedData.toCharArray()) {
                bitOutput.writeBoolean(b == '1');
            }
            bitOutput.align(1);
        }else {
            outputStream = outputStream.appendMode();
            for (HuffmanFile hf: this.getData().getLeft()) {
                hf.toFile(outputStream);
            }
        }
    }


    public void toFile() throws IOException {
        String fos =   getHeader().getFileName()+".huff";
        toFile(new AppendableFileOutputStream(fos,false));
    }


    public  void toFile(String name) throws IOException {
        toFile(new AppendableFileOutputStream(name,false));
    }

    public HuffmanHeader getHeader() {
        return header;
    }

    public void setHeader(HuffmanHeader header) {
        this.header = header;
    }

    public Either<HuffmanFile[], String> getData() {
        return data;
    }

    public void setData(Either<HuffmanFile[], String> data) {
        this.data = data;
    }


    /**
     * Mehtod used to compress a given directory of file
     * @param location
     */
//    public static void toFile(String location) throws FileNotFoundException {
//        //determine if the given path is a file or directory
//        BitOutput bitOutput;
//
//        if(FileUtils.isDir(location)){
//            String fldrName = getFldrName(location);
//
//            bitOutput = new DefaultBitOutput(
//                    new StreamByteOutput(
//                            new FileOutputStream(fldrName+".huff")
//                    )
//                );
//
//            HuffmanHeader folderHeader = new HuffmanHeaderBuilder()
//                                                                .asFile(false)
//                                                                .withFileName(fldrName)
//                                                                .withNum_of_files(NoDirFiles(fldrName))
//                                                                .withHuff_char_flag(true)
//                                                                .build();
//
//        }else{
//
//            Tuple2<String, String> temp = getFileNameAndExtension(location);
//            String filename= temp._1;
//            String extension = temp._2;
//
//
//
//            HuffmanHeader folderHeader = new HuffmanHeaderBuilder()
//                                                                .asFile(true)
//                                                                .withFileName(filename+"."+extension)
//                                                                .withHuff_char_flag(true)
//                                                                .withHuffmanTree()
//                                                                .build();
//
//        }
//    }
}
