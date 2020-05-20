package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.orchestraria.domain.Imodel.IMusicianRole;
import at.fhv.orchestraria.domain.model.MusicianRoleEntity;
import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.dao.interfaces.IMusicianRoleDao;
import at.fhv.teamb.symphoniacus.persistence.model.MusicianRole;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicianEntity;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class MusicianRoleDao extends BaseDao<MusicianRoleEntity> implements IMusicianRoleDao {
    /**
     * Finds the object based on the provided primary key.
     *
     * @param key The primary key to use
     * @return The object
     */
    @Override
    public Optional<MusicianRoleEntity> find(Integer key) {
        return this.find(IMusicianEntity.class,key);
    }

    /**
     * Persists an object.
     *
     * @param elem The object to persist
     * @return Optional.empty if persisting not possible
     */
    @Override
    public Optional<MusicianRoleEntity> persist(MusicianRoleEntity elem) {
        return this.persist(IMusicianRole.class,elem);
    }

    /**
     * Updates an existing object.
     *
     * @param elem The object to update
     * @return Optional.empty if updating not possible
     */
    @Override
    public Optional<MusicianRoleEntity> update(MusicianRoleEntity elem) {
        return this.update(IMusicianRole.class,elem);
    }

    @Override
    public boolean remove(MusicianRoleEntity elem) {
        return false;
    }

    @Override
    public List<IMusicianRole> getAll() {
        return new LinkedList<>(this.getAll(MusicianRole.class));
    }
}
