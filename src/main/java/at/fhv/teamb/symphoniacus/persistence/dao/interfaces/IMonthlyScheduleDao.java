package at.fhv.teamb.symphoniacus.persistence.dao.interfaces;

import at.fhv.teamb.symphoniacus.persistence.Dao;
import at.fhv.teamb.symphoniacus.persistence.model.MonthlyScheduleEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMonthlyScheduleEntity;
import java.util.Optional;

public interface IMonthlyScheduleDao extends Dao<IMonthlyScheduleEntity> {

    /**
     * Returns the {@link MonthlyScheduleEntity} for a given month and year.
     *
     * @param month The month to use
     * @param year  The year to use
     * @return A monthly schedule, if any
     */
    Optional<IMonthlyScheduleEntity> findForMonthAndYear(int month, int year);
}
