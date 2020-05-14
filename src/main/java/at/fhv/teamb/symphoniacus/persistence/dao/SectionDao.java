package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.SectionEntity;
import java.util.List;
import java.util.Optional;
import javax.persistence.TypedQuery;

/**
 * DAO for SectionEntity.
 *
 * @author Dominic Luidold
 */
public class SectionDao extends BaseDao<SectionEntity> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<SectionEntity> find(Integer key) {
        return this.find(SectionEntity.class, key);
    }

    /**
     * Returns all {@link SectionEntity} objects.
     *
     * @return A List of sections
     */
    public List<SectionEntity> getAll() {
        TypedQuery<SectionEntity> query = entityManager.createQuery(
            "SELECT s FROM SectionEntity s",
            SectionEntity.class
        );

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<SectionEntity> persist(SectionEntity elem) {
        return this.persist(SectionEntity.class, elem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<SectionEntity> update(SectionEntity elem) {
        return this.update(SectionEntity.class, elem);
    }

    @Override
    public boolean remove(SectionEntity elem) {
        return false;
    }
}
