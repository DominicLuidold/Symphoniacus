package at.fhv.orchestraria.persistence.dao;

import at.fhv.orchestraria.domain.model.InstrumentCategoryEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Team C
 */

public class InstrumentCategoryDAO implements DaoBase<InstrumentCategoryEntity> {
    private SessionManager _sessionManager;

    protected InstrumentCategoryDAO(SessionManager sessionManager) {
        _sessionManager = sessionManager;
    }

    @Override
    public synchronized Optional<InstrumentCategoryEntity> get(int id) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        Optional <InstrumentCategoryEntity> ice = Optional.ofNullable(session.get(InstrumentCategoryEntity.class, id));
        ta.commit();
        return ice;
    }

    @Override
    public synchronized List<InstrumentCategoryEntity> getAll() {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        List<InstrumentCategoryEntity> ices = session.createQuery("SELECT a FROM InstrumentCategoryEntity a", InstrumentCategoryEntity.class).getResultList();
        List <InstrumentCategoryEntity> wrappedInstrumentCategories = new ArrayList<>(ices.size());
        for(InstrumentCategoryEntity ice: ices){
            wrappedInstrumentCategories.add(ice);
        }
        ta.commit();
        return wrappedInstrumentCategories;
    }

    @Override
    public synchronized void save(InstrumentCategoryEntity instrumentCategoryEntity) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        session.saveOrUpdate(instrumentCategoryEntity);
        ta.commit();
    }

    @Override
    public synchronized InstrumentCategoryEntity update(InstrumentCategoryEntity instrumentCategoryEntity) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        session.saveOrUpdate(instrumentCategoryEntity);
        ta.commit();
        return instrumentCategoryEntity;
    }

    @Override
    public synchronized void delete(InstrumentCategoryEntity instrumentCategoryEntity) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        session.delete(instrumentCategoryEntity);
        ta.commit();
    }
}
