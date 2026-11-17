package cesur.dam.inventarioappg1.common;

import cesur.dam.inventarioappg1.AppG1;
import cesur.dam.inventarioappg1.controllers.DetailsController;
import cesur.dam.inventarioappg1.controllers.LoginController;
import cesur.dam.inventarioappg1.controllers.MainController;
import cesur.dam.inventarioappg1.data.DAO;
import cesur.dam.inventarioappg1.data.EquipoJdbcDAO;
import cesur.dam.inventarioappg1.models.Equipo;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase utilitaria para navegar entre las vistas FXML de la aplicación.
 * <p>
 * Mantiene una única referencia al {@link Stage} compartido y cambia la escena
 * mostrada en él cada vez que se solicita una nueva vista. Todos sus miembros
 * son estáticos, por lo que puede usarse desde cualquier punto sin necesidad de
 * crear una instancia.
 * </p>
 */
public class SceneNavigator {

    /**
     * Escenario principal de la aplicación sobre el que se muestran las escenas.
     */
    private static Stage stage;

    /**
     * Caché de escenas ya cargadas, indexada por el nombre del fichero FXML.
     */
    private static final Map<String, Scene> sceneCache = new HashMap<>();

    /** DAO de equipos compartido por los controladores de las vistas. */
    private static DAO<Equipo> equipoDAO;

    /**
     * Registra el DAO de equipos que se inyectará en los controladores.
     *
     * @param equipoDAO DAO de equipos que usarán las vistas
     */
    public static void setDAO(DAO<Equipo> equipoDAO) {
        SceneNavigator.equipoDAO = equipoDAO;
    }

    /**
     * Registra el escenario que se utilizará en las siguientes navegaciones.
     *
     * @param stage el {@link Stage} principal de la aplicación
     */
    public static void setStage(Stage stage) {
        SceneNavigator.stage = stage;
    }

    /**
     * Carga la vista FXML indicada y la muestra en el escenario registrado.
     * <p>
     * El fichero FXML se busca dentro del paquete {@code views/} de {@link AppG1}.
     * </p>
     *
     * @param fxml  nombre del fichero FXML a cargar (relativo a {@code views/})
     * @param title título de la ventana que se asignará al escenario
     * @throws RuntimeException si no se puede cargar el recurso FXML (envuelve
     *                          la {@link IOException})
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
     * Carga la vista FXML indicada y la muestra en el escenario registrado,
     * reutilizando una escena ya cargada si existe.
     * <p>
     * Las escenas se guardan en caché en un {@link HashMap} indexado por el
     * nombre del fichero FXML, de modo que cada vista solo se carga una vez
     * (junto con su controlador y su estado).
     * </p>
     *
     * @param fxml  nombre del fichero FXML a cargar (relativo a {@code views/})
     * @param title título de la ventana que se asignará al escenario
     * @throws RuntimeException si no se puede cargar el recurso FXML (envuelve
     *                          la {@link IOException})
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
     * Carga la vista FXML indicada y la muestra en el escenario registrado,
     * creando su controlador mediante una factoría para poder inyectarle
     * dependencias en el constructor.
     * <p>
     * La factoría crea el controlador adecuado según el fichero FXML y le pasa
     * el DAO de equipos compartido ({@link #equipoDAO}), aplicando así una
     * inyección de dependencias sencilla.
     * </p>
     *
     * @param fxml  nombre del fichero FXML a cargar (relativo a {@code views/})
     * @param title título de la ventana que se asignará al escenario
     * @throws RuntimeException si no se puede cargar el recurso FXML (envuelve
     *                          la {@link IOException})
     * @throws IllegalArgumentException si el fichero FXML no corresponde a
     *                                  ninguna vista soportada
     */
    public static void navigateToInjection(String fxml, String title) {
        FXMLLoader fxmlLoader = new FXMLLoader(AppG1.class.getResource("views/" + fxml));

        fxmlLoader.setControllerFactory(type -> {
            switch (fxml) {
                case "login-view.fxml":   return new LoginController();
                case "main-view.fxml":    return new MainController( (EquipoJdbcDAO) SceneNavigator.equipoDAO);
                case "details-view.fxml": return new DetailsController( SceneNavigator.equipoDAO);
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
