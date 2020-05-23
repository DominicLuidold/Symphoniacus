package at.fhv.orchestraria.persistence.dao;


import at.fhv.orchestraria.domain.model.MusicianEntity;
import at.fhv.orchestraria.domain.model.SectionEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author Team C
 */

public class MusicianDAO implements DaoBase<MusicianEntity>{

    private SessionManager _sessionManager;

    protected MusicianDAO(SessionManager sessionFactory){
        _sessionManager = sessionFactory;
    }

    /**
     * Get all musicians of a specified section
     * @param sectionID sectionID of the searched musicians
     * @return Collection of musicians of the section
     */
    public synchronized Collection<MusicianEntity> getAllBySectionID(int sectionID) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        SectionEntity section =  session.createQuery("FROM SectionEntity a WHERE a.sectionId="+sectionID,SectionEntity.class).getSingleResult();
        Collection<MusicianEntity> musicians = section.getMusicians();
        ta.commit();
        return musicians;
    }

    @Override
    public synchronized Optional<MusicianEntity> get(int id) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        Optional<MusicianEntity> me = Optional.ofNullable(session.get(MusicianEntity.class , id));
        ta.commit();
        return me;
    }

    @Override
    public synchronized List<MusicianEntity> getAll() {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        List<MusicianEntity> mes =  session.createQuery("SELECT a FROM MusicianEntity a",MusicianEntity.class).getResultList();
        List<MusicianEntity> wrappedMusicians = new ArrayList<>(mes.size());
        for(MusicianEntity me : mes){
            wrappedMusicians.add(me);
        }
        ta.commit();
        return wrappedMusicians;
    }

    @Override
    public synchronized void save(MusicianEntity musician) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        session.saveOrUpdate(musician);
        ta.commit();
    }

    /**
     *
     * @param musicianlist is a list of musicians;
     * updates a list of musicians with only one transaction;
     */
    public synchronized void updatelist(List<MusicianEntity> musicianlist){
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        for (MusicianEntity m:musicianlist) {
            MusicianEntity me = (MusicianEntity) session.merge(m);
        }
        ta.commit();
    }

    /**
     *
     * @param musicanlist is a list of musicians;
     * saves a list of musicians with only one transaction;
     */
    public synchronized void savelist(List<MusicianEntity> musicanlist){
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        for (MusicianEntity d:musicanlist) {
            session.saveOrUpdate(d);
        }
        ta.commit();
    }

    public synchronized MusicianEntity update(MusicianEntity musician) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        MusicianEntity m = (MusicianEntity) session.merge(musician);
        ta.commit();
        return m;
    }

    @Override
    public synchronized void delete(MusicianEntity musician) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        session.delete(musician);
        ta.commit();
    }
}
