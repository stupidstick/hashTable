package com.example.hashtable.visual;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TableData {

    private final StringProperty index;

    private final StringProperty key;

    private final StringProperty value;


    public TableData(int index, String key, String value) {
        this.index = new SimpleStringProperty(String.valueOf(index));
        this.key = new SimpleStringProperty(key);
        this.value = new SimpleStringProperty(value);
    }

    public String getIndex() {
        return index.get();
    }

    public StringProperty indexProperty() {
        return index;
    }

    public String getKey() {
        return key.get();
    }

    public StringProperty keyProperty() {
        return key;
    }

    public String getValue() {
        return value.get();
    }

    public StringProperty valueProperty() {
        return value;
    }
}
