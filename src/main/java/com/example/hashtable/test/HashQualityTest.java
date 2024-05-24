package com.example.hashtable.test;

import com.example.hashtable.table.AbstractHashTable;
import com.example.hashtable.table.LinkedHashTable;

import java.lang.reflect.Method;
import java.util.Arrays;

import static java.lang.Math.sqrt;

public class HashQualityTest {

    public static void main(String[] args) {
        var test = new ReflectionTest(256);
        System.out.println(test.test());
    }

    private static class ReflectionTest {

        private final LinkedHashTable<String> table;

        public ReflectionTest(int m) {
            this.table = new LinkedHashTable<>(m);
        }

        public String test() {
            int size = table.length();

            int[] keysCount = new int[size];
            Arrays.fill(keysCount, 0);

            for (int i = 0; i < 20 * size; i++) {
                int hashKey = invokeHashMethod(TestUtils.generateRandomString());
                keysCount[hashKey]++;
            }

            double sum = 0;
            for (int i = 0; i < size; i++) {
                int diff = keysCount[i] - 20;
                sum += (diff * diff);
            }

            sum /= 20;

            return (size - sqrt(size)) + ";" + (size + sqrt(size)) + ";" + sum;

        }

        private int invokeHashMethod(String key) {
            try {
                Method hashMethod = AbstractHashTable.class.getDeclaredMethod("hash", String.class);
                hashMethod.setAccessible(true);
                return (int) hashMethod.invoke(table, key);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }


    }


}
