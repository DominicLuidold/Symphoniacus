package at.fhv.teamb.symphoniacus.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class acts as a base for all DAOs by handling the {@link EntityManagerFactory} and
 * {@link EntityManager} and therefore managing the database connections.
 *
 * @param <T> The type of DAO, based on JPA entities
 */
public abstract class BaseDao<T> implements Dao<T> {
    private static final Logger LOG = LogManager.getLogger(BaseDao.class);
    protected static EntityManagerFactory entityManagerFactory =
        Persistence.createEntityManagerFactory(
            "mysqldb"
        );
    protected static EntityManager entityManager = entityManagerFactory.createEntityManager();

    public BaseDao() {
        LOG.debug("Creating a new DAO and EntityManager");
    }

    /**
     * Creates a new {@link EntityManager} that can be used to create new connections.
     */
    public void createEntityManager() {
        LOG.debug("createEntityManager is currently NOP");
        //this.entityManagerFactory = Persistence.createEntityManagerFactory(
        //    "mysqldb"
        //);
        //this.entityManager = entityManagerFactory.createEntityManager();
    }

    /**
     * Tears down the {@link EntityManagerFactory}, thus closing all open connections.
     */
    public void tearDown() {
        LOG.debug("createEntityManager is currently NOP");
        // this.entityManager.close();
    }
}
