package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.SectionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISectionEntity;
import java.util.List;
import java.util.Optional;
import javax.persistence.TypedQuery;

/**
 * DAO for SectionEntity.
 *
 * @author Dominic Luidold
 */
public class SectionDao extends BaseDao<ISectionEntity> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ISectionEntity> find(Integer key) {
        return this.find(SectionEntity.class, key);
    }

    /**
     * Returns all {@link SectionEntity} objects.
     *
     * @return A List of sections
     */
    public List<ISectionEntity> getAll() {
        TypedQuery<ISectionEntity> query = entityManager.createQuery(
            "SELECT s FROM SectionEntity s",
            SectionEntity.class
        );

        return query.getResultList();
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
