package at.fhv.teamb.symphoniacus.persistence.dao.interfaces;

import at.fhv.teamb.symphoniacus.persistence.Dao;
import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.SectionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.SeriesOfPerformancesEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyCategoryEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IInstrumentationEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISectionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISeriesOfPerformancesEntity;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Interface for DutyDao class.
 *
 * @author Theresa Gierer
 */
public interface IDutyDao extends Dao<IDutyEntity> {

    /**
     * Checks whether a duty with the given parameters exists or not.
     *
     * @param series           given Series of Performances from searched Duty.
     * @param instrumentations given instrumentation from searched Duty.
     * @param startingDate     given starting Date from the searched Duty.
     * @param endingDate       given ending Date from searched Duty.
     * @param category         given dutyCategory from searched Duty.
     * @return True if duty exists, false otherwise
     */
    boolean doesDutyAlreadyExists(
        ISeriesOfPerformancesEntity series,
        List<IInstrumentationEntity> instrumentations,
        LocalDateTime startingDate,
        LocalDateTime endingDate,
        IDutyCategoryEntity category);

    /**
     * Finds all duties in a week.
     *
     * @param section The section of the current user
     * @param start   A LocalDateTime that represents the start
     * @return A List of the corresponding duties that were found
     * @see #findAllInRangeWithSection
     * (Section, LocalDateTime, LocalDateTime, boolean, boolean, boolean)
     */
    List<IDutyEntity> findAllInWeekWithSection(
        ISectionEntity section,
        LocalDateTime start,
        boolean isReadyForDutyScheduler,
        boolean isReadyForOrganisationManager,
        boolean isPublished
    );

    /**
     * Returns all duties in a specific time range that have a sectionMonthlySchedule
     * with this section's sectionId where the flag isReadyForDutyScheduler
     * is true.
     *
     * @param section The section of the current user
     * @param start   A LocalDateTime that represents the start
     * @param end     A LocalDateTime that represents the end
     * @return A List of the corresponding duties that were found
     */
    List<IDutyEntity> findAllInRangeWithSection(
        ISectionEntity section,
        LocalDateTime start,
        LocalDateTime end,
        boolean isReadyForDutyScheduler,
        boolean isReadyForOrganisationManager,
        boolean isPublished
    );

    /**
     * Returns all duties that are in the week of the given start and end date.
     *
     * @param start A LocalDateTime that represents the start
     * @param end   A LocalDateTime that represents the end
     * @return A List of all Duties that have the date between the given start and end dates
     */
    List<IDutyEntity> findAllInRange(LocalDateTime start, LocalDateTime end);

    /**
     * Returns {@link DutyEntity} objects belonging to a {@link SeriesOfPerformancesEntity}
     * based on provided data.
     *
     * @param sop               The series of performance to use
     * @param dutyStart         The start date of the duty
     * @param maxNumberOfDuties The amount of duties to return
     * @return A List of duties
     */
    List<IDutyEntity> getOtherDutiesForSeriesOfPerformances(
        ISeriesOfPerformancesEntity sop,
        LocalDateTime dutyStart,
        Integer maxNumberOfDuties
    );

    /**
     * Returns {@link DutyEntity} objects belonging to a {@link SectionEntity} based on
     * provided data.
     *
     * @param duty              The duty to use
     * @param section           The section to use
     * @param maxNumberOfDuties The amount of duties to return
     * @return A List of duties
     */
    List<IDutyEntity> getOtherDutiesForSection(
        IDutyEntity duty,
        ISectionEntity section,
        Integer maxNumberOfDuties
    );

    /**
     * Finds all duties in a week. The start date must be a Monday.
     *
     * @param start A monday that represents the start of the
     * @return A List of the corresponding duties that were found
     * @see #findAllInRange(LocalDateTime, LocalDateTime)
     */
    List<IDutyEntity> findAllInWeek(LocalDateTime start);
}
