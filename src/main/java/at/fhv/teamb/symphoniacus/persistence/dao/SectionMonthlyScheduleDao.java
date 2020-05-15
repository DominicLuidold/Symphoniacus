package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.dao.interfaces.ISectionMonthlyScheduleDao;
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
public class SectionMonthlyScheduleDao extends BaseDao<ISectionMonthlyScheduleEntity>
    implements ISectionMonthlyScheduleDao {

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ISectionMonthlyScheduleEntity> find(Integer key) {
        return this.find(SectionMonthlyScheduleEntity.class, key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SectionMonthlyScheduleEntity> findAllInYear(ISectionEntity section, Year year) {
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
     * {@inheritDoc}
     */
    @Override
    public List<SectionMonthlyScheduleEntity> findAllInYearAndMonth(Year year, Month month) {
        TypedQuery<SectionMonthlyScheduleEntity> query = entityManager.createQuery(
            "SELECT sms FROM SectionMonthlyScheduleEntity sms "
                + "JOIN FETCH sms.monthlySchedule ms "
                + "WHERE ms.year = :year AND ms.month = :month",
            SectionMonthlyScheduleEntity.class
        );

        query.setParameter("year", year.getValue());
        query.setParameter("month", month.getValue());

        return query.getResultList();
    }

    @Override
    public ISectionMonthlyScheduleEntity findAllInYearAndMonth(
        ISectionEntity section,
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
    public Optional<ISectionMonthlyScheduleEntity> persist(ISectionMonthlyScheduleEntity elem) {
        return this.persist(ISectionMonthlyScheduleEntity.class, elem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ISectionMonthlyScheduleEntity> update(ISectionMonthlyScheduleEntity elem) {
        return this.update(ISectionMonthlyScheduleEntity.class, elem);
    }

    @Override
    public boolean remove(ISectionMonthlyScheduleEntity elem) {
        return false;
    }
}
