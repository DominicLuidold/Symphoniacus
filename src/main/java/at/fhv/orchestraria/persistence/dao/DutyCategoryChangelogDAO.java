package at.fhv.orchestraria.persistence.dao;

import at.fhv.orchestraria.domain.model.DutyCategoryChangelogEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Team C
 */

public class DutyCategoryChangelogDAO implements DaoBase<DutyCategoryChangelogEntity> {

    private SessionFactory _sessionFactory;

    @Override
    public Optional<DutyCategoryChangelogEntity> get(int id) {
        Session session = _sessionFactory.openSession();
        Transaction ta = session.beginTransaction();
        Optional<DutyCategoryChangelogEntity> dcce = Optional.ofNullable(session.get(DutyCategoryChangelogEntity.class , id));
        ta.commit();
        return dcce;
    }

    @Override
    public List<DutyCategoryChangelogEntity> getAll() {
        Session session = _sessionFactory.openSession();
        Transaction ta = session.beginTransaction();
        List<DutyCategoryChangelogEntity> dcces = session.createQuery("SELECT a FROM DutyCategoryChangelogEntity a",DutyCategoryChangelogEntity.class).getResultList();
        ta.commit();
        List<DutyCategoryChangelogEntity> wrappedChangelogs = new ArrayList<>(dcces.size());
        for(DutyCategoryChangelogEntity dcce:dcces){
            wrappedChangelogs.add(dcce);
        }
        return wrappedChangelogs;
    }

    @Override
    public void save(DutyCategoryChangelogEntity changelog) {
        Session session = _sessionFactory.openSession();
        Transaction ta = session.beginTransaction();
        session.saveOrUpdate(changelog);
        ta.commit();
    }

    @Override
    public synchronized DutyCategoryChangelogEntity update(DutyCategoryChangelogEntity changelog) {
        Session session = _sessionFactory.openSession();
        Transaction ta = session.beginTransaction();
        DutyCategoryChangelogEntity dcce = (DutyCategoryChangelogEntity) session.merge(changelog);
        ta.commit();
        return dcce;
    }

    @Override
    public synchronized void delete(DutyCategoryChangelogEntity changelog) {
        Session session = _sessionFactory.openSession();
        Transaction ta = session.beginTransaction();
        session.delete(changelog);
        ta.commit();
    }
}