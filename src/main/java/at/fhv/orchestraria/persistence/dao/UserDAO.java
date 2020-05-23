
package at.fhv.orchestraria.persistence.dao;


import at.fhv.orchestraria.domain.model.UserEntityC;
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

public class UserDAO implements DaoBase<UserEntityC> {

    private SessionManager _sessionManager;

    protected UserDAO(SessionManager sessionFactory) {
        _sessionManager = sessionFactory;
    }

    @Override
    public synchronized Optional<UserEntityC> get(int id) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        Optional<UserEntityC> ue =  Optional.ofNullable(session.get(UserEntityC.class , id));
        ta.commit();
        return ue;
    }

    @Override
    public synchronized List<UserEntityC> getAll() {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        List<UserEntityC> user = session.createQuery("SELECT a FROM UserEntity a", UserEntityC.class).getResultList();
        List<UserEntityC> wrappedusers = new ArrayList<>(user.size());
        for(UserEntityC d :user){
            wrappedusers.add(d);
        }
        ta.commit();
        return wrappedusers;
    }


    @Override
    public synchronized void save(UserEntityC entity) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        session.saveOrUpdate(entity);
        ta.commit();
    }

    @Override
    public synchronized UserEntityC update(UserEntityC entity) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        UserEntityC de = (UserEntityC) session.merge(entity);
        ta.commit();
        return de;
    }

    @Override
    public synchronized void delete(UserEntityC entity) {
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
    public synchronized void savelist(List<UserEntityC> user){
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        for (UserEntityC d:user) {
            session.saveOrUpdate(d);
        }
        ta.commit();
    }

    public synchronized Optional<UserEntityC> getByShortcut(String shorty) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        Query userquery = session.createQuery("SELECT a FROM UserEntity a where shortcut = :shorty ", UserEntityC.class);
        userquery.setParameter("shorty", shorty);
        Optional<UserEntityC> user = Optional.empty();
        try{
            user= Optional.ofNullable((UserEntityC)userquery.getSingleResult());
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
    public synchronized List<UserEntityC> getFname(String shorty) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        Query userquery = session.createQuery("SELECT a FROM UserEntity a where firstName = :shorty ", UserEntityC.class);
        userquery.setParameter("shorty", shorty);
        List<UserEntityC> user=userquery.getResultList();
        List<UserEntityC> wrappedusers = new ArrayList<>(user.size());
        for(UserEntityC d :user){
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
    public synchronized List<UserEntityC> getSname(String shorty) {
        Session session = _sessionManager.openConnection();
        Transaction ta = session.beginTransaction();
        Query userquery = session.createQuery("SELECT a FROM UserEntity a where lastName = :shorty ", UserEntityC.class);
        userquery.setParameter("shorty", shorty);
        List<UserEntityC> user=userquery.getResultList();
        List<UserEntityC> wrappedusers = new ArrayList<>(user.size());
        for(UserEntityC d :user){
            wrappedusers.add(d);
        }
        ta.commit();
        return wrappedusers;
    }
}