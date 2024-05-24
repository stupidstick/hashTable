package com.example.hashtable.visual;

import com.example.hashtable.table.AddressHashTable;
import com.example.hashtable.table.HashTable;
import com.example.hashtable.table.LinkedHashTable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    private final Map<TableType, HashTable<String, String>> hashTables = new EnumMap<>(TableType.class);
    private final Map<TableType, TableView<TableData>> tableViews = new EnumMap<>(TableType.class);

    {
        hashTables.put(TableType.ADDRESS, new AddressHashTable<>(32));
        hashTables.put(TableType.LINKED, new LinkedHashTable<>(32));
    }


    private TableType tableType = TableType.LINKED;

    @FXML
    private TextField keyField;

    @FXML
    private TextField valueField;

    @FXML
    private TableView<TableData> addressHashTable;

    @FXML
    private TableView<TableData> linkedHashTable;

    @FXML
    private TableColumn<TableData, String> addressKeyColumn;

    @FXML
    private TableColumn<TableData, String> linkedKeyColumn;

    @FXML
    private TableColumn<TableData, String> addressValueColumn;

    @FXML
    private TableColumn<TableData, String> linkedValueColumn;

    @FXML
    private TableColumn<TableData, String> addressIndexColumn;

    @FXML
    private TableColumn<TableData, String> linkedIndexColumn;

    @FXML
    private ComboBox<String> tableTypeBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeComboBox();
        initializeComboBoxListener();
        initializeAddressTable();
        initializeLinkedTable();
        initializeTableViewsMap();
    }

    @FXML
    private void put() {
        if (!validateKey(keyField.getText())) {
            return;
        }
        var hashTable = hashTables.get(tableType);
        hashTable.put(keyField.getText(), valueField.getText());
        var tableView = tableViews.get(tableType);
        tableView.getItems().setAll(hashTable.convertToTableData());
    }

    @FXML
    private void delete() {
        if (!validateKey(keyField.getText())) {
            return;
        }
        var hashTable = hashTables.get(tableType);
        hashTable.remove(keyField.getText());
        var tableView = tableViews.get(tableType);
        tableView.getItems().setAll(hashTable.convertToTableData());
    }

    @FXML
    private void clear() {
        var hashTable = hashTables.get(tableType);
        hashTable.clear();
        var tableView = tableViews.get(tableType);
        tableView.getItems().setAll(hashTable.convertToTableData());
    }

    private boolean validateKey(String key) {
        return key.matches("^[A-Z]+$");
    }

    private void initializeComboBox() {
        tableTypeBox.getSelectionModel().select(tableType.toString());
        tableTypeBox.getItems()
                .addAll(
                        Arrays.stream(TableType.values())
                                .map(Enum::toString)
                                .toList()
                );
    }

    private void initializeComboBoxListener() {
        tableTypeBox.getSelectionModel()
                .selectedItemProperty()
                .addListener((observableValue, s, newType) -> tableType = TableType.valueOf(newType));
    }

    private void initializeAddressTable() {
        addressIndexColumn.setCellValueFactory(new PropertyValueFactory<>("index"));
        addressKeyColumn.setCellValueFactory(new PropertyValueFactory<>("key"));
        addressValueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
    }

    private void initializeLinkedTable() {
        linkedIndexColumn.setCellValueFactory(new PropertyValueFactory<>("index"));
        linkedKeyColumn.setCellValueFactory(new PropertyValueFactory<>("key"));
        linkedValueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
    }

    private void initializeTableViewsMap() {
        tableViews.put(TableType.LINKED, linkedHashTable);
        tableViews.put(TableType.ADDRESS, addressHashTable);
    }
}