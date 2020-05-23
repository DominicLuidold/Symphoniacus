package at.fhv.orchestraria.persistence.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class SessionManager {
    private static Session _session;
    private SessionFactory _factory;

    protected SessionManager(SessionFactory sessionFactory){
        _factory = sessionFactory;
    }

    public Session openConnection(){
        if(_session == null){
            _session = _factory.openSession();
            System.out.println("Session opened");
            return _session;
        }else{
            if(_session.getTransaction().isActive()){
                _session.getTransaction().commit();
            }
            return _session;
        }
    }

    public Session newSession(){
        Session session = _factory.openSession();
        return session;
    }

    public void closeSession(){
        System.out.println("Session closed");
        _session.close();
        _session = null;
    }
}
