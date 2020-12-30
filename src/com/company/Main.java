package com.company;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) throws IOException {
        String filename=args[1];
        if(args[0].toLowerCase().equals("compress")){
            System.out.println("COMPRESSING");
            String unCompressed=readFile(filename);
            HuffmanEnc encoder=new HuffmanEnc();
            encoder.compress(unCompressed);
            DataHandler.save(encoder,filename+".huff");

        }
        else if(args[0].toLowerCase().equals("decompress")){
            System.out.println(DataHandler.load(filename));
        }
        else{
            throw new IllegalArgumentException();
        }

    }


    public static String readFile(String filename) throws IOException {
        File file = new File(filename);
        return FileUtils.readFileToString(file, StandardCharsets.US_ASCII   );
    }


















   /* public static void main(String[] args) throws IOException {
        String test="sattefli flooooosi";
    HuffmanEnc encoder= new HuffmanEnc();
    encoder.compress(test);
        System.out.println("encoded message: "+encoder.encodedData);
        System.out.println("Message After Decoding String: "+encoder.decompress(encoder.encodedData));
        byte[] header= DataHandler.getHeaderInBytes(encoder);
       int headerlength=3* encoder.num_entries+2;
        System.out.println("Header As Bytes: "+HuffmanEnc.getBytesAsString(header,headerlength));
        DataHandler.printHeaderAsString(header);
        byte[] encodedBytes= HuffmanEnc.getStringAsBytes(encoder.encodedData);
        System.out.println("Message After Decoding Bytes: "+encoder.decompress(HuffmanEnc.getBytesAsString(encodedBytes,encoder.encodedData.length())));
        DataHandler.save(encoder,"test");
        System.out.println(DataHandler.load("test.huff"));
        System.out.println("SIZE BEFORE COMPRESSION: "+test.length());
        System.out.println("SIZE  AFTER COMPRESSION: "+(encodedBytes.length+headerlength+ encoder.paddedZeros));
    }*/
}
