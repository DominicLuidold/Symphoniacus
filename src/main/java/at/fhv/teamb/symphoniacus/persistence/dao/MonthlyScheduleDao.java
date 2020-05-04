package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.MonthlyScheduleEntity;
import java.util.Optional;
import javax.persistence.TypedQuery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MonthlyScheduleDao extends BaseDao<MonthlyScheduleEntity> {
    private static final Logger LOG = LogManager.getLogger(MonthlyScheduleDao.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<MonthlyScheduleEntity> find(Integer key) {
        return this.find(MonthlyScheduleEntity.class, key);
    }

    /**
     * Returns the {@link MonthlyScheduleEntity} for a given month and year.
     *
     * @param month The month to use
     * @param year  The year to use
     * @return A monthly schedule, if any
     */
    public Optional<MonthlyScheduleEntity> findForMonthAndYear(int month, int year) {
        TypedQuery<MonthlyScheduleEntity> query = entityManager.createQuery(
            "SELECT m FROM MonthlyScheduleEntity m "
                + "WHERE m.month = :month AND m.year = :year",
            MonthlyScheduleEntity.class
        );

        query.setParameter("month", month);
        query.setParameter("year", year);

        try {
            return Optional.of(query.getSingleResult());
        } catch (Exception e) {
            LOG.error(e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<MonthlyScheduleEntity> persist(MonthlyScheduleEntity elem) {
        return Optional.empty();
    }

    @Override
    public Optional<MonthlyScheduleEntity> update(MonthlyScheduleEntity elem) {
        return Optional.empty();
    }

    @Override
    public Boolean remove(MonthlyScheduleEntity elem) {
        return false;
    }
}
