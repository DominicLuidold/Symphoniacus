package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.DutyCategoryEntity;
import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.InstrumentationEntity;
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
import javax.persistence.TypedQuery;

/**
 * DAO for Duty class.
 *
 * @author Valentin Goronjic
 * @author Dominic Luidold
 */
public class DutyDao extends BaseDao<DutyEntity> {

    /**
     * {@inheritDoc}
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
                + "JOIN FETCH d.dutyPositions dp "
                + "JOIN FETCH dp.musician m "
                + "JOIN FETCH m.contractualObligations co "
                + "JOIN FETCH d.dutyCategory "
                + "WHERE d.end <= :end "
                + "AND d.start >= :start "
                + "AND m = :musician "
                + "AND co.startDate <= :currentDate "
                + "AND co.endDate >= :currentDate",
            DutyEntity.class
        );

        query.setParameter("musician", musician);
        query.setParameter("start", startWithTime);
        query.setParameter("end", endWithTime);
        query.setParameter("currentDate", LocalDate.now());

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
     * {@inheritDoc}
     */
    @Override
    public Optional<DutyEntity> persist(DutyEntity elem) {
        return this.persist(DutyEntity.class, elem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<DutyEntity> update(DutyEntity elem) {
        return this.update(DutyEntity.class, elem);
    }

    /**
     * Removes a duty.
     *
     * @param elem The duty to be removed.
     * @return True if the duty was removed
     */
    @Override
    public boolean remove(DutyEntity elem) {
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
    public boolean doesDutyAlreadyExists(
        SeriesOfPerformancesEntity series,
        List<InstrumentationEntity> instrumentations,
        LocalDateTime startingDate,
        LocalDateTime endingDate,
        DutyCategoryEntity category) {

        TypedQuery<Long> query = entityManager.createQuery(
            "SELECT COUNT(d) FROM DutyEntity d "
                + "LEFT JOIN d.dutyPositions dp "
                + "LEFT JOIN dp.instrumentationPosition ip "
                + "WHERE ip.instrumentation IN :inst "
                + "AND d.start = :sDate "
                + "AND d.end = :eDate "
                + "AND d.seriesOfPerformances = :series "
                + "AND d.dutyCategory = :category ",
            Long.class
        );

        query.setParameter("series", series);
        query.setParameter("sDate", startingDate);
        query.setParameter("eDate", endingDate);
        query.setParameter("inst", instrumentations);
        query.setParameter("category", category);

        return (query.getSingleResult() >= 1);
    }
}
