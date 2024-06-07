module com.example.meteoserver {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.meteoserver to javafx.fxml;
    exports com.example.meteoserver;
}