package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.dao.interfaces.IMusicianRoleDao;
import at.fhv.teamb.symphoniacus.persistence.model.MusicianRole;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicianRole;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class MusicianRoleDao extends BaseDao<IMusicianRole> implements IMusicianRoleDao {

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IMusicianRole> find(Integer key) {
        return this.find(IMusicianEntity.class, key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IMusicianRole> persist(IMusicianRole elem) {
        return this.persist(IMusicianRole.class, elem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IMusicianRole> update(IMusicianRole elem) {
        return this.update(IMusicianRole.class, elem);
    }

    @Override
    public boolean remove(IMusicianRole elem) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized List<IMusicianRole> getAll() {
        return new LinkedList<>(this.getAll(MusicianRole.class));
    }
}
