package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.dao.interfaces.IWeeklyScheduleDao;
import at.fhv.teamb.symphoniacus.persistence.model.WeeklyScheduleEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IWeeklyScheduleEntity;
import java.time.LocalDate;
import java.util.Optional;
import javax.persistence.TypedQuery;

public class WeeklyScheduleDao extends BaseDao<IWeeklyScheduleEntity>
    implements IWeeklyScheduleDao {

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IWeeklyScheduleEntity> find(Integer key) {
        return this.find(WeeklyScheduleEntity.class, key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IWeeklyScheduleEntity> findForDayAndYear(LocalDate day, int year) {
        TypedQuery<WeeklyScheduleEntity> query = entityManager.createQuery(
            "SELECT w FROM WeeklyScheduleEntity w "
                + "WHERE :day BETWEEN w.startDate AND w.endDate "
                + "AND w.year = :year",
            WeeklyScheduleEntity.class
        );

        query.setParameter("day", day);
        query.setParameter("year", year);

        try {
            return Optional.of(query.getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IWeeklyScheduleEntity> persist(IWeeklyScheduleEntity elem) {
        return this.persist(WeeklyScheduleEntity.class, elem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IWeeklyScheduleEntity> update(IWeeklyScheduleEntity elem) {
        return this.update(WeeklyScheduleEntity.class, elem);
    }

    @Override
    public boolean remove(IWeeklyScheduleEntity elem) {
        return false;
    }
}
