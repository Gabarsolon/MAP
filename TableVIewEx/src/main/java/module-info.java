module com.example.tableviewex {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.tableviewex to javafx.fxml;
    exports com.example.tableviewex;
}