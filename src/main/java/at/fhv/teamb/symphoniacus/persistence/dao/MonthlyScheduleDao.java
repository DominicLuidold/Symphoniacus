package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.MonthlyScheduleEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMonthlyScheduleEntity;
import java.util.Optional;
import javax.persistence.TypedQuery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MonthlyScheduleDao extends BaseDao<IMonthlyScheduleEntity> {
    private static final Logger LOG = LogManager.getLogger(MonthlyScheduleDao.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IMonthlyScheduleEntity> find(Integer key) {
        return this.find(IMonthlyScheduleEntity.class, key);
    }

    /**
     * Returns the {@link MonthlyScheduleEntity} for a given month and year.
     *
     * @param month The month to use
     * @param year  The year to use
     * @return A monthly schedule, if any
     */
    public Optional<IMonthlyScheduleEntity> findForMonthAndYear(int month, int year) {
        TypedQuery<IMonthlyScheduleEntity> query = entityManager.createQuery(
            "SELECT m FROM MonthlyScheduleEntity m "
                + "WHERE m.month = :month AND m.year = :year",
            IMonthlyScheduleEntity.class
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

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IMonthlyScheduleEntity> persist(IMonthlyScheduleEntity elem) {
        return this.persist(IMonthlyScheduleEntity.class, elem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IMonthlyScheduleEntity> update(IMonthlyScheduleEntity elem) {
        return this.update(IMonthlyScheduleEntity.class, elem);
    }

    @Override
    public boolean remove(IMonthlyScheduleEntity elem) {
        return false;
    }
}
