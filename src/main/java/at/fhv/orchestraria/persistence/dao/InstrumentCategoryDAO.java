package at.fhv.orchestraria.persistence.dao;

import at.fhv.orchestraria.domain.model.InstrumentCategoryEntityC;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Team C
 */

public class InstrumentCategoryDAO implements DaoBase<InstrumentCategoryEntityC> {
    private SessionManager _sessionManager;

    protected InstrumentCategoryDAO(SessionManager sessionManager) {
        _sessionManager = sessionManager;
    }

    @Override
    public synchronized Optional<InstrumentCategoryEntityC> get(int id) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        Optional <InstrumentCategoryEntityC> ice = Optional.ofNullable(session.get(InstrumentCategoryEntityC.class, id));
        ta.commit();
        return ice;
    }

    @Override
    public synchronized List<InstrumentCategoryEntityC> getAll() {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        List<InstrumentCategoryEntityC> ices = session.createQuery("SELECT a FROM InstrumentCategoryEntity a", InstrumentCategoryEntityC.class).getResultList();
        List <InstrumentCategoryEntityC> wrappedInstrumentCategories = new ArrayList<>(ices.size());
        for(InstrumentCategoryEntityC ice: ices){
            wrappedInstrumentCategories.add(ice);
        }
        ta.commit();
        return wrappedInstrumentCategories;
    }

    @Override
    public synchronized void save(InstrumentCategoryEntityC instrumentCategoryEntity) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        session.saveOrUpdate(instrumentCategoryEntity);
        ta.commit();
    }

    @Override
    public synchronized InstrumentCategoryEntityC update(InstrumentCategoryEntityC instrumentCategoryEntity) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        session.saveOrUpdate(instrumentCategoryEntity);
        ta.commit();
        return instrumentCategoryEntity;
    }

    @Override
    public synchronized void delete(InstrumentCategoryEntityC instrumentCategoryEntity) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        session.delete(instrumentCategoryEntity);
        ta.commit();
    }
}
