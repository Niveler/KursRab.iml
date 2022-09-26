module com.example.kursrab {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.kursrab to javafx.fxml;
    exports com.example.kursrab;
    exports com.example.kursrab.controllers;
    opens com.example.kursrab.controllers to javafx.fxml;
}