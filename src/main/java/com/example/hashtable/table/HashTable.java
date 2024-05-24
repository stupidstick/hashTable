package com.example.hashtable.table;

import com.example.hashtable.visual.TableData;

import java.util.List;

public interface HashTable<K, V> extends Iterable<V> {
    int size();

    int length();

    boolean isEmpty();

    void clear();

    V get(K key);

    boolean put(K key, V val);

    boolean remove(K key);


    List<TableData> convertToTableData();
}
