package at.fhv.teamb.symphoniacus.persistence.dao.interfaces;

import at.fhv.teamb.symphoniacus.domain.Section;
import at.fhv.teamb.symphoniacus.persistence.Dao;
import at.fhv.teamb.symphoniacus.persistence.model.SectionMonthlyScheduleEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISectionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISectionMonthlyScheduleEntity;
import java.time.Month;
import java.time.Year;
import java.util.List;

public interface ISectionMonthlyScheduleDao extends Dao<ISectionMonthlyScheduleEntity> {

    /**
     * Finds all {@link SectionMonthlyScheduleEntity} objects based on provided {@link Section}
     * object and year.
     *
     * @param section The section to use
     * @param year    The year to use
     * @return A List of section monthly schedules
     */
    List<ISectionMonthlyScheduleEntity> findAllInYear(ISectionEntity section, Year year);

    /**
     * Finds a List of {@link SectionMonthlyScheduleEntity} objects based on
     * provided year and month.
     *
     * @param year  The year to use
     * @param month The month to use
     * @return A List of section monthly schedules
     */
    List<ISectionMonthlyScheduleEntity> findAllInYearAndMonth(Year year, Month month);

    /**
     * Finds a {@link SectionMonthlyScheduleEntity} object based on provided {@link Section}
     * object, year and month.
     *
     * @param year  The year to use
     * @param month The month to use
     * @return A section monthly schedule
     */
    ISectionMonthlyScheduleEntity findAllInYearAndMonth(
        ISectionEntity section,
        Year year,
        Month month
    );
}
