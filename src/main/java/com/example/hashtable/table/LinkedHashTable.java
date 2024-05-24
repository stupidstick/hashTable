package com.example.hashtable.table;

import com.example.hashtable.visual.TableData;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class LinkedHashTable<V> extends AbstractHashTable<V> {
    private final Entry[] data;

    @SuppressWarnings("unchecked")
    public LinkedHashTable(int length) {
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
        int i = hash(key);
        if (data[i] == null) {
            return null;
        }
        var node = new Node(key, null);
        int iNode = data[i].nodes.indexOf(node);
        if (iNode == -1)
            return null;
        return data[i].nodes.get(iNode).val;
    }

    @Override
    public boolean put(String key, V val) {
        int i = hash(key);
        if (data[i] == null)
            data[i] = new Entry();
        data[i].addNode(key, val);
        size++;
        return true;
    }

    @Override
    public boolean remove(String key) {
        int i = hash(key);
        if (data[i] == null) {
            return false;
        }
        var node = new Node(key, null);
        int iNode = data[i].nodes.indexOf(node);
        if (iNode == -1) {
            return false;
        }
        data[i].nodes.remove(iNode);
        size--;
        return true;
    }

    @Override
    public List<TableData> convertToTableData() {
        List<TableData> tableData = new ArrayList<>();
        for (int i = 0; i < data.length; i++) {
            if (data[i] == null || data[i].nodes.isEmpty()) {
                tableData.add(new TableData(i, "", "-"));
            } else {
                String nodes = data[i].nodes.stream()
                        .map(n -> "(" + n.key + ", " + n.val + ")")
                        .collect(Collectors.joining(", "));
                tableData.add(new TableData(i, "", nodes));
            }
        }
        return tableData;
    }

    @Override
    public Iterator<V> begin() {
        return new LinkedIterator();
    }

    private class Entry {
        List<Node> nodes = new ArrayList<>();

        public void addNode(String key, V val) {
            var node = new Node(key, val);
            int i = nodes.indexOf(node);
            if (i == -1) {
                size++;
                nodes.add(node);
            } else {
                nodes.set(i, node);
            }
        }
    }

    private class Node {
        String key;
        V val;

        public Node(String key, V val) {
            this.key = key;
            this.val = val;
        }

        @Override
        @SuppressWarnings("unchecked")
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return Objects.equals(key, node.key);
        }
    }

    private class LinkedIterator implements Iterator<V> {

        private int dataIndex = 0;

        private int entryIndex = 0;

        @Override
        public boolean hasNext() {
            if (data[dataIndex] != null && entryIndex < data[dataIndex].nodes.size() - 1) {
                return true;
            }

            for (int i = dataIndex + 1; i < data.length; i++) {
                if (data[i] != null && !data[i].nodes.isEmpty()) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public V get() {
            return data[dataIndex].nodes.get(entryIndex).val;
        }

        @Override
        public void set(V val) {
            data[dataIndex].nodes.get(entryIndex).val = val;
        }

        @Override
        public void next() {
            if (data[dataIndex] != null && entryIndex < data[dataIndex].nodes.size() - 1) {
                entryIndex++;
                return;
            }

            for (int i = dataIndex + 1; i < data.length; i++) {
                if (data[i] != null && !data[i].nodes.isEmpty()) {
                    dataIndex = i;
                    entryIndex = 0;
                    return;
                }
            }

            throw new IndexOutOfBoundsException("Следующего элемента не существует");
        }
    }
}
