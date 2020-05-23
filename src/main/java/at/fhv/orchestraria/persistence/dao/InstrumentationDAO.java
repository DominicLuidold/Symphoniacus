package at.fhv.orchestraria.persistence.dao;

import at.fhv.orchestraria.domain.model.InstrumentationEntityC;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Team C
 */

public class InstrumentationDAO implements DaoBase<InstrumentationEntityC> {
    private SessionManager _sessionManager;

    public InstrumentationDAO(SessionManager sessionFactory) {
        _sessionManager = sessionFactory;
    }

    @Override
    public synchronized Optional<InstrumentationEntityC> get(int id) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        Optional<InstrumentationEntityC> ie = Optional.ofNullable(session.get(InstrumentationEntityC.class, id));
        ta.commit();
        return ie;
    }

    @Override
    public synchronized List<InstrumentationEntityC> getAll() {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        List<InstrumentationEntityC> ies = session.createQuery("SELECT a FROM InstrumentationEntity a", InstrumentationEntityC.class).getResultList();
        List<InstrumentationEntityC> wrappedInstrumentations = new ArrayList<>(ies.size());
        for(InstrumentationEntityC ie : ies){
            wrappedInstrumentations.add(ie);
        }
        ta.commit();
        return wrappedInstrumentations;
    }

    @Override
    public synchronized void save(InstrumentationEntityC instrumentationEntity) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        session.saveOrUpdate(instrumentationEntity);
        ta.commit();
    }

    @Override
    public synchronized InstrumentationEntityC update(InstrumentationEntityC instrumentationEntity) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        InstrumentationEntityC ie = (InstrumentationEntityC) session.merge(instrumentationEntity);
        ta.commit();
        return ie;
    }

    @Override
    public synchronized void delete(InstrumentationEntityC instrumentationEntity) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        session.delete(instrumentationEntity);
        ta.commit();
    }
}
