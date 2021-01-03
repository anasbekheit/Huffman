package files.huffmanfile;

import collections.extended.ExBitSet;
import collections.huffman.trees.SimpleHuffmanTree;
import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import io.vavr.Tuple2;

import java.io.IOException;
import java.util.Arrays;
import java.util.function.BiConsumer;

import static formats.Utils.longArrayToStrArray;


public class HuffmanHeader {
    private boolean isFile;
    private long num_of_files;

    private String fileName;

    private boolean huff_char_flag;
    private int num_of_nodes;

    private long[] file_sizes;

    private SimpleHuffmanTree simpleHuffmanTree;

    /**
     * Describes the file header definition.
     */
    private enum Components{
        IS_FILE( 1,false,HuffmanHeader::readIsFile,HuffmanHeader::writeIsFile),
        LEN_FILE_NAME(8,false,HuffmanHeader::readFileName,HuffmanHeader::writeFileName),   // unsigned byte (0,255)
        LEN_FILE_EXT(4,false,HuffmanHeader::readExtension,HuffmanHeader::writeExtension),    // unsigned half a byte (0,20)

        NUM_OF_FILES( 8*8, false,HuffmanHeader::readNumOfFiles,HuffmanHeader::writeNumOfFiles), // unsigned long
        LEN_OF_SIZES( 8*8,false,HuffmanHeader::readSizes,HuffmanHeader::writeSizes),  // unsigned long
        NUM_OF_NODES(2*8,false,HuffmanHeader::readNumOfNodes,HuffmanHeader::writeNumOfNodes),  // unsigned short
        HUFF_CHAR_FLAG( 1,false,HuffmanHeader::readHuffCharFlag,HuffmanHeader::writeHuffCharFlag),  // unsigned bool
        HUFFMAN_TREE(8,false,HuffmanHeader::readHuffmanTree,HuffmanHeader::writeHuffmanTree),
        ;

        int numOfBits;
        boolean signed;

        BiConsumer<BitInput, HuffmanHeader> rd;
        BiConsumer<BitOutput, HuffmanHeader> wt;

        Components(int nbits,
                   boolean isSigned,
                   BiConsumer<BitInput, HuffmanHeader> readFunc,
                   BiConsumer<BitOutput, HuffmanHeader> writeFunc){

            numOfBits = nbits;
            signed    = isSigned;
            rd        = readFunc;
            wt        = writeFunc;
            };


    }

    public static HuffmanHeader read(BitInput bitInputStream) {
        HuffmanHeader header = new HuffmanHeader();
        for (Components c:
             Components.values()) {
            c.rd.accept(bitInputStream,header);
        }
        return header;
    }

    public void write(BitOutput bitOutputStream) throws IOException{
        for (Components c:
                Components.values()) {
            c.wt.accept(bitOutputStream,this);
        }
    }

