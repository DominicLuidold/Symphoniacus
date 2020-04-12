package at.fhv.teamb.symphoniacus.persistence;

import at.fhv.teamb.symphoniacus.persistence.model.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public abstract class BaseDao<T> implements Dao<T> {
    protected EntityManagerFactory entityManagerFactory;
    protected EntityManager entityManager;

    public BaseDao() {
    }

    public void createEntityManager() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("mysqldb");
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    public void tearDown() {
        this.entityManagerFactory.close();
    }

}
