package cesur.dam.inventarioappg1.controllers;

import cesur.dam.inventarioappg1.AppG1;
import cesur.dam.inventarioappg1.data.DataSourceFactory;
import cesur.dam.inventarioappg1.data.EquipoJdbcDAO;
import cesur.dam.inventarioappg1.models.Equipo;
import cesur.dam.inventarioappg1.ui.helper.MessageHelper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static cesur.dam.inventarioappg1.controllers.DetailsController.lop;

public class DetailsController implements Initializable {

    @FXML
    public Button btnNuevo;
    @FXML
    public Button btnSalir;
    @FXML
    private TableColumn<Equipo, String> cAvailability;
    @FXML
    private TableView<Equipo> table;
    @FXML
    private TableColumn<Equipo, String> cCategory;
    @FXML
    private TableColumn<Equipo, String> cName;
    @FXML
    private TableColumn<Equipo, String> cId;

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     *                  the root object was not localized.
     */

    static DataSource dao = DataSourceFactory.getDataSource();
    static EquipoJdbcDAO lop = new EquipoJdbcDAO(dao);
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        EquipoJdbcDAO lop = new EquipoJdbcDAO(dao);



        /* Inicialización de la tabla */
        cId.setCellValueFactory((row) -> {
            Equipo equipment = row.getValue();
            Long id = equipment.getId();
            return new SimpleStringProperty(String.valueOf(id));
        });
        cName.setCellValueFactory((row) -> {
            if (row.getValue().getNombre().isBlank()) {
                return new SimpleStringProperty("--");
            } else {
                return new SimpleStringProperty(String.valueOf(row.getValue().getNombre()));
            }
        });
        cCategory.setCellValueFactory((row) -> {
            return new SimpleStringProperty(String.valueOf(row.getValue().getCategoria()).toUpperCase());
        });
        cAvailability.setCellValueFactory((row) -> {
            var availability = row.getValue().getDisponible();
            String output = availability ? "disponible" : "no disponible";
            return new SimpleStringProperty(output);
        });
        if(!lop.findAll().isEmpty()) {
            for(Equipo lkl : lop.findAll()) {
                table.getItems().add(lkl);
            }
        }




    }

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

    /**
     * Cierra la aplicación.
     * <p>
     * Se invoca al pulsar el botón volver y finaliza el proceso con
     * {@link System#exit(int)} usando el código de salida {@code 0}.
     * </p>
     *
     * @param actionEvent evento de acción generado al pulsar el botón volver
     */
    @Deprecated
    public void goBack(ActionEvent actionEvent) throws IOException {

        ;
    }

    @FXML
    public void onNuevo(ActionEvent actionEvent) throws IOException {
        AppG1.setescena("views/create-view.fxml");

    }

    @FXML
    protected void onSalir(ActionEvent event) throws IOException {
        AppG1.setescena("views/login-view.fxml");
    }
}
