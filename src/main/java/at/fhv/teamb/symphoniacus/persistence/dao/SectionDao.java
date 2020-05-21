package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.dao.interfaces.ISectionDao;
import at.fhv.teamb.symphoniacus.persistence.model.SectionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISectionEntity;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import javax.persistence.TypedQuery;

/**
 * DAO for SectionEntity.
 *
 * @author Dominic Luidold
 */
public class SectionDao extends BaseDao<ISectionEntity>
    implements ISectionDao {

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ISectionEntity> find(Integer key) {
        return this.find(ISectionEntity.class, key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ISectionEntity> getAll() {
        TypedQuery<SectionEntity> query = entityManager.createQuery(
            "SELECT s FROM SectionEntity s",
            SectionEntity.class
        );

        return new LinkedList<>(query.getResultList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ISectionEntity> persist(ISectionEntity elem) {
        return this.persist(SectionEntity.class, elem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ISectionEntity> update(ISectionEntity elem) {
        return this.update(SectionEntity.class, elem);
    }

    @Override
    public boolean remove(ISectionEntity elem) {
        return false;
    }

}
