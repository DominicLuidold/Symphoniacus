
package at.fhv.orchestraria.persistence.dao;


import at.fhv.orchestraria.domain.model.UserEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Team C
 */

public class UserDAO implements DaoBase<UserEntity> {

    private SessionManager _sessionManager;

    protected UserDAO(SessionManager sessionFactory) {
        _sessionManager = sessionFactory;
    }

    @Override
    public synchronized Optional<UserEntity> get(int id) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        Optional<UserEntity> ue =  Optional.ofNullable(session.get(UserEntity.class , id));
        ta.commit();
        return ue;
    }

    @Override
    public synchronized List<UserEntity> getAll() {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        List<UserEntity> user = session.createQuery("SELECT a FROM UserEntity a", UserEntity.class).getResultList();
        List<UserEntity> wrappedusers = new ArrayList<>(user.size());
        for(UserEntity d :user){
            wrappedusers.add(d);
        }
        ta.commit();
        return wrappedusers;
    }


    @Override
    public synchronized void save(UserEntity entity) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        session.saveOrUpdate(entity);
        ta.commit();
    }

    @Override
    public synchronized UserEntity update(UserEntity entity) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        UserEntity de = (UserEntity) session.merge(entity);
        ta.commit();
        return de;
    }

    @Override
    public synchronized void delete(UserEntity entity) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        session.delete(entity);
        ta.commit();
    }

    /**
     *
     * @param user is a list of unsers;
     * saves a list of users with only one transaction;
     */
    public synchronized void savelist(List<UserEntity> user){
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        for (UserEntity d:user) {
            session.saveOrUpdate(d);
        }
        ta.commit();
    }

    public synchronized Optional<UserEntity> getByShortcut(String shorty) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        Query userquery = session.createQuery("SELECT a FROM UserEntity a where shortcut = :shorty ", UserEntity.class);
        userquery.setParameter("shorty", shorty);
        Optional<UserEntity> user = Optional.empty();
        try{
            user= Optional.ofNullable((UserEntity)userquery.getSingleResult());
        } catch(NoResultException e){
            return user;
        }
        ta.commit();
        return user;
    }


    /**
     *
     * @param shorty is the first name of a user
     * @return list of all users with the first name
     */
    public synchronized List<UserEntity> getFname(String shorty) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        Query userquery = session.createQuery("SELECT a FROM UserEntity a where firstName = :shorty ", UserEntity.class);
        userquery.setParameter("shorty", shorty);
        List<UserEntity> user=userquery.getResultList();
        List<UserEntity> wrappedusers = new ArrayList<>(user.size());
        for(UserEntity d :user){
            wrappedusers.add(d);
        }
        ta.commit();
        return wrappedusers;
    }

    /**
     *
     * @param shorty is the secondname
     * @return list of users with the second name
     */
    public synchronized List<UserEntity> getSname(String shorty) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        Query userquery = session.createQuery("SELECT a FROM UserEntity a where lastName = :shorty ", UserEntity.class);
        userquery.setParameter("shorty", shorty);
        List<UserEntity> user=userquery.getResultList();
        List<UserEntity> wrappedusers = new ArrayList<>(user.size());
        for(UserEntity d :user){
            wrappedusers.add(d);
        }
        ta.commit();
        return wrappedusers;
    }
}