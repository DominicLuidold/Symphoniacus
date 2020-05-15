package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.dao.interfaces.IDutyCategoryChangeLogDao;
import at.fhv.teamb.symphoniacus.persistence.model.DutyCategoryChangelogEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyCategoryChangelogEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyCategoryEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyEntity;
import java.util.List;
import java.util.Optional;
import javax.persistence.TypedQuery;

/**
 * DAO for DutyCategoryChangeLog.
 *
 * @author Nino Heinzle
 */
public class DutyCategoryChangeLogDao extends BaseDao<IDutyCategoryChangelogEntity>
    implements IDutyCategoryChangeLogDao {

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IDutyCategoryChangelogEntity> find(Integer key) {
        return this.find(DutyCategoryChangelogEntity.class, key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IDutyCategoryChangelogEntity> persist(IDutyCategoryChangelogEntity elem) {
        return this.persist(DutyCategoryChangelogEntity.class, elem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IDutyCategoryChangelogEntity> update(IDutyCategoryChangelogEntity elem) {
        return this.update(DutyCategoryChangelogEntity.class, elem);
    }

    @Override
    public boolean remove(IDutyCategoryChangelogEntity elem) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DutyCategoryChangelogEntity> getDutyCategoryChangelogs(
        IDutyCategoryEntity categoryEntity
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
    @Override
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
    @Override
    public Optional<DutyCategoryChangelogEntity> getChangelogByDetails(IDutyEntity duty) {
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
