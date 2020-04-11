package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.Duty;
import at.fhv.teamb.symphoniacus.persistence.model.Section;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import javax.persistence.TypedQuery;

/**
 * DAO for Duty class.
 * @author Valentin Goronjic
 */
public class DutyDao extends BaseDao<Duty> {

    /**
     * Finds a duty by its key.
     *
     * @param key The key of the duty
     * @return The duty that is looked for
     */
    @Override
    public Optional<Duty> find(Object key) {
        this.createEntityManager();
        Duty d = this.entityManager.find(Duty.class, key);
        this.tearDown();
        return Optional.of(d);
    }

    /**
     * Finds all duties in a week.
     *
     * @return A List of the corresponding duties that were found
     * @see #findAllInRange(LocalDateTime, LocalDateTime)
     */
    public List<Duty> findAllInWeek(LocalDateTime start) {
        return findAllInRange(start, start.plusDays(6));
    }

    /**
     * Returns all duties that are in the week of the given start and end date.
     *
     * @param start A LocalDateTime that represents the start
     * @param end   A LocalDateTime that represents the end
     * @return A List of all Duties that have the date between the given start and end dates
     */
    public List<Duty> findAllInRange(LocalDateTime start, LocalDateTime end) {
        this.createEntityManager();
        TypedQuery<Duty> query = this.entityManager.createQuery(
            "SELECT d FROM Duty d WHERE d.start >= :start AND d.end <= :end",
            Duty.class
        );
        query.setMaxResults(300);
        query.setParameter("start", start);
        query.setParameter("end", end);
        List<Duty> resultList = query.getResultList();
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
     *      (Section, LocalDateTime, LocalDateTime, boolean, boolean, boolean)
     */
    public List<Duty> findAllInRangeWithSection(
        Section section,
        LocalDateTime start,
        boolean isReadyForDutyScheduler,
        boolean isReadyForOrganisationManager,
        boolean isPublished
    ) {
        return findAllInRangeWithSection(section, start, start.plusDays(6),
            isReadyForDutyScheduler,
            isReadyForOrganisationManager,
            isPublished);
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
    public List<Duty> findAllInRangeWithSection(
        Section section,
        LocalDateTime start,
        LocalDateTime end,
        boolean isReadyForDutyScheduler,
        boolean isReadyForOrganisationManager,
        boolean isPublished
    ) {
        this.createEntityManager();
        TypedQuery<Duty> query = this.entityManager.createQuery("SELECT d FROM Duty d "
            + "INNER JOIN SectionMonthlySchedule sms "
            + "ON d.sectionMonthlyScheduleId = sms.sectionMonthlyScheduleId "
            + "INNER JOIN Section s ON sms.sectionId = s.sectionId "
            + "WHERE d.start >= :start AND d.end <= :end "
            + "AND s.sectionId = :sectionId "
            + "AND sms.isReadyForDutyScheduler = :isReadyForDutyScheduler "
            + "AND sms.isReadyForOrganisationManager = :isReadyForOrganisationManager "
            + "AND sms.isPublished = :isPublished", Duty.class);
        query.setMaxResults(300);
        query.setParameter("start", start);
        query.setParameter("end", end);
        query.setParameter("sectionId", section.getSectionId());
        query.setParameter("isReadyForDutyScheduler", isReadyForDutyScheduler);
        query.setParameter("isReadyForOrganisationManager", isReadyForOrganisationManager);
        query.setParameter("isPublished", isPublished);

        List<Duty> resultList = query.getResultList();
        this.tearDown();
        return resultList;
    }

    /**
     * Persists a new duty.
     * @param elem The duty to persist
     * @return The persisted duty filled with its Identifier
     */
    @Override
    public Optional<Duty> persist(Duty elem) {
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
    public Optional<Duty> update(Duty elem) {
        this.createEntityManager();
        this.entityManager.merge(elem);
        this.tearDown();
        return Optional.of(elem);
    }

    /**
     * Removes a duty.
     * @param elem The duty to be removed.
     * @return True if the duty was removed
     */
    @Override
    public Boolean remove(Duty elem) {
        this.createEntityManager();
        this.entityManager.remove(elem);
        this.tearDown();
        return true;
    }
}
