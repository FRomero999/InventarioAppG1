package cesur.dam.inventarioappg1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private TextField txtDisponible;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtCategoria;


    /**
     * Guarda los datos introducidos en el formulario.
     * <p>
     * Comprueba que los campos de id, nombre, categoría y disponible no estén vacíos.
     * Si todos tienen contenido, muestra una alerta informativa con el nombre del
     * artículo, limpia los campos y deja el formulario listo para un nuevo registro.
     * Si algún campo está vacío, no realiza ninguna acción.
     * </p>
     *
     * @param actionEvent evento de acción generado al pulsar el botón guardar
     */
    @FXML
    public void guardar(ActionEvent actionEvent) {
        if (hayCamposVacios()) {
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Guardar");
        alert.setHeaderText("Guardando");
        alert.setContentText(txtNombre.getText());
        alert.showAndWait();
        txtId.clear();
        txtNombre.clear();
        txtCategoria.clear();
        txtDisponible.clear();
    }

    /**
     * Comprueba si alguno de los campos del formulario está vacío.
     *
     * @return {@code true} si al menos uno de los campos id, nombre, categoría
     *         o disponible está vacío; {@code false} en caso contrario
     */
    private boolean hayCamposVacios() {
        return txtId.getText().isEmpty()
                || txtNombre.getText().isEmpty()
                || txtCategoria.getText().isEmpty()
                || txtDisponible.getText().isEmpty();
    }

    /**
     * Cierra la aplicación.
     * <p>
     * Se invoca al pulsar el botón volver y finaliza el proceso con
     * {@link System#exit(int)} usando el código de salida {@code 0}.
     * </p>
     *
     * @param actionEvent evento de acción generado al pulsar el botón volver
     */
    @FXML
    public void volver(ActionEvent actionEvent) {
        System.exit(0);
    }
}
