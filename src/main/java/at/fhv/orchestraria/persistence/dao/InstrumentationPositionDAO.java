package at.fhv.orchestraria.persistence.dao;

import at.fhv.orchestraria.domain.model.InstrumentationPositionEntityC;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Team C
 */

public class InstrumentationPositionDAO implements DaoBase<InstrumentationPositionEntityC> {
    private SessionManager _sessionManager;

    protected InstrumentationPositionDAO(SessionManager sessionManager) {
        _sessionManager = sessionManager;
    }

    @Override
    public synchronized Optional<InstrumentationPositionEntityC> get(int id) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        Optional<InstrumentationPositionEntityC> ipe = Optional.ofNullable(session.get(InstrumentationPositionEntityC.class, id));
        ta.commit();
        return ipe;
    }

    @Override
    public synchronized List<InstrumentationPositionEntityC> getAll() {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        List<InstrumentationPositionEntityC> ipes =session.createQuery("SELECT a FROM InstrumentationPositionEntity a", InstrumentationPositionEntityC.class).getResultList();
        List <InstrumentationPositionEntityC> wrappedInstrumentationPositions = new ArrayList<>(ipes.size());
        for(InstrumentationPositionEntityC ipe: ipes){
            wrappedInstrumentationPositions.add(ipe);
        }
        ta.commit();
        return wrappedInstrumentationPositions;
    }

    @Override
    public synchronized void save(InstrumentationPositionEntityC instrumentationPositionEntity) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        session.saveOrUpdate(instrumentationPositionEntity);
        ta.commit();
    }

    @Override
    public synchronized InstrumentationPositionEntityC update(InstrumentationPositionEntityC instrumentationPositionEntity) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        InstrumentationPositionEntityC ipe = (InstrumentationPositionEntityC) session.merge(instrumentationPositionEntity);
        ta.commit();
        return ipe;
    }

    @Override
    public synchronized void delete(InstrumentationPositionEntityC instrumentationPositionEntity) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        session.delete(instrumentationPositionEntity);
        ta.commit();
    }
}
