package at.fhv.orchestraria.persistence.dao;

import at.fhv.orchestraria.domain.model.MusicianRoleMusicianEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Team C
 */

public class MusicianRoleMusicianDAO implements DaoBase<MusicianRoleMusicianEntity> {
    private SessionManager _sessionManager;

    protected MusicianRoleMusicianDAO(SessionManager sessionFactory) {
        _sessionManager = sessionFactory;
    }

    @Override
    public synchronized Optional<MusicianRoleMusicianEntity> get(int id) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        Optional<MusicianRoleMusicianEntity> mrme = Optional.ofNullable(session.get(MusicianRoleMusicianEntity.class , id));
        ta.commit();
        return mrme;
    }

    @Override
    public synchronized List<MusicianRoleMusicianEntity> getAll() {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        List <MusicianRoleMusicianEntity> mrmes = session.createQuery("SELECT a FROM MusicianRoleMusicianEntity a", MusicianRoleMusicianEntity.class).getResultList();
        List<MusicianRoleMusicianEntity> wrappedMRME = new ArrayList<>(mrmes.size());
        for(MusicianRoleMusicianEntity mrme: mrmes){
            wrappedMRME.add(mrme);
        }
        ta.commit();
        return wrappedMRME;
    }

    @Override
    public synchronized void save(MusicianRoleMusicianEntity musicianRoleMusicianEntity) {
        Session session = _sessionManager.openConnection();
        Transaction ta= session.beginTransaction();
        session.saveOrUpdate(musicianRoleMusicianEntity);
        ta.commit();
    }

    @Override
    public synchronized MusicianRoleMusicianEntity update(MusicianRoleMusicianEntity musicianRoleMusicianEntity) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        MusicianRoleMusicianEntity ipe = (MusicianRoleMusicianEntity) session.merge(musicianRoleMusicianEntity);
        ta.commit();
        return ipe;
    }

    @Override
    public synchronized void delete(MusicianRoleMusicianEntity musicianRoleMusicianEntity) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        session.delete(musicianRoleMusicianEntity);
        ta.commit();
    }
}
