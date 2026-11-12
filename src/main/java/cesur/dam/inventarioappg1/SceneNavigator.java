package cesur.dam.inventarioappg1;

import cesur.dam.inventarioappg1.controllers.DetailsController;
import cesur.dam.inventarioappg1.controllers.LoginController;
import cesur.dam.inventarioappg1.controllers.MainController;
import cesur.dam.inventarioappg1.data.DAO;
import cesur.dam.inventarioappg1.data.DataSourceFactory;
import cesur.dam.inventarioappg1.data.EquipoJdbcDAO;
import cesur.dam.inventarioappg1.models.Equipo;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for navigating between FXML views in the application.
 * <p>
 * It holds a single shared {@link Stage} reference and swaps the scene shown on it
 * whenever a new view is requested. All members are static, so the navigator can be
 * used from anywhere without needing an instance.
 */
public class SceneNavigator {

    /**
     * The main application stage on which scenes are displayed.
     */
    private static Stage stage;

    /**
     * Cache of already loaded scenes, keyed by FXML file name.
     */
    private static final Map<String, Scene> sceneCache = new HashMap<>();
    private static DAO<Equipo> equipoDAO;

    /**
     * Registers the stage that will be used for subsequent scene navigation.
     *
     * @param stage the main application {@link Stage}
     */

    public static void setDAO(DAO<Equipo> equipoDAO) {
        SceneNavigator.equipoDAO = equipoDAO;
    }

    public static void setStage(Stage stage) {
        SceneNavigator.stage = stage;
    }

    /**
     * Loads the given FXML view and displays it on the registered stage.
     * <p>
     * The FXML file is resolved relative to the {@code views/} package of {@link AppG1}.
     *
     * @param fxml  file name of the FXML view to load (resolved under {@code views/})
     * @param title window title to set on the stage
     * @throws RuntimeException if the FXML resource fails to load (wraps the {@link IOException})
     */
    public static void navigateTo(String fxml,String title){
        FXMLLoader fxmlLoader = new FXMLLoader(AppG1.class.getResource("views/"+fxml));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
            stage.setTitle(title);
            stage.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Loads the given FXML view and displays it on the registered stage, reusing a
     * previously loaded scene when available.
     * <p>
     * Scenes are cached in a {@link HashMap} keyed by the FXML file name, so each view
     * is only loaded once (along with its controller and state).
     *
     * @param fxml  file name of the FXML view to load (resolved under {@code views/})
     * @param title window title to set on the stage
     * @throws RuntimeException if the FXML resource fails to load (wraps the {@link IOException})
     */
    public static void navigateToCache(String fxml, String title) {
        Scene scene = sceneCache.computeIfAbsent(fxml, key -> {
            FXMLLoader fxmlLoader = new FXMLLoader(AppG1.class.getResource("views/" + key));
            try {
                return new Scene(fxmlLoader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        stage.setTitle(title);
        stage.setScene(scene);
    }

    /**
     * Loads the given FXML view and displays it on the registered stage, creating its
     * controller through a controller factory so dependencies can be injected in the
     * constructor.
     * <p>
     * The factory currently uses the no-argument constructors of the existing
     * controllers; dependency injection can be added per view in the switch.
     *
     * @param fxml  file name of the FXML view to load (resolved under {@code views/})
     * @param title window title to set on the stage
     * @throws RuntimeException if the FXML resource fails to load (wraps the {@link IOException})
     */
    public static void navigateToInjection(String fxml, String title) {
        FXMLLoader fxmlLoader = new FXMLLoader(AppG1.class.getResource("views/" + fxml));

        fxmlLoader.setControllerFactory(type -> {
            switch (fxml) {
                case "login-view.fxml":   return new LoginController();
                case "main-view.fxml":    return new MainController( (EquipoJdbcDAO) SceneNavigator.equipoDAO);
                case "details-view.fxml": return new DetailsController();
            }
            throw new IllegalArgumentException("Vista no soportada: " + fxml);
        });

        try {
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle(title);
            stage.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
