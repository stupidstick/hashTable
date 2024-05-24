package com.example.hashtable.table;

import com.example.hashtable.visual.TableData;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class AddressHashTable<V> extends AbstractHashTable<V> {
    private static final int C1 = 7;
    private static final int C2 = 228;

    private final Entry[] data;

    @SuppressWarnings("unchecked")
    public AddressHashTable(int length) {
        super(length);
        this.data = (Entry[]) Array.newInstance(Entry.class, length);
    }

    @Override
    public void clear() {
        Arrays.fill(data, null);
        size = 0;
    }

    @Override
    public V get(String key) {
        for (int i = 0; i < length; i++) {
            int j = quadHash(key, i);
            if (Objects.equals(data[j].key, key)) {
                return data[j].val;
            }
        }
        return null;
    }

    @Override
    public boolean put(String key, V val) {
        int i = 0;
        int pos = -1;
        while (i != length) {
            int j = quadHash(key, i);
            if (data[j] == null) {
                pos = j;
                break;
            }
            if (data[j].key.equals(key)) {
                data[j].val = val;
                return true;
            }
            i++;
        }
        if (i == length) {
            return false;
        }
        data[pos] = new Entry(key, val);
        size++;
        return true;
    }

    @Override
    public boolean remove(String key) {
        for (int i = 0; i < length; i++) {
            int j = quadHash(key, i);
            if (Objects.equals(data[j].key, key)) {
                data[j] = null;
                size--;
                return true;
            }
        }
        return false;
    }

    @Override
    public List<TableData> convertToTableData() {
        List<TableData> tableData = new ArrayList<>();
        for (int i = 0; i < data.length; i++) {
            if (data[i] == null) {
                tableData.add(new TableData(i, "-", "-"));
            } else {
                tableData.add(new TableData(i, data[i].key, String.valueOf(data[i].val)));
            }
        }
        return tableData;
    }

    @Override
    public Iterator<V> begin() {
        return new AddressIterator();
    }

    private int quadHash(String key, int i) {
        return (hash(key) + C1 * i + C2 * i * i) % length;
    }

    private class Entry {
        String key;
        V val;

        public Entry(String key, V val) {
            this.key = key;
            this.val = val;
        }
    }

    private class AddressIterator implements Iterator<V> {

        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            for (int i = currentIndex + 1; currentIndex < length; i++) {
                if (data[i] != null) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public void next() {
            for (int i = currentIndex + 1; currentIndex < length; i++) {
                if (data[i] != null) {
                    currentIndex = i;
                    return;
                }
            }
            throw new IndexOutOfBoundsException("Следующего элемента не существует.");
        }

        @Override
        public V get() {
            return data[currentIndex].val;
        }

        @Override
        public void set(V val) {
            data[currentIndex].val = val;
        }

    }

}
