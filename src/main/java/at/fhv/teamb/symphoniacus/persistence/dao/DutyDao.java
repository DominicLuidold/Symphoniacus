package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.Duty;
import at.fhv.teamb.symphoniacus.persistence.model.Section;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import javax.persistence.TypedQuery;

public class DutyDao extends BaseDao<Duty> {

    @Override
    public Optional<Duty> find(Object key) {
        return Optional.empty();
    }

    /**
     * @see #findAllInRange(LocalDateTime, LocalDateTime) 
     */
    public List<Duty> findAllInWeek(LocalDateTime start) {
        return findAllInRange(start, start.plusDays(6));
    }

    /**
     * @see #findAllInRange(Section, LocalDateTime, LocalDateTime) 
     * @param section
     * @param start
     * @return
     */
    public List<Duty> findAllInWeek(Section section, LocalDateTime start) {
        return findAllInRange(section,start, start.plusDays(6));
    }

    /**
     * Returns all duties that are in the week of the given start and end date.
     *
     * @param start A LocalDate that represents the start of the week
     * @param end   A LocalDate that represents the end of the week
     * @return A List of all Duties that have the date between the given start and end dates
     * @author Valentin Goronjic
     */
    public List<Duty> findAllInRange(LocalDateTime start, LocalDateTime end) {
        createEntityManager();
        TypedQuery<Duty> query = this.entityManager.createQuery(
            "SELECT d FROM Duty d WHERE d.start >= :start AND d.end <= :end",
            Duty.class
        );
        query.setMaxResults(300);
        query.setParameter("start", start);
        query.setParameter("end", end);
        List<Duty> resultList = query.getResultList();
        tearDown();
        return resultList;
    }

    /**
     * Returns all duties in a specific time range that have a sectionMonthlySchedule with this section's sectionId.
     * @param section
     * @param start
     * @param end
     * @return
     */
    public List<Duty> findAllInRange(Section section, LocalDateTime start, LocalDateTime end) {
        if (section == null || start == null || end == null) {
            // TODO use a proper logger?
            System.out.println("Cannot findAllInRange with null arguments");
            return new LinkedList<>();
        }
        this.createEntityManager();
        // TODO check if this is ok ?????
        TypedQuery<Duty> query = this.entityManager.createQuery("SELECT d FROM Duty d " +
                "INNER JOIN SectionMonthlySchedule sms ON d.sectionMonthlyScheduleId = sms.sectionMonthlyScheduleId " +
                "INNER JOIN Section s ON sms.sectionId = s.sectionId " +
                "WHERE d.start >= :start AND d.end <= :end " +
                "AND s.sectionId = :sectionId", Duty.class);
        query.setMaxResults(300);
        query.setParameter("start", start);
        query.setParameter("end", end);
        query.setParameter("sectionId", section.getSectionId());

        List<Duty> resultList = query.getResultList();
        tearDown();
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Duty> persist(Duty elem) {
        return Optional.empty();
    }

    @Override
    public Optional<Duty> update(Duty elem) {
        return Optional.empty();
    }

    @Override
    public Boolean remove(Duty elem) {
        return null;
    }
}
