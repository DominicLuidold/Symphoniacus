package at.fhv.orchestraria.persistence.dao;

import at.fhv.orchestraria.domain.model.MusicianRoleEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Team C
 */

public class MusicianRoleDAO implements DaoBase<MusicianRoleEntity> {
    private SessionManager _sessionManager;

    protected MusicianRoleDAO(SessionManager sessionFactory) {
        _sessionManager = sessionFactory;
    }

    @Override
    public synchronized Optional<MusicianRoleEntity> get(int id) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        Optional <MusicianRoleEntity> mre = Optional.ofNullable(session.get(MusicianRoleEntity.class , id));
        ta.commit();
        return mre;
    }

    @Override
    public synchronized List<MusicianRoleEntity> getAll() {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        List <MusicianRoleEntity> mres = session.createQuery("SELECT a FROM MusicianRoleEntity a", MusicianRoleEntity.class).getResultList();
        List<MusicianRoleEntity> wrappedMusicianRoles = new ArrayList<>(mres.size());
        for(MusicianRoleEntity mre : mres){
            wrappedMusicianRoles.add(mre);
        }
        ta.commit();
        return wrappedMusicianRoles;
    }

    @Override
    public synchronized void save(MusicianRoleEntity musicianRoleEntity) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        session.saveOrUpdate(musicianRoleEntity);
        ta.commit();
    }

    @Override
    public synchronized MusicianRoleEntity update(MusicianRoleEntity musicianRoleEntity) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        MusicianRoleEntity ipe = (MusicianRoleEntity) session.merge(musicianRoleEntity);
        ta.commit();
        return ipe;
    }

    @Override
    public synchronized void delete(MusicianRoleEntity musicianRoleEntity) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        session.delete(musicianRoleEntity);
        ta.commit();
    }
}
