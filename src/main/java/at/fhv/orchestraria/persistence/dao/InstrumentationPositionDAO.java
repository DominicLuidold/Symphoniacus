package at.fhv.orchestraria.persistence.dao;

import at.fhv.orchestraria.domain.model.InstrumentationPositionEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Team C
 */

public class InstrumentationPositionDAO implements DaoBase<InstrumentationPositionEntity> {
    private SessionManager _sessionManager;

    protected InstrumentationPositionDAO(SessionManager sessionManager) {
        _sessionManager = sessionManager;
    }

    @Override
    public synchronized Optional<InstrumentationPositionEntity> get(int id) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        Optional<InstrumentationPositionEntity> ipe = Optional.ofNullable(session.get(InstrumentationPositionEntity.class, id));
        ta.commit();
        return ipe;
    }

    @Override
    public synchronized List<InstrumentationPositionEntity> getAll() {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        List<InstrumentationPositionEntity> ipes =session.createQuery("SELECT a FROM InstrumentationPositionEntity a", InstrumentationPositionEntity.class).getResultList();
        List <InstrumentationPositionEntity> wrappedInstrumentationPositions = new ArrayList<>(ipes.size());
        for(InstrumentationPositionEntity ipe: ipes){
            wrappedInstrumentationPositions.add(ipe);
        }
        ta.commit();
        return wrappedInstrumentationPositions;
    }

    @Override
    public synchronized void save(InstrumentationPositionEntity instrumentationPositionEntity) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        session.saveOrUpdate(instrumentationPositionEntity);
        ta.commit();
    }

    @Override
    public synchronized InstrumentationPositionEntity update(InstrumentationPositionEntity instrumentationPositionEntity) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        InstrumentationPositionEntity ipe = (InstrumentationPositionEntity) session.merge(instrumentationPositionEntity);
        ta.commit();
        return ipe;
    }

    @Override
    public synchronized void delete(InstrumentationPositionEntity instrumentationPositionEntity) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        session.delete(instrumentationPositionEntity);
        ta.commit();
    }
}
