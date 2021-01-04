package files;

import com.company.HuffmanEnc;
import formats.Utils;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;


public class DataHandler {
//
//    public static void save(HuffmanEnc encoder, String path) throws IOException {
//        byte[] header = getHeaderInBytes(encoder);
//        byte[] data = Utils.getStringAsBytes(encoder.encodedData);
//        appendToFile(path, header, false);
//        appendToFile(path, data, true);
//        System.out.println("Saved Successfully");
//    }


//    public static String load(String filename) throws IOException {
//
//        File file = new File(filename);
//
//        byte[] fileAsBytes = FileUtils.readFileToByteArray(file);
//
//        int paddedZeros = Utils.readUnsignedByte(fileAsBytes[0]);
//        int numEntries = Utils.readUnsignedByte(fileAsBytes[1]);
//
//        int[] freq = new int[256];
//        int headerLength = 3 * numEntries + 6;
//        char c = 0;
//        int f = 0;
//
//        for (int i = 2; i < headerLength - 2; i += 3) {
//            c = Utils.readByteAsChar(fileAsBytes[i]);
//            f = Utils.readTwoBytesToInt(fileAsBytes, i + 1);
//            freq[Utils.readByteAsChar(fileAsBytes[i])] = Utils.readTwoBytesToInt(fileAsBytes, i + 1);
//        }
//
//        byte[] encodedData = Arrays.copyOfRange(fileAsBytes, headerLength, fileAsBytes.length);
//        HuffmanEnc encoder = new HuffmanEnc();
//        encoder.root = HuffmanEnc.buildHuffmanTree(freq);
//
//        int encodedDataLength = 8 * encodedData.length - paddedZeros;
//
//        return encoder.decompress(Utils.getBytesAsString(encodedData, encodedDataLength));
//    }

    public static byte[] getHeaderInBytes(HuffmanEnc encoder) {
        byte[] header = new byte[3 * encoder.num_entries + 6];
        byte num_entries = (byte)(encoder.num_entries & 0xff);

        int i = 2;
        for (int j = 0; j < 256; j++) {
            if (encoder.freq[j] > 0) {
                header[i] = (byte) j;
                byte[] freq = Utils.writeShortInTwoBytes((short) encoder.freq[j]);
                header[i + 1] = freq[0];
                header[i + 2] = freq[1];
                i += 3;
            }

        }
        return header;
    }

    public static void printHeaderAsString(byte[] header) {
        byte paddedZeros = header[0];
        int numEntries = Utils.readUnsignedByte(header[1]);

        System.out.println("\n-----------BEGIN HEADER-----------");
        System.out.println("padded zeros: " + paddedZeros);
        System.out.println("num entries: " + numEntries);

        for (int i = 2; i < header.length - 2; i += 3) {
            System.out.println("Character: " + Utils.readByteAsChar(header[i]) +
                               " Frequency:" + Utils.readTwoBytesToInt(header, i + 1));
        }

        System.out.println("-----------END HEADER-----------");
    }

    private static void appendToFile(String  path,
                                     byte[]  data,
                                     boolean append) throws IOException {
        FileOutputStream output = new FileOutputStream(path + ".huff", append);
        try {
            output.write(data);
        } finally {
            output.close();
        }
    }
}