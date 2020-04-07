package at.fhv.teamb.symphoniacus.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public abstract class BaseDAO<T> implements DAO<T> {
    protected EntityManagerFactory _entityManagerFactory;
    protected EntityManager _entityManager;

    public BaseDAO() {
        _entityManagerFactory = Persistence.createEntityManagerFactory("mysqldb");
    }

    public void createEntityManager(){
        _entityManager = _entityManagerFactory.createEntityManager();
    }

    public void tearDown() {
        _entityManagerFactory.close();
    }
}
