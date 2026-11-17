module org.example.inventarioappg1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires mysql.connector.j;
    requires java.naming;
    requires java.sql;

    opens cesur.dam.inventarioappg1 to javafx.fxml;
    exports cesur.dam.inventarioappg1;
    exports cesur.dam.inventarioappg1.models;
    opens cesur.dam.inventarioappg1.models to javafx.fxml;
    exports cesur.dam.inventarioappg1.controllers;
    opens cesur.dam.inventarioappg1.controllers to javafx.fxml;
    exports cesur.dam.inventarioappg1.common;
    opens cesur.dam.inventarioappg1.common to javafx.fxml;
}