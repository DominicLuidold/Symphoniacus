package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.DAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class DutyDAO implements DAO<Duty> {

    protected EntityManager _em;

    public DutyDAO(EntityManagerFactory entityManagerFactory) {
        _em = entityManagerFactory.createEntityManager();
    }

    @Override
    public Optional<Duty> find(int dutyId) {
        return Optional.empty();
    }

    @Override
    public List<Duty> findAll() {
        TypedQuery<Duty> query = _em.createQuery("from Duty", Duty.class);
        query.setMaxResults(300);
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
