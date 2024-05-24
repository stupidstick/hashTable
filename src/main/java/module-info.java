module com.example.hashtable {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.hashtable to javafx.fxml;
    exports com.example.hashtable;
    exports com.example.hashtable.visual;
    opens com.example.hashtable.visual to javafx.fxml;
}