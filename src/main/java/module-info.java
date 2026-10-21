module org.example.inventarioappg1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens cesur.dam.inventarioappg1 to javafx.fxml;
    exports cesur.dam.inventarioappg1;
}