package cesur.dam.inventarioappg1.controllers;

import cesur.dam.inventarioappg1.AppG1;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;


public class LoginController {

    @javafx.fxml.FXML
    private Button btnlogin;

    @javafx.fxml.FXML
    public void onbtnlogin(ActionEvent actionEvent) throws IOException {
       AppG1.setescena(("views/details-view.fxml"));
    }
}
