package com.company;

import java.util.Map;

public  class DataHandler {
    public static void save(HuffmanEnc encoder){

    }
    public static byte[] getHeaderInBytes(HuffmanEnc encoder) {
        byte[] header = new byte[3* encoder.num_entries+2];
        byte num_entries = (byte) (encoder.num_entries & 0xff);
        header[0] = encoder.paddedZeros;
        header[1] = num_entries;

        int test;
        int i=2;
        for (int j=0 ;j<256;j++){
            if(encoder.freq[j]>0){
                header[i] = (byte)j;
                byte[] freq = writeShortInTwoBytes(encoder.freq[j]);
                header[i + 1] = freq[0];
                header[i + 2] = freq[1];
                test=readTwoBytesToInt(freq,0);
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
        int i = ((byt[index] << 8) & 0x0000ff00) | (byt[index+1] & 0x000000ff);
        return i;
    }
    static char readByteAsChar(byte byt){
        return (char)byt;
    }
    public static void printHeaderAsString(byte[] header){
        System.out.println("padded zeros: "+header[0]);
        System.out.println("num entries: "+readUnsignedByte(header[1]));
        for(int i=2;i< header.length-2;i+=3){
            System.out.println("Character: "+readByteAsChar(header[i])+" Frequency:"+readTwoBytesToInt(header,i+1));

        }
    }
}
