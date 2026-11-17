package cesur.dam.inventarioappg1.controllers;

import cesur.dam.inventarioappg1.common.AppContext;
import cesur.dam.inventarioappg1.common.SceneNavigator;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @javafx.fxml.FXML
    public void login(ActionEvent actionEvent) {
        AppContext.getInstance().put("user",1);
        AppContext.getInstance().put("logged",true);
        SceneNavigator.navigateToInjection("main-view.fxml","Main");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
