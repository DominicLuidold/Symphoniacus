package at.fhv.teamb.symphoniacus.persistence;

import java.util.Optional;

public interface DAO<T> {
    Optional<T> find(Object key);

    Optional<T> persist(T elem);

    Optional<T> update(T elem);

    Boolean remove(T elem);
}
