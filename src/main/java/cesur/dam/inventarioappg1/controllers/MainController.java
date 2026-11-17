package cesur.dam.inventarioappg1.controllers;

import cesur.dam.inventarioappg1.common.AppContext;
import cesur.dam.inventarioappg1.common.SceneNavigator;
import cesur.dam.inventarioappg1.data.DAO;
import cesur.dam.inventarioappg1.data.EquipoJdbcDAO;
import cesur.dam.inventarioappg1.models.Equipo;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controlador de la vista principal.
 * <p>
 * Muestra en una tabla todos los equipos almacenados y permite navegar a la
 * vista de detalle para modificar un equipo seleccionado o para crear uno
 * nuevo.
 * </p>
 */
public class MainController implements Initializable {

    /** Columna que muestra si el equipo está disponible. */
    @FXML
    private TableColumn<Equipo,String> cAvailability;

    /** Tabla con el listado de equipos. */
    @FXML
    private TableView<Equipo> table;

    /** Columna que muestra la categoría del equipo. */
    @FXML
    private TableColumn<Equipo,String> cCategory;

    /** Columna que muestra el nombre del equipo. */
    @FXML
    private TableColumn<Equipo,String> cName;

    /** Columna que muestra el identificador del equipo. */
    @FXML
    private TableColumn<Equipo,String> cId;

    /** DAO utilizado para obtener los equipos. */
    private DAO<Equipo> equipoDAO;

    /**
     * Crea el controlador inyectando el DAO de equipos.
     *
     * @param equipoJdbcDAO DAO de equipos que se usará para consultar los datos
     */
    public MainController(EquipoJdbcDAO equipoJdbcDAO) {
        this.equipoDAO = equipoJdbcDAO;
    }

    /**
     * Se invoca automáticamente después de cargar el FXML.
     * <p>
     * Configura las columnas de la tabla, carga en ella todos los equipos y
     * registra un listener para abrir la vista de detalle al seleccionar una
     * fila. También muestra por consola la información de sesión si el usuario
     * ha iniciado sesión.
     * </p>
     *
     * @param location  ubicación usada para resolver rutas relativas, o
     *                  {@code null} si no se conoce
     * @param resources recursos usados para localizar la vista, o {@code null}
     *                  si no está localizada
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /* Inicialización de la tabla */
        cId.setCellValueFactory( (row)->{
            Equipo equipment = row.getValue();
            Long  id = equipment.getId();
            return new SimpleStringProperty(String.valueOf(id));
        });
        cName.setCellValueFactory( (row)->{
            if(row.getValue().getNombre().isBlank()){
                return new SimpleStringProperty("--");
            } else  {
                return new SimpleStringProperty(String.valueOf(row.getValue().getNombre()));
            }
        });
        cCategory.setCellValueFactory( (row)->{
            return new SimpleStringProperty(String.valueOf(row.getValue().getCategoria()).toUpperCase());
        });
        cAvailability.setCellValueFactory( (row)->{
            var availability = row.getValue().getDisponible();
            String output = availability? "disponible":"no disponible";
            return new SimpleStringProperty(output);
        });

        equipoDAO.findAll().forEach( (equipo)->{
            System.out.println(equipo);
            table.getItems().add(equipo);
        });

        table.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection)->{
            AppContext.getInstance().put("equipo",newSelection);
            SceneNavigator.navigateToInjection("details-view.fxml","Modificar equipo");
        });

        if(AppContext.getInstance().getDatos().containsKey("logged")){
            if((boolean) AppContext.getInstance().getDatos().get("logged")){
                System.out.println(AppContext.getInstance().get("logged"));
                System.out.println(AppContext.getInstance().get("user"));
            }
        }
    }

    /**
     * Gestiona la acción de volver. Actualmente no realiza ninguna acción.
     *
     * @param actionEvent evento de acción generado al pulsar el botón volver
     */
    @FXML
    public void goBack(ActionEvent actionEvent) {
    }

    /**
     * Abre la vista de detalle para crear un equipo nuevo.
     * <p>
     * Elimina cualquier equipo seleccionado del {@link AppContext} para que el
     * formulario de detalle se muestre vacío.
     * </p>
     *
     * @param actionEvent evento de acción generado al pulsar el botón añadir
     */
    @FXML
    public void addNew(ActionEvent actionEvent) {
        AppContext.getInstance().getDatos().remove("equipo");
        SceneNavigator.navigateToInjection("details-view.fxml","Nuevo equipo");
    }
}
