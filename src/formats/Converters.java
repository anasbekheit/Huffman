package formats;

import collections.extended.ExBitSet;

import java.util.BitSet;

class Converters {
    public static BitSet BitStr2BitSet(String s){
        BitSet res = new BitSet(s.length());
        char[] charArray = s.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];
            if (c == '1')
                res.set(i, true);
            else
                res.set(i, false);
        }
        return  res;
    }
    public static byte[] BitStr2BitByteArr(String s){
        byte[] res  = new byte[s.length()];
        for(int i=0; i<s.length();i++){
            if(s.toCharArray()[i]!='1'&& s.toCharArray()[i]!='0')
                throw new IllegalArgumentException("A bit can only be 0 or 1");
            res[i] = (byte)(s.toCharArray()[i]=='1'?1:0);
        }
        return res;
    }


    public static byte[] Bitset2BitByteArr(BitSet bitSet){
        byte[] res  = new byte[bitSet.length()];
        for(int i=0; i<bitSet.length();i++){
            res[i] = (byte)(bitSet.get(i)?1:0);
        }
        return res;
    }

    public static byte[] BitSet2ByteArr(BitSet bitSet){
        int  blen = bitSet.length();
        ExBitSet res = new ExBitSet(blen);
        for (int i =0;i<blen;i++){
            res.set(i,bitSet.get(i));
        }
        return res.toByteArray();
    }

    public static int BitSetToInt(BitSet bitSet){
        int  blen = bitSet.length();
        ExBitSet res = new ExBitSet(blen);
        for (int i =0;i<blen;i++){
            res.set(i,bitSet.get(i));
        }
        byte[] bytes = res.toByteArray();

        return ((bytes[0] & 0xFF) << 24)  | ((bytes[1] & 0xFF) << 16) |
                ((bytes[2] & 0xFF) << 8 ) | ((bytes[3] & 0xFF) << 0 );
    }


    public static String Int2BinStr(int number, int len) {
       return String.format("%"+len+"s", Integer.toBinaryString(number))
                    .replaceAll(" ", "0");
    }


    public static int ByteArr2Int(byte[] bytes) {
        return  ((bytes[0] & 0xFF) << 24) |
                ((bytes[1] & 0xFF) << 16) |
                ((bytes[2] & 0xFF) << 8 ) |
                ((bytes[3] & 0xFF) << 0 );
    }


    public static String StrToBinStr(String s) {
        StringBuilder res = new StringBuilder();
        char[] chars = s.toCharArray();
        for (char aChar : chars) {
            res.append(
                    String.format("%8s", Integer.toBinaryString(aChar))
                          .replaceAll(" ", "0")
            );
        }
        return res.toString();
    }
    public static BitSet StrToBitSet(String s){
        return BitStr2BitSet(StrToBinStr(s));
    }
    public static byte[] StrToBitByteArr(String s){
        return Bitset2BitByteArr(StrToBitSet(s));
    }


    public static Character[] charArrToCharObjArr(char[] in){
        Character[] out = new Character[in.length];
        for (int j = 0; j < in.length; j++) {
            out[j]= in[j];
        }
        return out;
    }


}
