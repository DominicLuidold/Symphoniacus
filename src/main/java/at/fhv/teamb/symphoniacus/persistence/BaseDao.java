package at.fhv.teamb.symphoniacus.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * This class acts as a base for all DAOs by handling the {@link EntityManagerFactory} and
 * {@link EntityManager} and therefore managing the database connections.
 *
 * @param <T> The type of DAO, based on JPA entities
 */
public abstract class BaseDao<T> implements Dao<T> {
    protected EntityManagerFactory entityManagerFactory;
    protected EntityManager entityManager;

    /**
     * Creates a new {@link EntityManager} that can be used to create new connections.
     */
    public void createEntityManager() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("mysqldb");
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    /**
     * Tears down the {@link EntityManagerFactory}, thus closing all open connections.
     */
    public void tearDown() {
        this.entityManagerFactory.close();
    }
}
