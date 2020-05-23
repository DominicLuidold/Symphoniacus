package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.dao.interfaces.IMusicianRoleDao;
import at.fhv.teamb.symphoniacus.persistence.model.MusicianRoleEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicianRoleEntity;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class MusicianRoleDao extends BaseDao<IMusicianRoleEntity> implements IMusicianRoleDao {

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IMusicianRoleEntity> find(Integer key) {
        return this.find(IMusicianEntity.class, key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IMusicianRoleEntity> persist(IMusicianRoleEntity elem) {
        return this.persist(IMusicianRoleEntity.class, elem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IMusicianRoleEntity> update(IMusicianRoleEntity elem) {
        return this.update(IMusicianRoleEntity.class, elem);
    }

    @Override
    public boolean remove(IMusicianRoleEntity elem) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized List<IMusicianRoleEntity> getAll() {
        return new LinkedList<>(this.getAll(MusicianRoleEntity.class));
    }
}
