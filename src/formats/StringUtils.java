package formats;

import java.util.Arrays;
import java.util.BitSet;

public class StringUtils {

    public static byte[] getStringAsBytes(String encoded, int index) {
        BitSet bitSet = new BitSet(encoded.length() - index);
        int bitcounter = 0;
        char[] charArray = encoded.toCharArray();
        for (int i = index, charArrayLength = charArray.length - index; i < charArrayLength - index; i++) {
            Character c = charArray[i];
            if (c.equals('1')) {
                bitSet.set(bitcounter);
            }
            bitcounter++;
        }
        return Arrays.copyOf(bitSet.toByteArray(), (bitcounter + 7) / 8);
    }

    public static String getBytesAsString(byte[] bytes, int stringLength) {
        BitSet set = BitSet.valueOf(bytes);
        StringBuilder binaryString = new StringBuilder();
        for (int i = 0; i < (set.length()); i++) {
            if (set.get(i)) {
                binaryString.append("1");
            } else {
                binaryString.append("0");
            }
        }
        int completer = stringLength - set.length();
        for (int i = 0; i < completer; i++) {
            binaryString.append("0");
        }
        return binaryString.toString();
    }

    public static String reverseString(String str) {
        StringBuilder sb = new StringBuilder(str);
        sb.reverse();
        return sb.toString();
    }

}
