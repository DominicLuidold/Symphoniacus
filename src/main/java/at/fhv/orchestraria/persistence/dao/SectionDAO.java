package at.fhv.orchestraria.persistence.dao;

import at.fhv.orchestraria.domain.model.SectionEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Team C
 */

public class SectionDAO implements DaoBase<SectionEntity>{
    private SessionManager _sessionManager;

    protected SectionDAO(SessionManager sessionFactory) {
        _sessionManager = sessionFactory;
    }

    @Override
    public synchronized Optional<SectionEntity> get(int id) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        Optional<SectionEntity> se =  Optional.ofNullable(session.get(SectionEntity.class, id));
        ta.commit();
        return se;
    }

    @Override
    public synchronized List<SectionEntity> getAll() {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        List<SectionEntity> ses=  session.createQuery("SELECT a FROM SectionEntity a", SectionEntity.class).getResultList();
        List<SectionEntity> wrappedSections = new ArrayList<>(ses.size());
        for(SectionEntity se: ses){
            wrappedSections.add(se);
        }
        ta.commit();
        return wrappedSections;
    }

    @Override
    public synchronized void save(SectionEntity sectionEntity) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        session.saveOrUpdate(sectionEntity);
        ta.commit();
    }

    @Override
    public synchronized SectionEntity update(SectionEntity sectionEntity) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        SectionEntity se = (SectionEntity) session.merge(sectionEntity);
        ta.commit();
        return se;
    }

    @Override
    public synchronized void delete(SectionEntity sectionEntity) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        session.delete(sectionEntity);
        ta.commit();
    }
}
