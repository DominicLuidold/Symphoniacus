package at.fhv.orchestraria.persistence.dao;

import at.fhv.orchestraria.domain.model.DutyCategoryChangelogEntityC;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Team C
 */

public class DutyCategoryChangelogDAO implements DaoBase<DutyCategoryChangelogEntityC> {

    private SessionFactory _sessionFactory;

    @Override
    public Optional<DutyCategoryChangelogEntityC> get(int id) {
        Session session = _sessionFactory.openSession();
        Transaction ta = session.beginTransaction();
        Optional<DutyCategoryChangelogEntityC> dcce = Optional.ofNullable(session.get(DutyCategoryChangelogEntityC.class , id));
        ta.commit();
        return dcce;
    }

    @Override
    public List<DutyCategoryChangelogEntityC> getAll() {
        Session session = _sessionFactory.openSession();
        Transaction ta = session.beginTransaction();
        List<DutyCategoryChangelogEntityC> dcces = session.createQuery("SELECT a FROM DutyCategoryChangelogEntity a", DutyCategoryChangelogEntityC.class).getResultList();
        ta.commit();
        List<DutyCategoryChangelogEntityC> wrappedChangelogs = new ArrayList<>(dcces.size());
        for(DutyCategoryChangelogEntityC dcce:dcces){
            wrappedChangelogs.add(dcce);
        }
        return wrappedChangelogs;
    }

    @Override
    public void save(DutyCategoryChangelogEntityC changelog) {
        Session session = _sessionFactory.openSession();
        Transaction ta = session.beginTransaction();
        session.saveOrUpdate(changelog);
        ta.commit();
    }

    @Override
    public synchronized DutyCategoryChangelogEntityC update(DutyCategoryChangelogEntityC changelog) {
        Session session = _sessionFactory.openSession();
        Transaction ta = session.beginTransaction();
        DutyCategoryChangelogEntityC dcce = (DutyCategoryChangelogEntityC) session.merge(changelog);
        ta.commit();
        return dcce;
    }

    @Override
    public synchronized void delete(DutyCategoryChangelogEntityC changelog) {
        Session session = _sessionFactory.openSession();
        Transaction ta = session.beginTransaction();
        session.delete(changelog);
        ta.commit();
    }
}