package at.fhv.orchestraria.persistence.dao;


import at.fhv.orchestraria.domain.model.SectionInstrumentationEntityC;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Team C
 */

public class SectionInstrumentationDAO implements DaoBase<SectionInstrumentationEntityC> {
    private SessionManager _sessionManager;

    protected SectionInstrumentationDAO(SessionManager sessionFactory) {
        _sessionManager = sessionFactory;
    }

    @Override
    public synchronized Optional<SectionInstrumentationEntityC> get(int id) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        Optional<SectionInstrumentationEntityC> sie = Optional.ofNullable(session.get(SectionInstrumentationEntityC.class, id));
        ta.commit();
        return sie;
    }

    @Override
    public synchronized List<SectionInstrumentationEntityC> getAll() {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        List<SectionInstrumentationEntityC> sies = session.createQuery("SELECT a FROM SectionInstrumentationEntity a", SectionInstrumentationEntityC.class).getResultList();
        List<SectionInstrumentationEntityC> wrappedSectionInstrumentations = new ArrayList<>(sies.size());
        for(SectionInstrumentationEntityC sie: sies){
            wrappedSectionInstrumentations.add(sie);
        }
        ta.commit();
        return wrappedSectionInstrumentations;
    }

    @Override
    public synchronized void save(SectionInstrumentationEntityC sectionInstrumentationEntity) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        session.saveOrUpdate(sectionInstrumentationEntity);
        ta.commit();
    }

    @Override
    public synchronized SectionInstrumentationEntityC update(SectionInstrumentationEntityC sectionInstrumentationEntity) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        SectionInstrumentationEntityC sie = (SectionInstrumentationEntityC) session.merge(sectionInstrumentationEntity);
        ta.commit();
        return sie;
    }

    @Override
    public synchronized void delete(SectionInstrumentationEntityC sectionInstrumentationEntity) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        session.delete(sectionInstrumentationEntity);
        ta.commit();
    }
}
