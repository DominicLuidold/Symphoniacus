package at.fhv.teamb.symphoniacus.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public abstract class BaseDAO<T> implements DAO<T> {
    protected EntityManagerFactory entityManagerFactory;
    protected EntityManager entityManager;

    public BaseDAO() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("mysqldb");
    }

    public void createEntityManager(){
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    public void tearDown() {
        this.entityManagerFactory.close();
    }
}
