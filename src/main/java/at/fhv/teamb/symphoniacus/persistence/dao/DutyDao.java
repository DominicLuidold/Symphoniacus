package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.SectionEntity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import javax.persistence.TypedQuery;

/**
 * DAO for Duty class.
 *
 * @author Valentin Goronjic
 */
public class DutyDao extends BaseDao<DutyEntity> {

    /**
     * Finds a duty by its key.
     *
     * @param key The key of the duty
     * @return The duty that is looked for
     */
    @Override
    public Optional<DutyEntity> find(Object key) {
        this.createEntityManager();
        DutyEntity d = this.entityManager.find(DutyEntity.class, key);
        this.tearDown();
        return Optional.of(d);
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
        this.createEntityManager();
        TypedQuery<DutyEntity> query = this.entityManager.createQuery(
            "SELECT d FROM DutyEntity d WHERE d.start >= :start AND d.end <= :end",
            DutyEntity.class
        );
        query.setMaxResults(300);
        query.setParameter("start", start);
        query.setParameter("end", end);
        List<DutyEntity> resultList = query.getResultList();
        this.tearDown();

        return resultList;
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
        this.createEntityManager();
        TypedQuery<DutyEntity> query = this.entityManager.createQuery("SELECT d FROM DutyEntity d "
            + "INNER JOIN d.sectionMonthlySchedules sms "
            + "INNER JOIN sms.section s "
            + "WHERE d.start >= :start AND d.start <= :end "
            + "AND s.sectionId = :sectionId "
            + "AND sms.isReadyForDutyScheduler = :isReadyForDutyScheduler "
            + "AND sms.isReadyForOrganisationManager = :isReadyForOrganisationManager "
            + "AND sms.isPublished = :isPublished", DutyEntity.class
        );

        query.setMaxResults(300);
        query.setParameter("start", start);
        query.setParameter("end", end);
        query.setParameter("sectionId", section.getSectionId());
        query.setParameter("isReadyForDutyScheduler", isReadyForDutyScheduler);
        query.setParameter("isReadyForOrganisationManager", isReadyForOrganisationManager);
        query.setParameter("isPublished", isPublished);

        List<DutyEntity> result = query.getResultList();

        this.tearDown();
        return result;
    }

    /**
     * Persists a new duty.
     *
     * @param elem The duty to persist
     * @return The persisted duty filled with its Identifier
     */
    @Override
    public Optional<DutyEntity> persist(DutyEntity elem) {
        this.createEntityManager();
        this.entityManager.persist(elem);
        this.tearDown();
        return Optional.of(elem);
    }

    /**
     * Updates an existing duty.
     *
     * @param elem The duty to be updated
     * @return The updated duty
     */
    @Override
    public Optional<DutyEntity> update(DutyEntity elem) {
        this.createEntityManager();
        this.entityManager.merge(elem);
        this.tearDown();
        return Optional.of(elem);
    }

    /**
     * Removes a duty.
     *
     * @param elem The duty to be removed.
     * @return True if the duty was removed
     */
    @Override
    public Boolean remove(DutyEntity elem) {
        this.createEntityManager();
        this.entityManager.remove(elem);
        this.tearDown();
        return true;
    }
}
