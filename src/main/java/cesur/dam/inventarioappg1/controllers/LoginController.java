package cesur.dam.inventarioappg1.controllers;

import cesur.dam.inventarioappg1.common.AppContext;
import cesur.dam.inventarioappg1.common.SceneNavigator;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controlador de la pantalla de inicio de sesión.
 * <p>
 * Simula el inicio de sesión guardando en el {@link AppContext} el usuario y
 * el estado de sesión, y a continuación navega a la vista principal.
 * </p>
 */
public class LoginController implements Initializable {

    /**
     * Realiza el inicio de sesión y abre la vista principal.
     *
     * @param actionEvent evento de acción generado al pulsar el botón de acceso
     */
    @javafx.fxml.FXML
    public void login(ActionEvent actionEvent) {
        AppContext.getInstance().put("user",1);
        AppContext.getInstance().put("logged",true);
        SceneNavigator.navigateToInjection("main-view.fxml","Main");
    }

    /**
     * Se invoca automáticamente después de cargar el FXML. No requiere
     * inicialización adicional en esta vista.
     *
     * @param location  ubicación usada para resolver rutas relativas, o
     *                  {@code null} si no se conoce
     * @param resources recursos usados para localizar la vista, o {@code null}
     *                  si no está localizada
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
