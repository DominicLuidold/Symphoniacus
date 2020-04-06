package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.DAO;
import at.fhv.teamb.symphoniacus.persistence.model.Duty;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class DutyDAO implements DAO<Duty> {

    protected EntityManager _em;

    public DutyDAO(EntityManagerFactory entityManagerFactory) {
        _em = entityManagerFactory.createEntityManager();
    }

    @Override
    public Optional<Duty> find(Object key) {
        return Optional.empty();
    }

    @Override
    public List<Duty> findAll() {
        TypedQuery<Duty> query = _em.createQuery("from Duty", Duty.class);
        query.setMaxResults(300);
        return query.getResultList();
    }

    /**
     * Returns all duties that are in the week of the given start and end date
     *
     * @param start A LocalDate that represents the start of the week
     * @param end A LocalDate that represents the end of the week
     * @return A List of all Duties that have the date between the given start and end dates
     * @author Valentin Goronjic
     */
    public List<Duty> findAllInWeek(LocalDate start, LocalDate end) {
        TypedQuery<Duty> query = _em.createQuery("SELECT d FROM Duty d WHERE d._date BETWEEN :start AND :end", Duty.class);
        query.setMaxResults(300);
        query.setParameter("start", start);
        query.setParameter("end", end);
        return query.getResultList();
    }

    /**
     * Returns all duties that are in the week of the given start date (must be Monday).
     *
     * @param start A LocalDate of a Monday which serves as the start of the week
     * @return A List of all Duties that have the date between the given start date plus 6 days
     * @author Valentin Goronjic
     */
    public List<Duty> findAllInWeek(LocalDate start) {
        TypedQuery<Duty> query = _em.createQuery("SELECT d FROM Duty d WHERE d._date BETWEEN :start AND :end", Duty.class);
        query.setMaxResults(300);
        query.setParameter("start", start);
        query.setParameter("end", start.plusDays(6));
        return query.getResultList();
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
