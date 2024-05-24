package com.example.hashtable.test;


import com.example.hashtable.table.HashTable;

public interface TestableTable<V> extends HashTable<String, V> {

    int lastComplexity();

}
