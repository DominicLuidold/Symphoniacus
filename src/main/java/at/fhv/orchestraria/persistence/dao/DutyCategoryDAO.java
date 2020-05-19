package at.fhv.orchestraria.persistence.dao;

import at.fhv.orchestraria.domain.model.DutyCategoryEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Team C
 */

public class DutyCategoryDAO implements DaoBase<DutyCategoryEntity> {

    private SessionFactory _sessionFactory;

    @Override
    public Optional<DutyCategoryEntity> get(int id) {
        Session session = _sessionFactory.openSession();
        Transaction ta = session.beginTransaction();
        Optional<DutyCategoryEntity> dce = Optional.ofNullable(session.get(DutyCategoryEntity.class, id));
        ta.commit();
        return dce;
    }

    @Override
    public List<DutyCategoryEntity> getAll() {
        Session session = _sessionFactory.openSession();
        Transaction ta = session.beginTransaction();
        List<DutyCategoryEntity> dces = session.createQuery("SELECT a FROM DutyCategoryEntity a", DutyCategoryEntity.class).getResultList();
        ta.commit();
        List<DutyCategoryEntity> wrappedCategories = new ArrayList<>(dces.size());
        for(DutyCategoryEntity dce: dces){
            wrappedCategories.add(dce);
        }
        return wrappedCategories;
    }

    @Override
    public void save(DutyCategoryEntity category) {
        Session session = _sessionFactory.openSession();
        Transaction ta = session.beginTransaction();
        session.saveOrUpdate(category);
        ta.commit();
    }

    @Override
    public DutyCategoryEntity update(DutyCategoryEntity category) {
        Session session = _sessionFactory.openSession();
        Transaction ta = session.beginTransaction();
        DutyCategoryEntity dce = (DutyCategoryEntity) session.merge(category);
        ta.commit();
        return dce;
    }

    @Override
    public void delete(DutyCategoryEntity category) {
        Session session = _sessionFactory.openSession();
        Transaction ta = session.beginTransaction();
        session.delete(category);
        ta.commit();
    }
}