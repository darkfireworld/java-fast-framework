package org.darkgem.support.kit;

import java.util.UUID;

/**
 * Created by Administrator on 2016/5/12.
 */
public class IdKit {
    /**
     * 获取一个UUID
     */
    public static String uuid() {
//        UUID uuid = UUID.randomUUID();
//        byte[] data = new byte[16];
//        long msb = uuid.getMostSignificantBits();
//        long lsb = uuid.getLeastSignificantBits();
//        {
//            int offset = 0;
//            for (int i = 7; i > -1; i--) {
//                data[offset++] = (byte) ((msb >> 8 * i) & 0xFF);
//            }
//        }
//        {
//            int offset = 8;
//            for (int i = 7; i > -1; i--) {
//                data[offset++] = (byte) ((lsb >> 8 * i) & 0xFF);
//            }
//        }
//        return Base58Kit.encode(data);
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * Base58编码:
     * 使用的目的:
     * 1.避免混淆。在某些字体下，数字0和字母大写O，以及字母大写I和字母小写l会非常相似。
     * 2.不使用"+"和"/"的原因是非字母或数字的字符串作为帐号较难被接受。
     * 3.没有标点符号，通常不会被从中间分行。
     * 4.大部分的软件支持双击选择整个字符串.
     * 缺点;
     * 1. 由于256不能被58整除，Base58无法象Base64那样转换为8bits的2进制后依次取出6bits就可以快速完成转换(仅仅用Java对比)
     * 2. 经过Base58编码的数据为原始的数据长度的1.37倍，稍稍多于Base64的1.33倍
     */
    public static class Base58Kit {

        private static final char[] ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz"
                .toCharArray();
        private static final int BASE_58 = ALPHABET.length;
        private static final int BASE_256 = 256;

        private static final int[] INDEXES = new int[128];

        static {
            for (int i = 0; i < INDEXES.length; i++) {
                INDEXES[i] = -1;
            }
            for (int i = 0; i < ALPHABET.length; i++) {
                INDEXES[ALPHABET[i]] = i;
            }
        }

        public static String encode(byte[] input) {
            if (input.length == 0) {
                // paying with the same coin
                return "";
            }

            //
            // Make a copy of the input since we are going to modify it.
            //
            input = copyOfRange(input, 0, input.length);

            //
            // Count leading zeroes
            //
            int zeroCount = 0;
            while (zeroCount < input.length && input[zeroCount] == 0) {
                ++zeroCount;
            }

            //
            // The actual encoding
            //
            byte[] temp = new byte[input.length * 2];
            int j = temp.length;

            int startAt = zeroCount;
            while (startAt < input.length) {
                byte mod = divmod58(input, startAt);
                if (input[startAt] == 0) {
                    ++startAt;
                }

                temp[--j] = (byte) ALPHABET[mod];
            }

            //
            // Strip extra '1' if any
            //
            while (j < temp.length && temp[j] == ALPHABET[0]) {
                ++j;
            }

            //
            // Add as many leading '1' as there were leading zeros.
            //
            while (--zeroCount >= 0) {
                temp[--j] = (byte) ALPHABET[0];
            }

            byte[] output = copyOfRange(temp, j, temp.length);
            return new String(output);
        }

        public static byte[] decode(String input) {
            if (input.length() == 0) {
                // paying with the same coin
                return new byte[0];
            }

            byte[] input58 = new byte[input.length()];
            //
            // Transform the String to a base58 byte sequence
            //
            for (int i = 0; i < input.length(); ++i) {
                char c = input.charAt(i);

                int digit58 = -1;
                if (c >= 0 && c < 128) {
                    digit58 = INDEXES[c];
                }
                if (digit58 < 0) {
                    throw new RuntimeException("Not a Base58 input: " + input);
                }

                input58[i] = (byte) digit58;
            }

            //
            // Count leading zeroes
            //
            int zeroCount = 0;
            while (zeroCount < input58.length && input58[zeroCount] == 0) {
                ++zeroCount;
            }

            //
            // The encoding
            //
            byte[] temp = new byte[input.length()];
            int j = temp.length;

            int startAt = zeroCount;
            while (startAt < input58.length) {
                byte mod = divmod256(input58, startAt);
                if (input58[startAt] == 0) {
                    ++startAt;
                }

                temp[--j] = mod;
            }

            //
            // Do no add extra leading zeroes, move j to first non null byte.
            //
            while (j < temp.length && temp[j] == 0) {
                ++j;
            }

            return copyOfRange(temp, j - zeroCount, temp.length);
        }

        private static byte divmod58(byte[] number, int startAt) {
            int remainder = 0;
            for (int i = startAt; i < number.length; i++) {
                int digit256 = (int) number[i] & 0xFF;
                int temp = remainder * BASE_256 + digit256;
                number[i] = (byte) (temp / BASE_58);
                remainder = temp % BASE_58;
            }

            return (byte) remainder;
        }

        private static byte divmod256(byte[] number58, int startAt) {
            int remainder = 0;
            for (int i = startAt; i < number58.length; i++) {
                int digit58 = (int) number58[i] & 0xFF;
                int temp = remainder * BASE_58 + digit58;
                number58[i] = (byte) (temp / BASE_256);
                remainder = temp % BASE_256;
            }

            return (byte) remainder;
        }

        private static byte[] copyOfRange(byte[] source, int from, int to) {
            byte[] range = new byte[to - from];
            System.arraycopy(source, from, range, 0, range.length);
            return range;
        }
    }
}