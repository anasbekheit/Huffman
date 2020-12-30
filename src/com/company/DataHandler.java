package com.company;

import com.sun.deploy.util.ArrayUtil;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;


public  class DataHandler {
    public static void save(HuffmanEnc encoder,String path) throws IOException {
        File file=new File(path+".huff");
        byte[] header=getHeaderInBytes(encoder);
       // FileUtils.writeByteArrayToFile(file, header);

        byte[] data= HuffmanEnc.getStringAsBytes(encoder.encodedData);
        byte[] write= concatBytes(header,data);
        FileUtils.writeByteArrayToFile(file,write);
        System.out.println("Saved Successfully");

    }
    public static String load(String filename) throws IOException {

        File file = new File(filename);
        byte[] fileAsBytes=FileUtils.readFileToByteArray(file);
        int paddedZeros=readUnsignedByte(fileAsBytes[0]);
        int numEntries=readUnsignedByte(fileAsBytes[1]);

       int[] freq= new int[256];
        int headerLength=3*numEntries+6;
        char c=0; int f =0;
        for(int i=2;i< headerLength-2;i+=3){
             c=readByteAsChar(fileAsBytes[i]);
             f=readTwoBytesToInt(fileAsBytes,i+1);
            freq[readByteAsChar(fileAsBytes[i])]=readTwoBytesToInt(fileAsBytes,i+1);
        }
        byte[] encodedData= Arrays.copyOfRange(fileAsBytes,headerLength,fileAsBytes.length);
        HuffmanEnc encoder = new HuffmanEnc();
        encoder.root=HuffmanEnc.buildHuffmanTree(freq);
            int encodedDataLength=8*encodedData.length-paddedZeros;
            //   encoder.decompress(HuffmanEnc.getBytesAsString(encodedData,encoder.encodedData.length()))
        return encoder.decompress(HuffmanEnc.getBytesAsString(encodedData,encodedDataLength));
    }
    public static byte[] getHeaderInBytes(HuffmanEnc encoder) {
        byte[] header = new byte[3* encoder.num_entries+6];
        byte num_entries = (byte) (encoder.num_entries & 0xff);
        header[0] = encoder.paddedZeros;
        header[1] = num_entries;

        int i=2;
        for (int j=0 ;j<256;j++){
            if(encoder.freq[j]>0){
                header[i] = (byte)j;
                byte[] freq = writeShortInTwoBytes((short)encoder.freq[j]);
                header[i + 1] = freq[0];
                header[i + 2] = freq[1];
                i+=3;
            }

        }
        return header;
    }
    static int readUnsignedByte(byte byt){
        return Byte.toUnsignedInt(byt);
    }
    static byte[]  writeShortInTwoBytes(short x){
        byte[] ret=new byte[2];
        ret[1] = (byte)(x & 0xff);
        ret[0] = (byte)((x >> 8) & 0xff);
        return ret;
    }
    static int readTwoBytesToInt(byte[] byt,int index){
        return ((byt[index] << 8) & 0x0000ff00) | (byt[index+1] & 0x000000ff);
    }
    static char readByteAsChar(byte byt){
        return (char)byt;
    }
    public static void printHeaderAsString(byte[] header){

        byte paddedZeros=header[0];
        int numEntries=readUnsignedByte(header[1]);



        System.out.println("\n-----------BEGIN HEADER-----------");
        System.out.println("padded zeros: "+paddedZeros);
        System.out.println("num entries: "+numEntries);
        for(int i=2;i< header.length-2;i+=3){
            System.out.println("Character: "+readByteAsChar(header[i])+" Frequency:"+readTwoBytesToInt(header,i+1));

        }
        System.out.println("-----------END HEADER-----------");
    }
    private static byte[] concatBytes(byte[]a,byte[]b){
        byte[] c = new byte[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }
}
