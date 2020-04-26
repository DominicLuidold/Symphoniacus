package at.fhv.teamb.symphoniacus.persistence;

import java.util.Optional;

public interface Dao<T> {
    Optional<T> find(Integer key);

    Optional<T> persist(T elem);

    Optional<T> update(T elem);

    Boolean remove(T elem);
}
