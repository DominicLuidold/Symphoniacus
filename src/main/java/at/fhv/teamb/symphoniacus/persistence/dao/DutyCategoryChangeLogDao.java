package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.DutyCategoryChangelogEntity;
import at.fhv.teamb.symphoniacus.persistence.model.DutyCategoryEntity;
import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;
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

    public boolean doesLogAlreadyExists(DutyEntity duty) {
        TypedQuery<Long> query = entityManager.createQuery(
            "SELECT COUNT(changelog) FROM DutyCategoryChangelogEntity changelog "
                + "WHERE changelog.dutyCategory = :givenCategory "
                + "AND changelog.startDate = :givenStartDate",
            Long.class
        );

        query.setParameter("givenCategory", duty.getDutyCategory());
        query.setParameter("givenStartDate", duty.getStart().toLocalDate());

        return (query.getSingleResult() >= 1);
    }

    public Optional<DutyCategoryChangelogEntity> getChangeLogByDetails(DutyEntity duty) {
        TypedQuery<DutyCategoryChangelogEntity> query = entityManager.createQuery(
            "SELECT changelog FROM DutyCategoryChangelogEntity changelog "
                + "WHERE changelog.dutyCategory = :givenCategory "
                + "AND changelog.startDate = :givenStartDate ",
            DutyCategoryChangelogEntity.class
        );

        query.setParameter("givenCategory", duty.getDutyCategory());
        query.setParameter("givenStartDate", duty.getStart().toLocalDate());

        return Optional.of(query.getSingleResult());
    }
}
