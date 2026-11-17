package cesur.dam.inventarioappg1.data;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz genérica de acceso a datos (Data Access Object).
 * <p>
 * Define las operaciones básicas de persistencia (CRUD) que debe implementar
 * cualquier DAO concreto para una entidad {@code T}.
 * </p>
 *
 * @param <T> tipo de entidad que gestiona el DAO
 */
public interface DAO<T> {

    /**
     * Busca una entidad por su identificador.
     *
     * @param id identificador de la entidad
     * @return un {@link Optional} con la entidad encontrada, o vacío si no existe
     */
    Optional<T> findById(Long id);

    /**
     * Recupera todas las entidades almacenadas.
     *
     * @return lista con todas las entidades
     */
    List<T> findAll();

    /**
     * Guarda una nueva entidad.
     *
     * @param obj entidad a guardar
     * @return la entidad guardada, normalmente con su identificador asignado
     */
    T save(T obj);

    /**
     * Actualiza una entidad existente.
     *
     * @param entity entidad con los datos actualizados; debe tener identificador
     */
    void update(T entity);

    /**
     * Elimina la entidad con el identificador indicado.
     *
     * @param id identificador de la entidad a eliminar
     */
    void deleteById(Long id);
}
