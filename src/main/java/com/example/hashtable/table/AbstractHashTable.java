package com.example.hashtable.table;

import static com.example.hashtable.table.HashTableUtils.convertToHashKey;
import static com.example.hashtable.table.HashTableUtils.validate;

public abstract class AbstractHashTable<V> implements HashTable<String, V> {
    protected static final double A = 0.6180339887;

    protected int size = 0;
    protected final int length;

    protected AbstractHashTable(int length) {
        this.length = length;
    }

    protected int hash(String key) {
        if (!validate(key))
            throw new IllegalArgumentException("Invalid key");
        int k = convertToHashKey(key);
        double ka = (k * A);
        double kaMod = ka - (int) ka;
        return (int) (length * kaMod);
    }

    @Override
    public int length() {
        return length;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }
}
