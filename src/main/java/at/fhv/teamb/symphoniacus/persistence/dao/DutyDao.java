package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.MusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.SectionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.SeriesOfPerformancesEntity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

/**
 * DAO for Duty class.
 *
 * @author Valentin Goronjic
 * @author Dominic Luidold
 */
public class DutyDao extends BaseDao<DutyEntity> {

    /**
     * Finds a {@link DutyEntity} by its key.
     *
     * @param key The key of the duty
     * @return The duty that is looked for
     */
    @Override
    public Optional<DutyEntity> find(Integer key) {
        return this.find(DutyEntity.class, key);
    }

    /**
     * Finds all duties in a week. The start date must be a Monday.
     *
     * @param start A monday that represents the start of the
     * @return A List of the corresponding duties that were found
     * @see #findAllInRange(LocalDateTime, LocalDateTime)
     */
    public List<DutyEntity> findAllInWeek(LocalDateTime start) {
        return findAllInRange(start, start.plusDays(6));
    }

    /**
     * Returns all duties that are in the week of the given start and end date.
     *
     * @param start A LocalDateTime that represents the start
     * @param end   A LocalDateTime that represents the end
     * @return A List of all Duties that have the date between the given start and end dates
     */
    public List<DutyEntity> findAllInRange(LocalDateTime start, LocalDateTime end) {
        TypedQuery<DutyEntity> query = entityManager.createQuery(
            "SELECT d FROM DutyEntity d "
                + "JOIN FETCH d.dutyCategory dc "
                + "LEFT JOIN FETCH d.seriesOfPerformances sop "
                + "WHERE d.start >= :start "
                + "AND d.end <= :end",
            DutyEntity.class
        );

        query.setMaxResults(300);
        query.setParameter("start", start);
        query.setParameter("end", end);

        return query.getResultList();
    }

    /**
     * Finds all duties in a week.
     *
     * @param section The section of the current user
     * @param start   A LocalDateTime that represents the start
     * @return A List of the corresponding duties that were found
     * @see #findAllInRangeWithSection
     * (Section, LocalDateTime, LocalDateTime, boolean, boolean, boolean)
     */
    public List<DutyEntity> findAllInWeekWithSection(
        SectionEntity section,
        LocalDateTime start,
        boolean isReadyForDutyScheduler,
        boolean isReadyForOrganisationManager,
        boolean isPublished
    ) {
        return findAllInRangeWithSection(
            section,
            start,
            start.plusDays(6),
            isReadyForDutyScheduler,
            isReadyForOrganisationManager,
            isPublished
        );
    }

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
    public List<DutyEntity> findAllInRangeWithSection(
        SectionEntity section,
        LocalDateTime start,
        LocalDateTime end,
        boolean isReadyForDutyScheduler,
        boolean isReadyForOrganisationManager,
        boolean isPublished
    ) {
        TypedQuery<DutyEntity> query = entityManager.createQuery(
            "SELECT d FROM DutyEntity d "
                + "INNER JOIN d.sectionMonthlySchedules sms "
                + "INNER JOIN sms.section s "
                + "JOIN FETCH d.dutyCategory dc "
                + "LEFT JOIN FETCH d.seriesOfPerformances sop "
                + "WHERE d.start >= :start AND d.end <= :end "
                + "AND s.sectionId = :sectionId "
                + "AND sms.isReadyForDutyScheduler = :isReadyForDutyScheduler "
                + "AND sms.isReadyForOrganisationManager = :isReadyForOrganisationManager "
                + "AND sms.isPublished = :isPublished",
            DutyEntity.class
        );

        query.setMaxResults(300);
        query.setParameter("start", start);
        query.setParameter("end", end);
        query.setParameter("sectionId", section.getSectionId());
        query.setParameter("isReadyForDutyScheduler", isReadyForDutyScheduler);
        query.setParameter("isReadyForOrganisationManager", isReadyForOrganisationManager);
        query.setParameter("isPublished", isPublished);

        return query.getResultList();
    }

