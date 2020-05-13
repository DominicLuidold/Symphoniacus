package at.fhv.teamb.symphoniacus.persistence;

import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
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
     * Finds the object based on the provided primary key.
     *
     * @param clazz The class to use
     * @param key   The primary key to use
     * @return The object
     */
    protected Optional<T> find(Class<T> clazz, Integer key) {
        // Disable cache because duty positions could be set.
        //entityManager.clear();
        T elem = entityManager.find(clazz, key);
        if (elem != null) {
            return Optional.of(elem);
        }
        return Optional.empty();
    }

    /**
     * Persists an object.
     *
     * @param clazz Class of the object
     * @param elem  The object to persist
     * @return Optional.empty if persisting not possible
     */
    protected Optional<T> persist(Class<T> clazz, T elem) {
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(elem);
            transaction.commit();
            return Optional.of(elem);
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
                LOG.error("Could not persist element", e);
            }
        }
        return Optional.empty();
    }

    /**
     * Updates an existing object.
     *
     * @param clazz Class of the object
     * @param elem  The object to update
     * @return Optional.empty if updating not possible
     */
    protected Optional<T> update(Class<T> clazz, T elem) {
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.merge(elem);
            transaction.commit();
            return Optional.of(elem);
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
                LOG.error("Could not update element", e);
            }
        }
        return Optional.empty();
    }
}
