package com.example.hashtable.test;

import java.util.Random;

public class TestUtils {
    private static final int MAX_LENGTH = 6;
    private static final int MIN_LENGTH = 5;
    private static final int SEQUENCE_START_INDEX = 65;
    private static final int SEQUENCE_SIZE = 26;
    private static final Random random = new Random();

    public static String generateRandomString() {
        int length = random.nextInt(MAX_LENGTH - MIN_LENGTH + 1) + MIN_LENGTH;
        StringBuilder builder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            builder.append((char) (SEQUENCE_START_INDEX + random.nextInt(SEQUENCE_SIZE)));
        }
        return builder.toString();
    }

    private TestUtils() {

    }

}
