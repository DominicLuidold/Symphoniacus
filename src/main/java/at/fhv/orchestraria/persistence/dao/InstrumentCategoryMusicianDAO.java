package at.fhv.orchestraria.persistence.dao;

import at.fhv.orchestraria.domain.model.InstrumentCategoryMusicianEntityC;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InstrumentCategoryMusicianDAO implements DaoBase<InstrumentCategoryMusicianEntityC> {
    private SessionManager _sessionManager;

    protected InstrumentCategoryMusicianDAO(SessionManager sessionManager){ _sessionManager =sessionManager;}

    @Override
    public synchronized Optional<InstrumentCategoryMusicianEntityC> get(int id) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        Optional<InstrumentCategoryMusicianEntityC> icme = Optional.ofNullable(session.get(InstrumentCategoryMusicianEntityC.class, id));
        ta.commit();
        return icme;
    }

    @Override
    public synchronized List<InstrumentCategoryMusicianEntityC> getAll() {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        List<InstrumentCategoryMusicianEntityC> icmes=  session.createQuery("SELECT a FROM InstrumentCategoryMusicianEntity a", InstrumentCategoryMusicianEntityC.class).getResultList();
        List<InstrumentCategoryMusicianEntityC> wrappedICME = new ArrayList<>(icmes.size());
        for(InstrumentCategoryMusicianEntityC icme:icmes){
            wrappedICME.add(icme);
        }
        ta.commit();
        return wrappedICME;
    }

    @Override
    public synchronized void save(InstrumentCategoryMusicianEntityC entity) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        session.saveOrUpdate(entity);
        ta.commit();
    }

    @Override
    public synchronized InstrumentCategoryMusicianEntityC update(InstrumentCategoryMusicianEntityC entity) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        InstrumentCategoryMusicianEntityC icme = (InstrumentCategoryMusicianEntityC) session.merge(entity);
        ta.commit();
        return icme;
    }

    @Override
    public synchronized void delete(InstrumentCategoryMusicianEntityC entity) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        session.delete(entity);
        ta.commit();
    }
}
