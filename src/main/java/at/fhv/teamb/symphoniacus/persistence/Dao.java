package at.fhv.teamb.symphoniacus.persistence;

import java.util.Optional;

public interface Dao<T> {

    /**
     * Finds the object based on the provided primary key.
     *
     * @param key The primary key to use
     * @return The object
     */
    Optional<T> find(Integer key);

    /**
     * Persists an object.
     *
     * @param elem The object to persist
     * @return Optional.empty if persisting not possible
     */
    Optional<T> persist(T elem);

    /**
     * Updates an existing object.
     *
     * @param elem The object to update
     * @return Optional.empty if updating not possible
     */
    Optional<T> update(T elem);

    boolean remove(T elem);
}
