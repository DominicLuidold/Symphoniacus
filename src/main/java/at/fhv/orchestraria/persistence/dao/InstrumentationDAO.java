package at.fhv.orchestraria.persistence.dao;

import at.fhv.orchestraria.domain.model.InstrumentationEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Team C
 */

public class InstrumentationDAO implements DaoBase<InstrumentationEntity> {
    private SessionManager _sessionManager;

    public InstrumentationDAO(SessionManager sessionFactory) {
        _sessionManager = sessionFactory;
    }

    @Override
    public synchronized Optional<InstrumentationEntity> get(int id) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        Optional<InstrumentationEntity> ie = Optional.ofNullable(session.get(InstrumentationEntity.class, id));
        ta.commit();
        return ie;
    }

    @Override
    public synchronized List<InstrumentationEntity> getAll() {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        List<InstrumentationEntity> ies = session.createQuery("SELECT a FROM InstrumentationEntity a", InstrumentationEntity.class).getResultList();
        List<InstrumentationEntity> wrappedInstrumentations = new ArrayList<>(ies.size());
        for(InstrumentationEntity ie : ies){
            wrappedInstrumentations.add(ie);
        }
        ta.commit();
        return wrappedInstrumentations;
    }

    @Override
    public synchronized void save(InstrumentationEntity instrumentationEntity) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        session.saveOrUpdate(instrumentationEntity);
        ta.commit();
    }

    @Override
    public synchronized InstrumentationEntity update(InstrumentationEntity instrumentationEntity) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        InstrumentationEntity ie = (InstrumentationEntity) session.merge(instrumentationEntity);
        ta.commit();
        return ie;
    }

    @Override
    public synchronized void delete(InstrumentationEntity instrumentationEntity) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        session.delete(instrumentationEntity);
        ta.commit();
    }
}
