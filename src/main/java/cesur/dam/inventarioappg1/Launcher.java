package cesur.dam.inventarioappg1;

import javafx.application.Application;

/**
 * Clase lanzadora de la aplicación.
 * <p>
 * Se utiliza para poder ejecutar la aplicación JavaFX desde un JAR sin
 * problemas de módulos, delegando el arranque real en {@link AppG1}.
 * </p>
 */
public class Launcher {

    /**
     * Método {@code main} que inicia la aplicación JavaFX.
     *
     * @param args argumentos de línea de comandos (no se utilizan)
     */
    public static void main(String[] args) {
        Application.launch(AppG1.class, args);
    }
}
