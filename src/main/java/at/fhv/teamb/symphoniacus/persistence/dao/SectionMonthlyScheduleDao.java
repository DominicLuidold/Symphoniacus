package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.domain.Section;
import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.SectionMonthlyScheduleEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISectionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISectionMonthlyScheduleEntity;
import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.Optional;
import javax.persistence.TypedQuery;

/**
 * DAO for SectionMonthlyScheduleEntity.
 *
 * @author Dominic Luidold
 */
public class SectionMonthlyScheduleDao extends BaseDao<ISectionMonthlyScheduleEntity> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ISectionMonthlyScheduleEntity> find(Integer key) {
        return this.find(SectionMonthlyScheduleEntity.class, key);
    }

    /**
     * Finds all {@link SectionMonthlyScheduleEntity} objects based on provided {@link Section}
     * object and year.
     *
     * @param section The section to use
     * @param year    The year to use
     * @return A List of section monthly schedules
     */
    public List<ISectionMonthlyScheduleEntity> findAllInYear(ISectionEntity section, Year year) {
        TypedQuery<ISectionMonthlyScheduleEntity> query = entityManager.createQuery(
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
     * Finds a List of {@link SectionMonthlyScheduleEntity} objects based on
     * provided year and month.
     *
     * @param year  The year to use
     * @param month The month to use
     * @return A List of section monthly schedules
     */
    public List<ISectionMonthlyScheduleEntity> findAllInYearAndMonth(Year year, Month month) {
        TypedQuery<ISectionMonthlyScheduleEntity> query = entityManager.createQuery(
            "SELECT sms FROM SectionMonthlyScheduleEntity sms "
                + "JOIN FETCH sms.monthlySchedule ms "
                + "WHERE ms.year = :year AND ms.month = :month",
            ISectionMonthlyScheduleEntity.class
        );

        query.setParameter("year", year.getValue());
        query.setParameter("month", month.getValue());

        return query.getResultList();
    }

    /**
     * Finds a {@link SectionMonthlyScheduleEntity} object based on provided {@link Section}
     * object, year and month.
     *
     * @param year  The year to use
     * @param month The month to use
     * @return A section monthly schedule
     */
    public ISectionMonthlyScheduleEntity findAllInYearAndMonth(
        ISectionEntity section,
        Year year,
        Month month
    ) {
        TypedQuery<ISectionMonthlyScheduleEntity> query = entityManager.createQuery(
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
    public Optional<ISectionMonthlyScheduleEntity> persist(ISectionMonthlyScheduleEntity elem) {
        return this.persist(SectionMonthlyScheduleEntity.class, elem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ISectionMonthlyScheduleEntity> update(ISectionMonthlyScheduleEntity elem) {
        return this.update(SectionMonthlyScheduleEntity.class, elem);
    }

    @Override
    public boolean remove(ISectionMonthlyScheduleEntity elem) {
        return false;
    }
}
