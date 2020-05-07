package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.WeeklyScheduleEntity;
import java.time.LocalDate;
import java.util.Optional;
import javax.persistence.TypedQuery;

public class WeeklyScheduleDao extends BaseDao<WeeklyScheduleEntity> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<WeeklyScheduleEntity> find(Integer key) {
        return this.find(WeeklyScheduleEntity.class, key);
    }

    /**
     * Returns the {@link WeeklyScheduleEntity} for a given day and year.
     *
     * @param day  The day to use
     * @param year The year to use
     * @return A weekly schedule, if any
     */
    public Optional<WeeklyScheduleEntity> findForDayAndYear(LocalDate day, int year) {
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
    public Optional<WeeklyScheduleEntity> persist(WeeklyScheduleEntity elem) {
        return this.persist(WeeklyScheduleEntity.class, elem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<WeeklyScheduleEntity> update(WeeklyScheduleEntity elem) {
        return this.update(WeeklyScheduleEntity.class, elem);
    }

    @Override
    public boolean remove(WeeklyScheduleEntity elem) {
        return false;
    }
}
