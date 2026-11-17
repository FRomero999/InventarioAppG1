package cesur.dam.inventarioappg1.models;

import lombok.Data;

/**
 * Entidad que representa un equipo del inventario.
 * <p>
 * Los métodos de acceso (getters y setters) se generan automáticamente
 * mediante la anotación {@link lombok.Data} de Lombok.
 * </p>
 */
@Data
public class Equipo {

    /** Identificador único del equipo en la base de datos. */
    private Long id;

    /** Nombre descriptivo del equipo. */
    private String nombre;

    /** Categoría a la que pertenece el equipo (por ejemplo, portátil o monitor). */
    private String categoria;

    /** Indica si el equipo está disponible para su uso. */
    private Boolean disponible;
}
