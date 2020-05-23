package at.fhv.orchestraria.persistence.dao;

import at.fhv.orchestraria.domain.model.MusicianRoleMusicianEntityC;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Team C
 */

public class MusicianRoleMusicianDAO implements DaoBase<MusicianRoleMusicianEntityC> {
    private SessionManager _sessionManager;

    protected MusicianRoleMusicianDAO(SessionManager sessionFactory) {
        _sessionManager = sessionFactory;
    }

    @Override
    public synchronized Optional<MusicianRoleMusicianEntityC> get(int id) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        Optional<MusicianRoleMusicianEntityC> mrme = Optional.ofNullable(session.get(MusicianRoleMusicianEntityC.class , id));
        ta.commit();
        return mrme;
    }

    @Override
    public synchronized List<MusicianRoleMusicianEntityC> getAll() {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        List <MusicianRoleMusicianEntityC> mrmes = session.createQuery("SELECT a FROM MusicianRoleMusicianEntity a", MusicianRoleMusicianEntityC.class).getResultList();
        List<MusicianRoleMusicianEntityC> wrappedMRME = new ArrayList<>(mrmes.size());
        for(MusicianRoleMusicianEntityC mrme: mrmes){
            wrappedMRME.add(mrme);
        }
        ta.commit();
        return wrappedMRME;
    }

    @Override
    public synchronized void save(MusicianRoleMusicianEntityC musicianRoleMusicianEntity) {
        Session session = _sessionManager.openConnection();
        Transaction ta= session.beginTransaction();
        session.saveOrUpdate(musicianRoleMusicianEntity);
        ta.commit();
    }

    @Override
    public synchronized MusicianRoleMusicianEntityC update(MusicianRoleMusicianEntityC musicianRoleMusicianEntity) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        MusicianRoleMusicianEntityC ipe = (MusicianRoleMusicianEntityC) session.merge(musicianRoleMusicianEntity);
        ta.commit();
        return ipe;
    }

    @Override
    public synchronized void delete(MusicianRoleMusicianEntityC musicianRoleMusicianEntity) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        session.delete(musicianRoleMusicianEntity);
        ta.commit();
    }
}
