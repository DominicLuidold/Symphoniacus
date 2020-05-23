package at.fhv.orchestraria.persistence.dao;

import at.fhv.orchestraria.domain.model.MusicianRoleEntityC;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Team C
 */

public class MusicianRoleDAO implements DaoBase<MusicianRoleEntityC> {
    private SessionManager _sessionManager;

    protected MusicianRoleDAO(SessionManager sessionFactory) {
        _sessionManager = sessionFactory;
    }

    @Override
    public synchronized Optional<MusicianRoleEntityC> get(int id) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        Optional <MusicianRoleEntityC> mre = Optional.ofNullable(session.get(MusicianRoleEntityC.class , id));
        ta.commit();
        return mre;
    }

    @Override
    public synchronized List<MusicianRoleEntityC> getAll() {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        List <MusicianRoleEntityC> mres = session.createQuery("SELECT a FROM MusicianRoleEntity a", MusicianRoleEntityC.class).getResultList();
        List<MusicianRoleEntityC> wrappedMusicianRoles = new ArrayList<>(mres.size());
        for(MusicianRoleEntityC mre : mres){
            wrappedMusicianRoles.add(mre);
        }
        ta.commit();
        return wrappedMusicianRoles;
    }

    @Override
    public synchronized void save(MusicianRoleEntityC musicianRoleEntity) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        session.saveOrUpdate(musicianRoleEntity);
        ta.commit();
    }

    @Override
    public synchronized MusicianRoleEntityC update(MusicianRoleEntityC musicianRoleEntity) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        MusicianRoleEntityC ipe = (MusicianRoleEntityC) session.merge(musicianRoleEntity);
        ta.commit();
        return ipe;
    }

    @Override
    public synchronized void delete(MusicianRoleEntityC musicianRoleEntity) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        session.delete(musicianRoleEntity);
        ta.commit();
    }
}
