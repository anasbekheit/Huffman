package com.company;

import files.DataHandler;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {
/*    public static void main(String[] args) throws IOException {
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

 */

//    public static void main(String[] args) throws IOException {
//        System.out.print("Please Enter File Name: ");
//
//        String filename=new Scanner(System.in).nextLine();
//        String data=readFile(filename);
//
//        HuffmanEnc encoder=new HuffmanEnc();
//        encoder.compress(data);
//
//        DataHandler.save(encoder,filename);
//
//        String decodedAfterLoading=DataHandler.load(filename+".huff");
//
//        System.out.println("SIZE BEFORE COMPRESSION: "+data.length());
//
//        int headerLength=DataHandler.getHeaderInBytes(encoder).length;
//
//        int dataLength=(encoder.encodedData.length()+7)/8;
//
//        System.out.println("SIZE  AFTER COMPRESSION: "+(dataLength+headerLength));
//        //TESTS IF decoded file is the same as input data
//        if(data.equals(decodedAfterLoading))
//        {
//            System.out.println("IDENTICAL");
//        }
//
//    }



    public static String readFile(String filename) throws IOException {
        File file = new File(filename);
        return FileUtils.readFileToString(file, StandardCharsets.US_ASCII   );
    }

}
