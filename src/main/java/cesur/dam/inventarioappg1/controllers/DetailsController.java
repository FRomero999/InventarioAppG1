package cesur.dam.inventarioappg1.controllers;

import cesur.dam.inventarioappg1.common.AppContext;
import cesur.dam.inventarioappg1.common.SceneNavigator;
import cesur.dam.inventarioappg1.data.DAO;
import cesur.dam.inventarioappg1.data.EquipoJdbcDAO;
import cesur.dam.inventarioappg1.models.Equipo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controlador de la vista de detalle de un equipo.
 * <p>
 * Permite consultar, crear, modificar y eliminar un equipo. Si en el
 * {@link AppContext} existe un equipo seleccionado, el formulario se rellena
 * con sus datos y se actualiza; en caso contrario se crea uno nuevo.
 * </p>
 */
public class DetailsController implements Initializable {

    /** Etiqueta de bienvenida de la vista. */
    @FXML
    private Label welcomeText;

    /** Campo de texto con el nombre del equipo. */
    @FXML
    private TextField txtName;

    /** Selector de la categoría del equipo. */
    @FXML
    private ChoiceBox<String> choiceCategory;

    /** Casilla que indica si el equipo está disponible. */
    @FXML
    private CheckBox chkAvailable;

    /** DAO utilizado para persistir los equipos. */
    private DAO<Equipo>  equipoDAO;

    /**
     * Crea el controlador inyectando el DAO de equipos.
     *
     * @param dao DAO de equipos que se usará para guardar, actualizar y borrar
     */
    public DetailsController(DAO<Equipo> dao) {
        this.equipoDAO = dao;
    }

    /**
     * Se invoca automáticamente después de cargar el FXML.
     * <p>
     * Rellena el selector de categorías y, si hay un equipo seleccionado en el
     * {@link AppContext}, carga sus datos en el formulario.
     * </p>
     *
     * @param location  ubicación usada para resolver rutas relativas, o
     *                  {@code null} si no se conoce
     * @param resources recursos usados para localizar la vista, o {@code null}
     *                  si no está localizada
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //spinnerId.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, 0,1));

        /*
            ChoiceBox  \
            ListView    \ Observable .getItems() .setItems()
            TableView   /

         */

        ObservableList<String> categories = FXCollections.observableArrayList();
        choiceCategory.setItems(categories);

        categories.add("Keyboard");
        categories.add("Laptop");

        choiceCategory.getItems().add("Gamepad");
        choiceCategory.getItems().add("Monitor");
        choiceCategory.getItems().add("Computer");
        choiceCategory.getItems().add("Printer");

        choiceCategory.getSelectionModel().selectFirst();

        if(AppContext.getInstance().getDatos().containsKey("equipo")){
            Equipo equipo = (Equipo) AppContext.getInstance().get("equipo");
            txtName.setText(equipo.getNombre());
            choiceCategory.setValue(equipo.getCategoria());
            chkAvailable.setSelected(equipo.getDisponible());
            //spinnerId.getValueFactory().setValue(Math.toIntExact(equipo.getId()));
        }

    }

    /**
     * Guarda los datos introducidos en el formulario.
     * <p>
     * Comprueba que el nombre no esté vacío. Si hay un equipo seleccionado en el
     * {@link AppContext}, actualiza sus datos; en caso contrario crea un equipo
     * nuevo. Al terminar, vuelve a la vista principal.
     * </p>
     *
     * @param actionEvent evento de acción generado al pulsar el botón guardar
     */
    @FXML
    public void save(ActionEvent actionEvent) {
        if (hasEmptyFields()) {
            return;
        }

        String name = txtName.getText();
        String category = choiceCategory.getSelectionModel().getSelectedItem();

        if(AppContext.getInstance().getDatos().containsKey("equipo")) {
            Equipo equipment = (Equipo) AppContext.getInstance().get("equipo");
            equipment.setNombre(name);
            equipment.setCategoria(category);
            equipment.setDisponible(chkAvailable.isSelected());

            equipoDAO.update(equipment);
            SceneNavigator.navigateToInjection("main-view.fxml","Main");
        }else{
            Equipo equipment = new Equipo();
            equipment.setNombre(name);
            equipment.setCategoria(category);
            equipment.setDisponible(chkAvailable.isSelected());

            equipoDAO.save(equipment);
            SceneNavigator.navigateToInjection("main-view.fxml","Main");
        }
    }

    /**
     * Comprueba si el campo del nombre está vacío.
     *
     * @return {@code true} si el campo del nombre está vacío;
     *         {@code false} en caso contrario
     */
    private boolean hasEmptyFields() {
        return txtName.getText().isEmpty();
    }

    /**
     * Vuelve a la vista principal sin guardar los cambios.
     *
     * @param actionEvent evento de acción generado al pulsar el botón volver
     */
    @FXML
    public void goBack(ActionEvent actionEvent) {
        SceneNavigator.navigateToInjection("main-view.fxml","Vista principal");
    }

    /**
     * Elimina el equipo seleccionado y vuelve a la vista principal.
     * <p>
     * Solo actúa si existe un equipo en el {@link AppContext}; en ese caso lo
     * borra de la base de datos mediante el DAO.
     * </p>
     *
     * @param actionEvent evento de acción generado al pulsar el botón eliminar
     */
    @FXML
    public void delete(ActionEvent actionEvent) {
        if(AppContext.getInstance().getDatos().containsKey("equipo")) {
            Equipo equipment = (Equipo) AppContext.getInstance().get("equipo");
            equipoDAO.deleteById(equipment.getId());
            SceneNavigator.navigateToInjection("main-view.fxml","Main");
        }
    }
}
