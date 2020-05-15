package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.dao.interfaces.IDutyCategoryChangeLogDao;
import at.fhv.teamb.symphoniacus.persistence.model.DutyCategoryChangelogEntity;
import at.fhv.teamb.symphoniacus.persistence.model.DutyCategoryEntity;
import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyCategoryChangelogEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyEntity;
import java.util.List;
import java.util.Optional;
import javax.persistence.TypedQuery;

/**
 * DAO for DutyCategoryChangeLog.
 *
 * @author Nino Heinzle
 */
public class DutyCategoryChangeLogDao extends BaseDao<IDutyCategoryChangelogEntity> implements
    IDutyCategoryChangeLogDao {

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IDutyCategoryChangelogEntity> find(Integer key) {
        return this.find(IDutyCategoryChangelogEntity.class, key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IDutyCategoryChangelogEntity> persist(IDutyCategoryChangelogEntity elem) {
        return this.persist(IDutyCategoryChangelogEntity.class, elem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IDutyCategoryChangelogEntity> update(IDutyCategoryChangelogEntity elem) {
        return this.update(IDutyCategoryChangelogEntity.class, elem);
    }

    @Override
    public boolean remove(IDutyCategoryChangelogEntity elem) {
        return false;
    }

    /**
     * Finds all {@link DutyCategoryChangelogEntity} matching that belong to a given
     * {@link DutyCategoryEntity}.
     *
     * @param categoryEntity The duty category to use
     * @return A List of DutyCategoryChangelogEntity objects
     */
    public List<DutyCategoryChangelogEntity> getDutyCategoryChangelogs(
        DutyCategoryEntity categoryEntity
    ) {
        TypedQuery<DutyCategoryChangelogEntity> query = entityManager.createQuery(
            "SELECT changelog FROM DutyCategoryChangelogEntity changelog "
                + "WHERE changelog.dutyCategory = :givenCategory",
            DutyCategoryChangelogEntity.class
        );

        query.setParameter("givenCategory", categoryEntity);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    public boolean doesLogAlreadyExists(IDutyEntity duty) {
        TypedQuery<Long> query = entityManager.createQuery(
            "SELECT COUNT(changelog) FROM DutyCategoryChangelogEntity changelog "
                + "WHERE changelog.dutyCategory = :givenCategory "
                + "AND changelog.startDate = :givenStartDate",
            Long.class
        );

        query.setParameter("givenCategory", duty.getDutyCategory());
        query.setParameter("givenStartDate", duty.getStart().toLocalDate());

        return query.getSingleResult() >= 1;
    }

    /**
     * {@inheritDoc}
     */
    public Optional<IDutyCategoryChangelogEntity> getChangelogByDetails(IDutyEntity duty) {
        TypedQuery<IDutyCategoryChangelogEntity> query = entityManager.createQuery(
            "SELECT changelog FROM DutyCategoryChangelogEntity changelog "
                + "WHERE changelog.dutyCategory = :givenCategory "
                + "AND changelog.startDate = :givenStartDate ",
            IDutyCategoryChangelogEntity.class
        );

        query.setParameter("givenCategory", duty.getDutyCategory());
        query.setParameter("givenStartDate", duty.getStart().toLocalDate());

        return Optional.of(query.getSingleResult());
    }
}
