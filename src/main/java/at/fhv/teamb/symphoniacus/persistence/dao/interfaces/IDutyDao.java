package at.fhv.teamb.symphoniacus.persistence.interfacedao;

import at.fhv.teamb.symphoniacus.persistence.Dao;
import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.MusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.SectionEntity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * Interface for DutyDao class.
 *
 * @author Theresa Gierer
 */
public interface IDutyDao extends Dao<DutyEntity> {

    /**
     * Finds all duties in a week. The start date must be a Monday.
     *
     * @param start A monday that represents the start of the
     * @return A List of the corresponding duties that were found
     * @see #findAllInRange(LocalDateTime, LocalDateTime)
     */
    List<DutyEntity> findAllInWeek(LocalDateTime start);

    /**
     * Returns all duties that are in the week of the given start and end date.
     *
     * @param start A LocalDateTime that represents the start
     * @param end   A LocalDateTime that represents the end
     * @return A List of all Duties that have the date between the given start and end dates
     */
    List<DutyEntity> findAllInRange(LocalDateTime start, LocalDateTime end);

    /**
     * Finds all duties in a week.
     *
     * @param section The section of the current user
     * @param start   A LocalDateTime that represents the start
     * @return A List of the corresponding duties that were found
     * @see #findAllInRangeWithSection
     * (Section, LocalDateTime, LocalDateTime, boolean, boolean, boolean)
     */
    List<DutyEntity> findAllInWeekWithSection(
        SectionEntity section,
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
    List<DutyEntity> findAllInRangeWithSection(
        SectionEntity section,
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
    List<DutyEntity> getAllDutiesInRangeFromMusician(
        MusicianEntity musician,
        LocalDate month
    );

    /**
     * Get all Duties for all given musicians within a given month.
     *
     * @param musicians List of musicians
     * @param month     LocalDate any day of a month represents the whole month
     * @return A set uf DutyEntities, because duplicates of duties are unnecessary
     */
    public Set<DutyEntity> getAllDutiesOfMusicians(
        List<MusicianEntity> musicians,
        LocalDate month
    );
}
