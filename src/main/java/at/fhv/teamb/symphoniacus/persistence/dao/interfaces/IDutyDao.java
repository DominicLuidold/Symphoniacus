package at.fhv.teamb.symphoniacus.persistence.dao.interfaces;

import at.fhv.teamb.symphoniacus.persistence.Dao;
import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyCategoryEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IInstrumentationEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISectionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISeriesOfPerformancesEntity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface IDutyDao extends Dao<IDutyEntity> {

    /**
     * Finds all duties in a week. The start date must be a Monday.
     *
     * @param start A monday that represents the start of the
     * @return A List of the corresponding duties that were found
     * @see #findAllInRange(LocalDateTime, LocalDateTime)
     */
    List<IDutyEntity> findAllInWeek(LocalDateTime start);

    /**
     * Returns all duties that are in the week of the given start and end date.
     *
     * @param start A LocalDateTime that represents the start
     * @param end   A LocalDateTime that represents the end
     * @return A List of all Duties that have the date between the given start and end dates
     */
    List<IDutyEntity> findAllInRange(LocalDateTime start, LocalDateTime end);

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
     * returns all duties of a musician in the period of the entered month.
     *
     * @param musician The requested Musician
     * @param month    A LocalDateTime that represents the month
     * @return A List of the corresponding duties that were found
     */
    List<IDutyEntity> getAllDutiesInRangeFromMusician(
        IMusicianEntity musician,
        LocalDate month
    );

    /**
     * Get all Duties for all given musicians within a given month.
     *
     * @param musicians List of musicians
     * @param month     LocalDate any day of a month represents the whole month
     * @return A set uf DutyEntities, because duplicates of duties are unnecessary
     */
    Set<IDutyEntity> getAllDutiesOfMusicians(
        List<IMusicianEntity> musicians,
        LocalDate month
    );

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
        IDutyCategoryEntity category
    );
}
