package at.fhv.orchestraria.persistence.dao;

import at.fhv.orchestraria.domain.model.InstrumentCategoryMusicianEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InstrumentCategoryMusicianDAO implements DaoBase<InstrumentCategoryMusicianEntity> {
    private SessionManager _sessionManager;

    protected InstrumentCategoryMusicianDAO(SessionManager sessionManager){ _sessionManager =sessionManager;}

    @Override
    public synchronized Optional<InstrumentCategoryMusicianEntity> get(int id) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        Optional<InstrumentCategoryMusicianEntity> icme = Optional.ofNullable(session.get(InstrumentCategoryMusicianEntity.class, id));
        ta.commit();
        return icme;
    }

    @Override
    public synchronized List<InstrumentCategoryMusicianEntity> getAll() {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        List<InstrumentCategoryMusicianEntity> icmes=  session.createQuery("SELECT a FROM InstrumentCategoryMusicianEntity a", InstrumentCategoryMusicianEntity.class).getResultList();
        List<InstrumentCategoryMusicianEntity> wrappedICME = new ArrayList<>(icmes.size());
        for(InstrumentCategoryMusicianEntity icme:icmes){
            wrappedICME.add(icme);
        }
        ta.commit();
        return wrappedICME;
    }

    @Override
    public synchronized void save(InstrumentCategoryMusicianEntity entity) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        session.saveOrUpdate(entity);
        ta.commit();
    }

    @Override
    public synchronized InstrumentCategoryMusicianEntity update(InstrumentCategoryMusicianEntity entity) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        InstrumentCategoryMusicianEntity icme = (InstrumentCategoryMusicianEntity) session.merge(entity);
        ta.commit();
        return icme;
    }

    @Override
    public synchronized void delete(InstrumentCategoryMusicianEntity entity) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        session.delete(entity);
        ta.commit();
    }
}
