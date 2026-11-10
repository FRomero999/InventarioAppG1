package cesur.dam.inventarioappg1.controllers;

import cesur.dam.inventarioappg1.SceneNavigator;
import javafx.event.ActionEvent;

public class LoginController {

    @javafx.fxml.FXML
    public void login(ActionEvent actionEvent) {
        SceneNavigator.navigateTo("main-view.fxml","Main");
    }
}
