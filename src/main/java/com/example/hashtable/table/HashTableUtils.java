package com.example.hashtable.table;

public class HashTableUtils {
    private static final byte CHAR_OFFSET = -64;
    private static final int BITS_COUNT = 5;

    private HashTableUtils() {
    }

    public static boolean validate(String key) {
        return key.matches("^[A-Z]+$");
    }

    public static int convertToHashKey(String key) {
        byte[] bytes = key.getBytes();
        StringBuilder binaryKeyBuilder = new StringBuilder(BITS_COUNT * bytes.length);

        for (byte b : bytes) {
            binaryKeyBuilder.append(
                    String.format("%5s", Integer.toBinaryString(b + CHAR_OFFSET)).replace(' ', '0')
            );
        }

        return Integer.parseInt(binaryKeyBuilder.toString(), 2);
    }


}
