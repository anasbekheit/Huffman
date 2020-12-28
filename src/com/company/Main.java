package com.company;

public class Main {
    public static void main(String[] args){
        String test="testeltestawy";
    HuffmanEnc encoder= new HuffmanEnc();
    encoder.compress(test);
        System.out.println(encoder.encodedData);
        System.out.println(encoder.decompress(encoder.encodedData));
        byte[] header= DataHandler.getHeaderInBytes(encoder);
       int headerlength=3* encoder.num_entries+2;
        System.out.println(encoder.getBytesAsString(header,headerlength));
        DataHandler.printHeaderAsString(header);

    }
}
