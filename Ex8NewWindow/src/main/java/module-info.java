module com.example.ex8newwindow {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.ex8newwindow to javafx.fxml;
    exports com.example.ex8newwindow;
}