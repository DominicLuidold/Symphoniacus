package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.domain.SectionMonthlySchedule;
import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.SectionMonthlyScheduleEntity;
import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

/**
 * DAO for SectionMonthlyScheduleEntity.
 *
 * @author Dominic Luidold
 */
public class SectionMonthlyScheduleDao extends BaseDao<SectionMonthlyScheduleEntity> {

    @Override
    public Optional<SectionMonthlyScheduleEntity> find(Integer key) {
        return Optional.empty();
    }

    /**
     * Finds all {@link SectionMonthlySchedule} objects based on provided year.
     *
     * @param year The year to use
     * @return A List of section monthly schedules
     */
    public List<SectionMonthlyScheduleEntity> findAllInYear(Year year) {
        TypedQuery<SectionMonthlyScheduleEntity> query = entityManager.createQuery(
            "SELECT sms FROM SectionMonthlyScheduleEntity sms "
                + "JOIN FETCH sms.monthlySchedule ms "
                + "WHERE ms.year = :year",
            SectionMonthlyScheduleEntity.class
        );

        query.setParameter("year", year.getValue());

        return query.getResultList();
    }

    /**
     * Finds all {@link SectionMonthlySchedule} objects based on provided year and month.
     *
     * @param year  The year to use
     * @param month The month to use
     * @return A List of section monthly schedules
     */
    public SectionMonthlyScheduleEntity findAllInYearAndMonth(Year year, Month month) {
        TypedQuery<SectionMonthlyScheduleEntity> query = entityManager.createQuery(
            "SELECT sms FROM SectionMonthlyScheduleEntity sms "
                + "JOIN FETCH sms.monthlySchedule ms "
                + "WHERE ms.year = :year AND ms.month = :month",
            SectionMonthlyScheduleEntity.class
        );

        query.setParameter("year", year.getValue());
        query.setParameter("month", month.getValue());

        return query.getSingleResult();
    }

    @Override
    public Optional<SectionMonthlyScheduleEntity> persist(SectionMonthlyScheduleEntity elem) {
        return Optional.empty();
    }

    @Override
    public Optional<SectionMonthlyScheduleEntity> update(SectionMonthlyScheduleEntity elem) {
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.merge(elem);
            transaction.commit();
            return Optional.of(elem);
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }
        return Optional.empty();
    }

    @Override
    public Boolean remove(SectionMonthlyScheduleEntity elem) {
        return false;
    }
}
