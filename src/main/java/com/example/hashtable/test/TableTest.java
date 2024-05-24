package com.example.hashtable.test;

import java.util.Random;

public class TableTest {
    private static final Random random = new Random();

    public static void main(String[] args) {

        for (double alpha = 0.1; alpha <= 1.0; alpha += 0.1) {
            test(new LinkedTestTable<>(32), alpha);
        }

    }

    public static void test(TestableTable<Integer> table, double alpha) {

        double insert = 0;
        double delete = 0;
        double search = 0;

        int length = (int) (alpha * table.length());
        String[] keys = new String[length];
        for (int i = 0; i < length; i++) {
            String str = TestUtils.generateRandomString();
            table.put(str, 0);
            keys[i] = str;
        }
        table.lastComplexity();

        for (int i = 0; i < length / 2; i++) {
            if (i % 10 != 0) {
                int index = random.nextInt(length);

                table.remove(keys[index]);
                delete += table.lastComplexity();

                String key = TestUtils.generateRandomString();

                table.put(key, 0);
                insert += table.lastComplexity();

                keys[index] = key;

                index = random.nextInt(length);
                table.get(keys[index]);
                search += table.lastComplexity();
            } else {
                table.remove(TestUtils.generateRandomString());
                delete += table.lastComplexity();

                int index = random.nextInt(length);
                table.put(keys[index], 0);
                insert += table.lastComplexity();

                table.get(TestUtils.generateRandomString());
                search += table.lastComplexity();
            }
        }

        System.out.print(alpha + ";");
        if (table instanceof LinkedTestTable<Integer>) {
            System.out.print((1 + alpha) + ";");   //вставка теор.
            System.out.print((0.1 * (1 + alpha) + 0.9 * (1 + alpha / 2)) + ";"); //удаление
            System.out.print((0.1 * (1 + alpha) + 0.9 * (1 + alpha / 2)) + ";");
        } else {
            System.out.print((0.1 * (-Math.log(1 - alpha) / alpha) + 0.9 * (1 / (1 - alpha))) + ";");
            System.out.print((0.9 * (-Math.log(1 - alpha) / alpha) + 0.1 * (1 / (1 - alpha))) + ";");
            System.out.print((0.9 * (-Math.log(1 - alpha) / alpha) + 0.1 * (1 / (1 - alpha))) + ";");
        }
        System.out.print((insert / ((double) length / 2)) + ";"); //вставка эксп.
        System.out.print((delete / ((double) length / 2)) + ";");
        System.out.print((search / ((double) length / 2)));
        System.out.println();
    }
}
