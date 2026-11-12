package cesur.dam.inventarioappg1;

import cesur.dam.inventarioappg1.data.DataSourceFactory;
import cesur.dam.inventarioappg1.data.EquipoJdbcDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AppG1 extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        // TODO:
        // - Implementar navegación completa entre las tres pantallas
        // - Realizar inyección de dependencia de los daos por constructor.
        // - Implementar las operaciones de borrado y actualización

        SceneNavigator.setStage(stage);
        SceneNavigator.setDAO(new EquipoJdbcDAO(DataSourceFactory.getDataSource()));
        SceneNavigator.navigateTo("login-view.fxml","Login");
        stage.show();
    }
}
