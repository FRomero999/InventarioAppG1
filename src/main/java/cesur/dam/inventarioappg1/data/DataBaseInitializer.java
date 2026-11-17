package cesur.dam.inventarioappg1.data;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.stream.Collectors;

/**
 * Utilidad para inicializar la base de datos de la aplicación.
 * <p>
 * Ejecuta los scripts SQL incluidos en los recursos: {@code init.sql} para
 * crear la estructura y {@code data.sql} para cargar datos de ejemplo.
 * </p>
 */
public class DataBaseInitializer {

    /**
     * Inicializa la estructura de la base de datos ejecutando el script
     * {@code init.sql}.
     *
     * @param ds origen de datos sobre el que se ejecuta el script
     */
    public static void initialize(DataSource ds){
        executeScript(ds, "init.sql");
    }

    /**
     * Carga los datos iniciales en la base de datos si la tabla {@code Equipo}
     * está vacía, usando el script {@code data.sql}.
     *
     * @param ds origen de datos sobre el que se ejecuta el script
     * @throws RuntimeException si ocurre un error de SQL
     */
    public static void loadData(DataSource ds){
        String countSql = "select count(*) from Equipo";

        try (Connection conn = ds.getConnection();
             PreparedStatement pst = conn.prepareStatement(countSql);
             ResultSet rs = pst.executeQuery()) {
            if (rs.next() && rs.getInt(1) == 0) {
                executeScript(ds, "data.sql");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Ejecuta todas las sentencias de un script SQL ubicado en los recursos.
     * <p>
     * Lee el fichero completo, lo divide por el carácter {@code ;} y ejecuta
     * cada sentencia no vacía.
     * </p>
     *
     * @param ds           origen de datos sobre el que se ejecuta el script
     * @param resourceName nombre del fichero SQL dentro de los recursos
     * @throws RuntimeException si el recurso no existe o si ocurre un error de
     *                          entrada/salida o de SQL
     */
    private static void executeScript(DataSource ds, String resourceName){
        try (InputStream is = DataBaseInitializer.class.getClassLoader().getResourceAsStream(resourceName)) {
            if (is == null) {
                throw new RuntimeException("No se encontró el recurso " + resourceName);
            }

            String sql;
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(is, StandardCharsets.UTF_8))) {
                sql = reader.lines().collect(Collectors.joining("\n"));
            }

            try (Connection conn = ds.getConnection();
                 Statement st = conn.createStatement()) {
                for (String statement : sql.split(";")) {
                    if (!statement.isBlank()) {
                        st.execute(statement);
                    }
                }
            }
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
