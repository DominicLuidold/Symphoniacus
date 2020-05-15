package at.fhv.teamb.symphoniacus.persistence.dao.interfaces;

import at.fhv.teamb.symphoniacus.persistence.Dao;
import at.fhv.teamb.symphoniacus.persistence.model.WeeklyScheduleEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IWeeklyScheduleEntity;
import java.time.LocalDate;
import java.util.Optional;

public interface IWeeklyScheduleDao extends Dao<IWeeklyScheduleEntity> {

    /**
     * Returns the {@link WeeklyScheduleEntity} for a given day and year.
     *
     * @param day  The day to use
     * @param year The year to use
     * @return A weekly schedule, if any
     */
    Optional<? extends IWeeklyScheduleEntity> findForDayAndYear(LocalDate day, int year);
}
