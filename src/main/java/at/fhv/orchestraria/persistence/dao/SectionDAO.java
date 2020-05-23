package at.fhv.orchestraria.persistence.dao;

import at.fhv.orchestraria.domain.model.SectionEntityC;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Team C
 */

public class SectionDAO implements DaoBase<SectionEntityC>{
    private SessionManager _sessionManager;

    protected SectionDAO(SessionManager sessionFactory) {
        _sessionManager = sessionFactory;
    }

    @Override
    public synchronized Optional<SectionEntityC> get(int id) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        Optional<SectionEntityC> se =  Optional.ofNullable(session.get(SectionEntityC.class, id));
        ta.commit();
        return se;
    }

    @Override
    public synchronized List<SectionEntityC> getAll() {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        List<SectionEntityC> ses=  session.createQuery("SELECT a FROM SectionEntity a", SectionEntityC.class).getResultList();
        List<SectionEntityC> wrappedSections = new ArrayList<>(ses.size());
        for(SectionEntityC se: ses){
            wrappedSections.add(se);
        }
        ta.commit();
        return wrappedSections;
    }

    @Override
    public synchronized void save(SectionEntityC sectionEntity) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        session.saveOrUpdate(sectionEntity);
        ta.commit();
    }

    @Override
    public synchronized SectionEntityC update(SectionEntityC sectionEntity) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        SectionEntityC se = (SectionEntityC) session.merge(sectionEntity);
        ta.commit();
        return se;
    }

    @Override
    public synchronized void delete(SectionEntityC sectionEntity) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        session.delete(sectionEntity);
        ta.commit();
    }
}
