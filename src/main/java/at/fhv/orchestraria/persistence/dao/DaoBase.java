package at.fhv.orchestraria.persistence.dao;

import java.util.List;
import java.util.Optional;

/**
 * @author Team C
 */

public interface DaoBase<T> {
    /**
     * Get by ID. Search for an entity of the specified class and ID.
     * @param id The ID the entity refers to.
     * @return Returns an optional that contains either the entity or is empty in case there is no entity instance.
     */
    Optional<T> get(int id);

    /**
     * Get all entities of the specified class.
     * @return Returns a list that contains all the entities of the specified class.
     */
    List<T> getAll();

    /**
     *Makes an instance managed and persistent.
     * @param entity The specified entity instance
     */
    void save(T entity);

    /**
     *Updates the state of the given entity.
     * @param entity The specified entity instance
     * @return The updated entity
     */
    T update(T entity);

    /**
     *Removes the entity instance.
     * @param entity The specified entity instance
     */
    void delete(T entity);
}
