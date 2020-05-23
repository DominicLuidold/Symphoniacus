package at.fhv.orchestraria.persistence.dao;

import at.fhv.orchestraria.domain.model.DutyCategoryEntityC;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Team C
 */

public class DutyCategoryDAO implements DaoBase<DutyCategoryEntityC> {

    private SessionFactory _sessionFactory;

    @Override
    public Optional<DutyCategoryEntityC> get(int id) {
        Session session = _sessionFactory.openSession();
        Transaction ta = session.beginTransaction();
        Optional<DutyCategoryEntityC> dce = Optional.ofNullable(session.get(DutyCategoryEntityC.class, id));
        ta.commit();
        return dce;
    }

    @Override
    public List<DutyCategoryEntityC> getAll() {
        Session session = _sessionFactory.openSession();
        Transaction ta = session.beginTransaction();
        List<DutyCategoryEntityC> dces = session.createQuery("SELECT a FROM DutyCategoryEntity a", DutyCategoryEntityC.class).getResultList();
        ta.commit();
        List<DutyCategoryEntityC> wrappedCategories = new ArrayList<>(dces.size());
        for(DutyCategoryEntityC dce: dces){
            wrappedCategories.add(dce);
        }
        return wrappedCategories;
    }

    @Override
    public void save(DutyCategoryEntityC category) {
        Session session = _sessionFactory.openSession();
        Transaction ta = session.beginTransaction();
        session.saveOrUpdate(category);
        ta.commit();
    }

    @Override
    public DutyCategoryEntityC update(DutyCategoryEntityC category) {
        Session session = _sessionFactory.openSession();
        Transaction ta = session.beginTransaction();
        DutyCategoryEntityC dce = (DutyCategoryEntityC) session.merge(category);
        ta.commit();
        return dce;
    }

    @Override
    public void delete(DutyCategoryEntityC category) {
        Session session = _sessionFactory.openSession();
        Transaction ta = session.beginTransaction();
        session.delete(category);
        ta.commit();
    }
}