package cesur.dam.inventarioappg1;

import cesur.dam.inventarioappg1.common.SceneNavigator;
import cesur.dam.inventarioappg1.data.DataBaseInitializer;
import cesur.dam.inventarioappg1.data.DataSourceFactory;
import cesur.dam.inventarioappg1.data.EquipoJdbcDAO;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Clase principal de la aplicación JavaFX.
 * <p>
 * Prepara la base de datos (creación de la tabla y carga de datos iniciales),
 * configura el navegador de escenas con el {@link Stage} y el DAO de equipos,
 * y muestra la pantalla de inicio de sesión.
 * </p>
 */
public class AppG1 extends Application {

    /**
     * Punto de entrada de JavaFX. Se ejecuta automáticamente al arrancar la
     * aplicación.
     *
     * @param stage escenario principal que proporciona JavaFX y sobre el que se
     *              mostrarán las distintas vistas
     * @throws IOException si ocurre un error al cargar el FXML inicial
     */
    @Override
    public void start(Stage stage) throws IOException {

        // TODO:
        // - Implementar navegación completa entre las tres pantallas
        // - Realizar inyección de dependencia de los daos por constructor.
        // - Implementar las operaciones de borrado y actualización

        DataBaseInitializer.initialize(DataSourceFactory.getDataSource());
        DataBaseInitializer.loadData(DataSourceFactory.getDataSource());

        SceneNavigator.setStage(stage);
        SceneNavigator.setDAO(new EquipoJdbcDAO(DataSourceFactory.getDataSource()));
        SceneNavigator.navigateTo("login-view.fxml","Login");
        stage.show();
    }
}
