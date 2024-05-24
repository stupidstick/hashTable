package com.example.hashtable.table;

public interface Iterator<V> {

    boolean hasNext();

    V get();

    void set(V val);

    void next();
}
