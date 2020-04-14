package at.fhv.teamb.symphoniacus.application;

import at.fhv.teamb.symphoniacus.persistence.dao.DutyDao;
import at.fhv.teamb.symphoniacus.persistence.model.Duty;
import at.fhv.teamb.symphoniacus.persistence.model.Section;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

/**
 * This class is responsible for finding {@link Duty} objects based on a range of time and
 * {@link Section}.
 *
 * @author Nino Heinzle
 */
public class DutyManager {
    protected DutyDao dutyDao;

    public DutyManager() {
        this.dutyDao = new DutyDao();
    }

    /**
     * Finds all duties within a full week (any Date can be given).
     * converts the given Date the last Monday
     *
     * @param start is any given Date
     * @return a List of all matching duties
     */
    public List<Duty> findAllInWeek(LocalDate start) {
        return this.dutyDao.findAllInWeek(getLastMondayDate(start).atStartOfDay());
    }

    /**
     * Finds all duties in a within the week of a given Date for a section.
     *
     * @param sectionOfUser The section of the current user
     * @param start         A LocalDate that represents the start
     * @return A List of the matching duties
     */
    public List<Duty> findAllInWeekWithSection(
        Section sectionOfUser,
        LocalDate start
    ) {
        return this.dutyDao.findAllInWeekWithSection(
            sectionOfUser,
            getLastMondayDate(start).atStartOfDay(),
            false,
            false,
            false
        );
    }

    /**
     * Finds all duties in a specific range of time for a section.
     *
     * @param sectionOfUser The section of the current user
     * @param start         A LocalDate that represents the start
     * @param end           A LocalDate that represents the end
     * @return A List of the matching duties
     */
    public List<Duty> findAllInRangeWithSection(
        Section sectionOfUser,
        LocalDate start,
        LocalDate end
    ) {
        return this.dutyDao.findAllInRangeWithSection(
            sectionOfUser,
            start.atStartOfDay(),
            end.atStartOfDay(),
            true, // TODO - Add logic to determine which parameters are true
            false,
            false
        );
    }

    /**
     * Finds all duties in a specific range of time.
     *
     * @param start A LocalDate that represents the start
     * @param end   A LocalDate that represents the end
     * @return A List of the matching duties
     */
    public List<Duty> findAllInRange(LocalDate start, LocalDate end) {
        return this.dutyDao.findAllInRange(start.atStartOfDay(), end.atStartOfDay());
    }

    /**
     * Returns the Monday of the week based on the {@link LocalDate}.
     *
     * @param givenDate The date to determine the monday of the week
     * @return A LocalDate representing the monday of the week
     */
    public static LocalDate getLastMondayDate(LocalDate givenDate) {
        // Will always jump back to last monday
        return givenDate.with(DayOfWeek.MONDAY);
    }
}
