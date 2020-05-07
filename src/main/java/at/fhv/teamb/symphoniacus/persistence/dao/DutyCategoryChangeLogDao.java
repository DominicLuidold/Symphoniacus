package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.DutyCategoryChangelogEntity;
import at.fhv.teamb.symphoniacus.persistence.model.DutyCategoryEntity;
import java.util.List;
import java.util.Optional;
import javax.persistence.TypedQuery;

/**
 * DAO for DutyCategoryChangeLog.
 *
 * @author Nino Heinzle
 */
public class DutyCategoryChangeLogDao extends BaseDao<DutyCategoryChangelogEntity> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<DutyCategoryChangelogEntity> find(Integer key) {
        return this.find(DutyCategoryChangelogEntity.class, key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<DutyCategoryChangelogEntity> persist(DutyCategoryChangelogEntity elem) {
        return this.persist(DutyCategoryChangelogEntity.class, elem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<DutyCategoryChangelogEntity> update(DutyCategoryChangelogEntity elem) {
        return this.update(DutyCategoryChangelogEntity.class, elem);
    }

    @Override
    public boolean remove(DutyCategoryChangelogEntity elem) {
        return false;
    }

    /**
     * Finds all DutyCategoryChangelogs matching the id of the given DutyCategoryEntity.
     *
     * @param categoryEntity is the given DutyCategoryEntity
     * @return A List of DutyCategoryChangelogEntity
     */
    public List<DutyCategoryChangelogEntity> getDutyCategoryChangeLog(
        DutyCategoryEntity categoryEntity) {
        TypedQuery<DutyCategoryChangelogEntity> query = entityManager.createQuery(
            "SELECT changelog FROM DutyCategoryChangelogEntity changelog "
                + "WHERE changelog.dutyCategory = :givenCategory",
            DutyCategoryChangelogEntity.class
        );

        query.setParameter("givenCategory", categoryEntity);

        return query.getResultList();
    }
}
