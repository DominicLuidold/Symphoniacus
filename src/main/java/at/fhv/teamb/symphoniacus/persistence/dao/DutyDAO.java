package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDAO;
import at.fhv.teamb.symphoniacus.persistence.model.Duty;
import at.fhv.teamb.symphoniacus.persistence.model.Section;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class DutyDAO extends BaseDAO<Duty> {

    @Override
    public Optional<Duty> find(Object key) {
        return Optional.empty();
    }

    /**
     * Returns all duties that are in the week of the given start date (must be Monday).
     *
     * @param start A LocalDate of a Monday which serves as the start of the week
     * @return A List of all Duties that have the date between the given start date plus 6 days
     * @author Valentin Goronjic
     */
    public List<Duty> findAllInWeek(LocalDateTime start) {
        createEntityManager();
        TypedQuery<Duty> query = this.entityManager.createQuery("SELECT d FROM Duty d WHERE d.start >= :start AND d.end <= :end", Duty.class);
        query.setMaxResults(300);
        query.setParameter("start", start);
        query.setParameter("end", start.plusDays(6));
        List<Duty> resultList = query.getResultList();
        tearDown();
        return resultList;
    }

    //TODO - IMPLEMENT
    public List<Duty> findAllInWeek(Section sectionOfUser, LocalDateTime monday) {
        return null;
    }

    /**
     * Returns all duties that are in the week of the given start and end date
     *
     * @param start A LocalDate that represents the start of the week
     * @param end   A LocalDate that represents the end of the week
     * @return A List of all Duties that have the date between the given start and end dates
     * @author Valentin Goronjic
     */
    public List<Duty> findAllInRange(LocalDateTime start, LocalDateTime end) {
        createEntityManager();
        TypedQuery<Duty> query = this.entityManager.createQuery("SELECT d FROM Duty d WHERE d.start >= :start AND d.end <= :end", Duty.class);
        query.setMaxResults(300);
        query.setParameter("start", start);
        query.setParameter("end", end);
        List<Duty> resultList = query.getResultList();
        tearDown();
        return resultList;
    }

    //TODO - IMPLEMENT
    public List<Duty> findAllInRange(Section section, LocalDateTime start, LocalDateTime end) {
        return null;
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
