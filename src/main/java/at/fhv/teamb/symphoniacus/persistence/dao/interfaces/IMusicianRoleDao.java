package at.fhv.teamb.symphoniacus.persistence.dao.interfaces;

import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicianRole;
import java.util.List;
import java.util.Optional;

public interface IMusicianRoleDao {
    List<IMusicianRole> getAll();

    Optional<IMusicianRole> find(Integer key);

    Optional<IMusicianRole> persist(IMusicianRole elem);

    Optional<IMusicianRole> update(IMusicianRole elem);

    boolean remove(IMusicianRole elem);
}
