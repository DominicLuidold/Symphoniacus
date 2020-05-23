package at.fhv.orchestraria.persistence.dao;


import at.fhv.orchestraria.domain.model.MusicianEntityC;
import at.fhv.orchestraria.domain.model.SectionEntityC;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author Team C
 */

public class MusicianDAO implements DaoBase<MusicianEntityC>{

    private SessionManager _sessionManager;

    protected MusicianDAO(SessionManager sessionFactory){
        _sessionManager = sessionFactory;
    }

    /**
     * Get all musicians of a specified section
     * @param sectionID sectionID of the searched musicians
     * @return Collection of musicians of the section
     */
    public synchronized Collection<MusicianEntityC> getAllBySectionID(int sectionID) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        SectionEntityC section =  session.createQuery("FROM SectionEntity a WHERE a.sectionId="+sectionID, SectionEntityC.class).getSingleResult();
        Collection<MusicianEntityC> musicians = section.getMusicians();
        ta.commit();
        return musicians;
    }

    @Override
    public synchronized Optional<MusicianEntityC> get(int id) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        Optional<MusicianEntityC> me = Optional.ofNullable(session.get(MusicianEntityC.class , id));
        ta.commit();
        return me;
    }

    @Override
    public synchronized List<MusicianEntityC> getAll() {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        List<MusicianEntityC> mes =  session.createQuery("SELECT a FROM MusicianEntity a", MusicianEntityC.class).getResultList();
        List<MusicianEntityC> wrappedMusicians = new ArrayList<>(mes.size());
        for(MusicianEntityC me : mes){
            wrappedMusicians.add(me);
        }
        ta.commit();
        return wrappedMusicians;
    }

    @Override
    public synchronized void save(MusicianEntityC musician) {
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
    public synchronized void updatelist(List<MusicianEntityC> musicianlist){
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        for (MusicianEntityC m:musicianlist) {
            MusicianEntityC me = (MusicianEntityC) session.merge(m);
        }
        ta.commit();
    }

    /**
     *
     * @param musicanlist is a list of musicians;
     * saves a list of musicians with only one transaction;
     */
    public synchronized void savelist(List<MusicianEntityC> musicanlist){
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        for (MusicianEntityC d:musicanlist) {
            session.saveOrUpdate(d);
        }
        ta.commit();
    }

    public synchronized MusicianEntityC update(MusicianEntityC musician) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        MusicianEntityC m = (MusicianEntityC) session.merge(musician);
        ta.commit();
        return m;
    }

    @Override
    public synchronized void delete(MusicianEntityC musician) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        session.delete(musician);
        ta.commit();
    }
}
