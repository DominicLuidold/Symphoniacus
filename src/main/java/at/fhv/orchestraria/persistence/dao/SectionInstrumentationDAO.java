package at.fhv.orchestraria.persistence.dao;


import at.fhv.orchestraria.domain.model.SectionInstrumentationEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Team C
 */

public class SectionInstrumentationDAO implements DaoBase<SectionInstrumentationEntity> {
    private SessionManager _sessionManager;

    protected SectionInstrumentationDAO(SessionManager sessionFactory) {
        _sessionManager = sessionFactory;
    }

    @Override
    public synchronized Optional<SectionInstrumentationEntity> get(int id) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        Optional<SectionInstrumentationEntity> sie = Optional.ofNullable(session.get(SectionInstrumentationEntity.class, id));
        ta.commit();
        return sie;
    }

    @Override
    public synchronized List<SectionInstrumentationEntity> getAll() {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        List<SectionInstrumentationEntity> sies = session.createQuery("SELECT a FROM SectionInstrumentationEntity a", SectionInstrumentationEntity.class).getResultList();
        List<SectionInstrumentationEntity> wrappedSectionInstrumentations = new ArrayList<>(sies.size());
        for(SectionInstrumentationEntity sie: sies){
            wrappedSectionInstrumentations.add(sie);
        }
        ta.commit();
        return wrappedSectionInstrumentations;
    }

    @Override
    public synchronized void save(SectionInstrumentationEntity sectionInstrumentationEntity) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        session.saveOrUpdate(sectionInstrumentationEntity);
        ta.commit();
    }

    @Override
    public synchronized SectionInstrumentationEntity update(SectionInstrumentationEntity sectionInstrumentationEntity) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        SectionInstrumentationEntity sie = (SectionInstrumentationEntity) session.merge(sectionInstrumentationEntity);
        ta.commit();
        return sie;
    }

    @Override
    public synchronized void delete(SectionInstrumentationEntity sectionInstrumentationEntity) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        session.delete(sectionInstrumentationEntity);
        ta.commit();
    }
}
