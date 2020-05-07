package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.domain.Section;
import at.fhv.teamb.symphoniacus.domain.SectionMonthlySchedule;
import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.SectionEntity;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<SectionMonthlyScheduleEntity> find(Integer key) {
        return this.find(SectionMonthlyScheduleEntity.class, key);
    }

    /**
     * Finds all {@link SectionMonthlySchedule} objects based on provided {@link Section}
     * object and year.
     *
     * @param section The section to use
     * @param year    The year to use
     * @return A List of section monthly schedules
     */
    public List<SectionMonthlyScheduleEntity> findAllInYear(SectionEntity section, Year year) {
        TypedQuery<SectionMonthlyScheduleEntity> query = entityManager.createQuery(
            "SELECT sms FROM SectionMonthlyScheduleEntity sms "
                + "JOIN FETCH sms.monthlySchedule ms "
                + "WHERE sms.section = :section "
                + "AND ms.year = :year",
            SectionMonthlyScheduleEntity.class
        );

        query.setParameter("section", section);
        query.setParameter("year", year.getValue());

        return query.getResultList();
    }

    /**
     * Finds all {@link SectionMonthlySchedule} objects based on provided {@link Section}
     * object, year and month.
     *
     * @param year  The year to use
     * @param month The month to use
     * @return A List of section monthly schedules
     */
    public SectionMonthlyScheduleEntity findAllInYearAndMonth(
        SectionEntity section,
        Year year,
        Month month
    ) {
        TypedQuery<SectionMonthlyScheduleEntity> query = entityManager.createQuery(
            "SELECT sms FROM SectionMonthlyScheduleEntity sms "
                + "JOIN FETCH sms.monthlySchedule ms "
                + "WHERE sms.section = :section "
                + "AND ms.year = :year AND ms.month = :month",
            SectionMonthlyScheduleEntity.class
        );

        query.setParameter("section", section);
        query.setParameter("year", year.getValue());
        query.setParameter("month", month.getValue());

        return query.getSingleResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<SectionMonthlyScheduleEntity> persist(SectionMonthlyScheduleEntity elem) {
        return this.persist(SectionMonthlyScheduleEntity.class, elem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<SectionMonthlyScheduleEntity> update(SectionMonthlyScheduleEntity elem) {
        return this.update(SectionMonthlyScheduleEntity.class, elem);
    }

    @Override
    public boolean remove(SectionMonthlyScheduleEntity elem) {
        return false;
    }
}
