package cesur.dam.inventarioappg1.controllers;

import cesur.dam.inventarioappg1.common.AppContext;
import cesur.dam.inventarioappg1.common.SceneNavigator;
import cesur.dam.inventarioappg1.data.DAO;
import cesur.dam.inventarioappg1.data.EquipoJdbcDAO;
import cesur.dam.inventarioappg1.models.Equipo;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private TableColumn<Equipo,String> cAvailability;
    @FXML
    private TableView<Equipo> table;
    @FXML
    private TableColumn<Equipo,String> cCategory;
    @FXML
    private TableColumn<Equipo,String> cName;
    @FXML
    private TableColumn<Equipo,String> cId;
@FXML
private Button btnEliminar;
@FXML
private Button btnModificar;


    private DAO<Equipo> equipoDAO;
    @FXML
    private BorderPane fondo;

    public MainController(EquipoJdbcDAO equipoJdbcDAO) {
        this.equipoDAO = equipoJdbcDAO;
    }

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

        actualizarTabla();
        btnEliminar.setDisable(true);
        btnModificar.setDisable(true);

        table.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection)->{
            AppContext.getInstance().put("equipo",newSelection);
            btnModificar.setDisable(false);
            btnEliminar.setDisable(false);

        });



        if(AppContext.getInstance().getDatos().containsKey("logged")){
            if((boolean) AppContext.getInstance().getDatos().get("logged")){
                System.out.println(AppContext.getInstance().get("logged"));
                System.out.println(AppContext.getInstance().get("user"));
            }
        }
    }

    private void actualizarTabla() {
        equipoDAO.findAll().forEach( (equipo)->{
            System.out.println(equipo);
            table.getItems().add(equipo);
        });
    }

    @FXML
    public void goBack(ActionEvent actionEvent) {
        SceneNavigator.navigateToInjection("login-view.fxml","login");
    }

    @FXML
    public void addNew(ActionEvent actionEvent) {
        AppContext.getInstance().delet();
        SceneNavigator.navigateToInjection("details-view.fxml","Nuevo equipo");
    }

    @FXML
    public void onEliminar(ActionEvent actionEvent) {
        equipoDAO.deleteById(table.getSelectionModel().getSelectedItem().getId());
        table.getItems().clear();
        actualizarTabla();
        btnEliminar.setDisable(true);
        btnModificar.setDisable(true);
        
    }

    @FXML
    public void onselect(InputMethodEvent inputMethodEvent) {

    }

    @FXML
    public void onModificar(ActionEvent actionEvent) {
        SceneNavigator.navigateToInjection("details-view.fxml","Modificar equipo");
    }

    @FXML
    public void onClicket(Event event) {
        table.getSelectionModel().clearSelection();
        AppContext.getInstance().delet();
        btnEliminar.setDisable(true);
        btnModificar.setDisable(true);
    }
}