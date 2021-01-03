package formats;

import java.util.Arrays;
import java.util.BitSet;
import collections.extended.*;

public class Utils {

    public static BitSet BitStr2BitSet(String s, int[] size) {
            ExBitSet res = new ExBitSet(s.length());
            char[] charArray = s.toCharArray();
            int j=0;
            for (int i = 0; i < charArray.length; i++,j++,size[0]++) {
                char c = charArray[i];
                if (c == '1')
                    res.set(j, true);
                else if (c == '0')
                    res.set(j, false);
                else {
                    res.appendNStartingFrom(
                            BitStr2BitSet(
                                    StrToBinStr(String.valueOf(c)
                                    ), size
                            )
                            ,j,8);
                    j+=7;
                    size[0]--;
                }
            }
            return res;
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

    public static byte[] getStringAsBytes(String encoded){
        BitSet bitSet = new BitSet(encoded.length());
        int bitcounter = 0;
        for(Character c : encoded.toCharArray()) {
            if(c.equals('1')) {
                bitSet.set(bitcounter);
            }
            bitcounter++;
        }
        return Arrays.copyOf(bitSet.toByteArray(),(bitcounter+7)/8);
    }

    public static String getBytesAsString(byte[] bytes, int stringLength){
        BitSet set= BitSet.valueOf(bytes);
        StringBuilder binaryString = new StringBuilder();
        int setLength=set.length();
        for(int i = 0; i < setLength; i++) {
            if(set.get(i)) {
                binaryString.append("1");
            } else {
                binaryString.append("0");
            }
        }

        int completer= stringLength-set.length();
        for(int i=0;i<completer;i++){
            binaryString.append("0");
        }
        return binaryString.toString();
    }


    public static String bitSetToString(BitSet bitSet){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bitSet.size() ; i++) {
            sb.append(bitSet.get(i)?"1":"0");
        }
        return sb.toString();
    }

    public static String longArrayToStrArray(long[] arr){
        return Arrays.toString(Arrays.stream(arr).mapToObj(String::valueOf).toArray(String[]::new));
    }


    private static byte[] concatBytes(byte[] a,
                                      byte[] b) {
        byte[] c = new byte[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }

    public static int readUnsignedByte(byte byt) {
        return Byte.toUnsignedInt(byt);
    }

    public static byte[] writeShortInTwoBytes(short x) {
        byte[] ret = new byte[2];
        ret[1] = (byte)(x & 0xff);
        ret[0] = (byte)((x >> 8) & 0xff);
        return ret;
    }

    public static int readTwoBytesToInt(byte[] byt,
                                        int index) {
        return ((byt[index] << 8) & 0x0000ff00) |
                (byt[index + 1] & 0x000000ff);
    }

    public static char readByteAsChar(byte byt) {
        return (char) byt;
    }
}