    /**
     * returns all duties of a musician in the period of the entered month.
     *
     * @param musician The requested Musician
     * @param month    A LocalDateTime that represents the month
     * @return A List of the corresponding duties that were found
     */
    public List<DutyEntity> getAllDutiesInRangeFromMusician(
        MusicianEntity musician,
        LocalDate month
    ) {
        YearMonth yearMonth = YearMonth.from(month);
        LocalDate start = yearMonth.atDay(1); // Find first day of month
        LocalDate end = yearMonth.atEndOfMonth(); // Find last day of month
        LocalDateTime startWithTime = start.atStartOfDay();
        LocalDateTime endWithTime = end.atStartOfDay();

        TypedQuery<DutyEntity> query = entityManager.createQuery(
            "SELECT d FROM DutyEntity d "
                + "INNER JOIN d.dutyPositions dp "
                + "INNER JOIN dp.musician m "
                + "JOIN FETCH d.dutyCategory "
                + "WHERE d.end <= :end AND d.start >= :start AND m = :musician",
            DutyEntity.class
        );

        query.setParameter("musician", musician);
        query.setParameter("start", startWithTime);
        query.setParameter("end", endWithTime);

        return query.getResultList();
    }

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
    ) {
        YearMonth yearMonth = YearMonth.from(month);
        LocalDate start = yearMonth.atDay(1); // Find first day of month
        LocalDate end = yearMonth.atEndOfMonth(); // Find last day of month
        LocalDateTime startWithTime = start.atStartOfDay();
        LocalDateTime endWithTime = end.atStartOfDay();

        TypedQuery<DutyEntity> query = entityManager.createQuery(
            "SELECT d FROM DutyEntity d "
                + "INNER JOIN d.dutyPositions dp "
                + "INNER JOIN dp.musician m "
                + "WHERE d.end <= :end "
                + "AND d.start >= :start AND m IN :musicians",
            DutyEntity.class
        );

        query.setParameter("start", startWithTime);
        query.setParameter("end", endWithTime);
        query.setParameter("musicians", musicians);

        return new LinkedHashSet<>(query.getResultList());
    }

    /**
     * Persists a new duty.
     *
     * @param elem The duty to persist
     * @return The persisted duty filled with its Identifier
     */
    @Override
    public Optional<DutyEntity> persist(DutyEntity elem) {
        return Optional.empty();
    }

    /**
     * Updates an existing duty.
     *
     * @param elem The duty to be updated
     * @return The updated duty
     */
    @Override
    public Optional<DutyEntity> update(DutyEntity elem) {
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.merge(elem);
            transaction.commit();
            return Optional.of(elem);
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }
        return Optional.empty();
    }

    /**
     * Removes a duty.
     *
     * @param elem The duty to be removed.
     * @return True if the duty was removed
     */
    @Override
    public Boolean remove(DutyEntity elem) {
        return false;
    }

    /**
     * Returns {@link DutyEntity} objects belonging to a {@link SeriesOfPerformancesEntity}
     * based on provided data.
     *
     * @param sop               The series of performance to use
     * @param dutyStart         The start date of the duty
     * @param maxNumberOfDuties The amount of duties to return
     * @return A List of duties
     */
    public List<DutyEntity> getOtherDutiesForSeriesOfPerformances(
        SeriesOfPerformancesEntity sop,
        LocalDateTime dutyStart,
        Integer maxNumberOfDuties
    ) {
        TypedQuery<DutyEntity> query = entityManager.createQuery(
            "SELECT d FROM DutyEntity d "
                + "INNER JOIN d.seriesOfPerformances sop "
                + "WHERE sop.seriesOfPerformancesId = :sopId "
                + "AND d.start < :dutyStart ",
            DutyEntity.class
        );

        query.setParameter("dutyStart", dutyStart);
        query.setParameter("sopId", sop.getSeriesOfPerformancesId());
        query.setMaxResults(maxNumberOfDuties);

        return query.getResultList();
    }

    /**
     * Returns {@link DutyEntity} objects belonging to a {@link SectionEntity} based on
     * provided data.
     *
     * @param duty              The duty to use
     * @param section           The section to use
     * @param maxNumberOfDuties The amount of duties to return
     * @return A List of duties
     */
    public List<DutyEntity> getOtherDutiesForSection(
        DutyEntity duty,
        SectionEntity section,
        Integer maxNumberOfDuties
    ) {
        TypedQuery<DutyEntity> query = entityManager.createQuery(
            "SELECT d FROM DutyEntity d "
                + "INNER JOIN d.sectionMonthlySchedules sms "
                + "WHERE sms.section.sectionId = :sectionId "
                + "AND d.seriesOfPerformances IS NULL "
                + "AND d.dutyCategory.dutyCategoryId = :dutyCategoryId ",
            DutyEntity.class
        );
        query.setParameter("sectionId", section.getSectionId());
        query.setParameter("dutyCategoryId", duty.getDutyId());
        query.setMaxResults(maxNumberOfDuties);

        return query.getResultList();
    }
}