    private static void readNumOfFiles(BitInput bitInputStream, HuffmanHeader header) {
        try {
            header.num_of_files = bitInputStream.readLong(
                    Components.NUM_OF_FILES.signed,
                    Components.NUM_OF_FILES.numOfBits);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readHuffCharFlag(BitInput bitInputStream, HuffmanHeader header) {
        try {
            header.huff_char_flag = bitInputStream.readBoolean();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readSizes(BitInput bitInputStream, HuffmanHeader header) {
        header.file_sizes = new long[Math.toIntExact(header.num_of_files)];

        for (int i = 0; i < header.num_of_files; i++) {
            try {
                header.file_sizes[i] = bitInputStream.readLong(
                        Components.LEN_OF_SIZES.signed,
                        Components.LEN_OF_SIZES.numOfBits);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void readNumOfNodes(BitInput bitInputStream, HuffmanHeader header) {
        try {
            header.num_of_nodes = bitInputStream.readShort(
                    Components.NUM_OF_NODES.signed,
                    Components.NUM_OF_NODES.numOfBits);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readFileName(BitInput bitInputStream, HuffmanHeader header) {
        // Could be the size of the folder or file name
        int len_of_filename = 0;
        try {
            len_of_filename = bitInputStream.readByte(
                    Components.LEN_FILE_NAME.signed,
                    Components.LEN_FILE_NAME.numOfBits);

            header.fileName ="";

            for (int i = 0; i <len_of_filename ; i++) {

                header.fileName+= bitInputStream.readChar(8);

            }

            header.fileName+=".";
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readExtension(BitInput bitInputStream, HuffmanHeader header) {

        int len_of_ext = 0;
        try {
            len_of_ext = bitInputStream.readByte(
                    Components.LEN_FILE_EXT.signed,
                    Components.LEN_FILE_EXT.numOfBits);

            for (int i = 0; i <len_of_ext ; i++) {
                header.fileName+= bitInputStream.readChar(8);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readIsFile(BitInput bitInputStream, HuffmanHeader header) {
        try {
            header.isFile = bitInputStream.readBoolean();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readHuffmanTree(BitInput bitInputStream, HuffmanHeader header){
        try {
            header.simpleHuffmanTree = new SimpleHuffmanTree(
                    bitInputStream,
                    header.num_of_nodes,
                    Components.HUFFMAN_TREE.numOfBits,
                    Components.HUFFMAN_TREE.signed,
                    header.huff_char_flag
                    );
            bitInputStream.align(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    private static void writeHuffmanTree(BitOutput bitOutputStream, HuffmanHeader header){
        Tuple2<ExBitSet,Integer> res =  header.simpleHuffmanTree.serializeLvlOrder();
        ExBitSet bs = res._1;
        Integer size = res._2;

        try {
        for (int i = 0; i < size; i++) {
                bitOutputStream.writeBoolean(bs.get(i));

        }
        bitOutputStream.align(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeNumOfFiles(BitOutput bitOutputStream, HuffmanHeader header) {
        try {
             bitOutputStream.writeLong(
                    Components.NUM_OF_FILES.signed,
                    Components.NUM_OF_FILES.numOfBits,
                     header.num_of_files);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeHuffCharFlag(BitOutput bitOutputStream, HuffmanHeader header) {
        try {
            bitOutputStream.writeBoolean(header.huff_char_flag);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeSizes(BitOutput bitOutputStream, HuffmanHeader header) {
        for (int i = 0; i < header.num_of_files; i++) {
            try {
                bitOutputStream.writeLong(
                        Components.LEN_OF_SIZES.signed,
                        Components.LEN_OF_SIZES.numOfBits,
                        header.file_sizes[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void writeNumOfNodes(BitOutput bitOutputStream, HuffmanHeader header) {
        try {
              bitOutputStream.writeShort(
                        Components.NUM_OF_NODES.signed,
                        Components.NUM_OF_NODES.numOfBits,
                      (short) header.num_of_nodes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeFileName(BitOutput bitOutputStream, HuffmanHeader header) {
        try {
            String filename = header.fileName.split("\\.")[0];
            byte len_of_filename = (byte)filename.length();

            bitOutputStream.writeByte(Components.LEN_FILE_EXT.signed,
                    Components.LEN_FILE_NAME.numOfBits,
                    len_of_filename
            );

            char[] filenameChars = filename.toCharArray();
            for (int i = 0; i <len_of_filename ; i++) {
                bitOutputStream.writeChar(8,filenameChars[i]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeExtension(BitOutput bitOutputStream, HuffmanHeader header) {
        try {
            String ext = header.fileName.split("\\.")[1];
            byte len_of_ext = (byte)ext.length();

            bitOutputStream.writeByte(Components.LEN_FILE_EXT.signed,
                                      Components.LEN_FILE_EXT.numOfBits,
                                      len_of_ext
                                     );

            char[] extchars = ext.toCharArray();
            for (int i = 0; i <len_of_ext ; i++) {
                bitOutputStream.writeChar(8,extchars[i]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeIsFile(BitOutput bitOutputStream, HuffmanHeader header) {
        try {
            bitOutputStream.writeBoolean(header.isFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isFile() {
        return isFile;
    }

    public long getNum_of_files() {
        return num_of_files;
    }

    public String getFileName() {
        return fileName;
    }

    public boolean isHuff_char_flag() {
        return huff_char_flag;
    }

    public int getNum_of_nodes() {
        return num_of_nodes;
    }

    public long[] getFile_sizes() {
        return file_sizes;
    }


    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("{\""+ (this.isFile?"Filename\": ":"Folder\": ") +this.fileName)
                .append(", \"# of files\": "+ String.valueOf(this.num_of_files))
                .append(", \"offsets\": "+longArrayToStrArray(this.file_sizes))
                .append(", \"# of Nodes\": "+ String.valueOf(this.num_of_nodes))
                .append(", \"huffman tree inorder:\" "+ Arrays.toString(simpleHuffmanTree.visitInOrder()))
                .append("}");

        return sb.toString();
    }

}
