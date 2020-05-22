package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.dao.interfaces.IUserDao;
import at.fhv.teamb.symphoniacus.persistence.model.UserEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IUserEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.TypedQuery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * DAO for Users.
 *
 * @author Danijel Antonijevic
 * @author Valentin Goronjic
 */
public class UserDao extends BaseDao<IUserEntity>
    implements IUserDao {
    private static final Logger LOG = LogManager.getLogger(UserDao.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IUserEntity> find(Integer key) {
        return this.find(UserEntity.class, key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IUserEntity> persist(IUserEntity elem) {
        return this.persist(UserEntity.class, elem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IUserEntity> update(IUserEntity elem) {
        return this.update(UserEntity.class, elem);
    }

    @Override
    public boolean remove(IUserEntity elem) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IUserEntity> loadUser(String userShortCut) {
        List<UserEntity> result = null;
        TypedQuery<UserEntity> query = entityManager.createQuery(
            "SELECT u FROM UserEntity u WHERE u.shortcut = :shortc",
            UserEntity.class
        );
        query.setParameter("shortc", userShortCut);

        result = query.getResultList();
        if (!result.isEmpty()) {
            return Optional.of(result.get(0));
        }

        LOG.debug("No results for query loadUser found");
        return Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isLoginCorrect(String userShortCut, String inputPasswordHash) {
        TypedQuery<Long> query = entityManager.createQuery(
            "SELECT COUNT(u) FROM UserEntity u "
                + "WHERE u.shortcut = :userShortCut "
                + "AND u.password = :inputPasswordHash",
            Long.class
        );
        query.setParameter("userShortCut", userShortCut);
        query.setParameter("inputPasswordHash", inputPasswordHash);

        Long result = query.getSingleResult();
        if (result == 1) {
            return true;
        }

        LOG.debug("Credentials incorrect");
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isUserMusician(IUserEntity currentUser) {
        Optional<Long> result = Optional.empty();
        TypedQuery<Long> query = entityManager.createQuery(
            "SELECT COUNT(m) FROM MusicianEntity m WHERE m.user = :user",
            Long.class
        );
        query.setParameter("user", currentUser);

        try {
            result = Optional.of(query.getSingleResult());
        } catch (Exception e) {
            LOG.error(e);
        }

        if (result.isPresent()) {
            return result.get() == 1;
        }
        LOG.debug("No results for query isUserMusician found");
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isUserAdministrativeAssistant(IUserEntity currentUser) {
        Optional<Long> result = Optional.empty();
        TypedQuery<Long> query = entityManager.createQuery(
            "SELECT COUNT(aae) FROM AdministrativeAssistantEntity aae WHERE aae.user = :user",
            Long.class
        );
        query.setParameter("user", currentUser);

        try {
            result = Optional.of(query.getSingleResult());
        } catch (Exception e) {
            LOG.error(e);
        }

        if (result.isPresent()) {
            return result.get() == 1;
        }
        LOG.debug("No results for query isUserAdministrativeAssistant found");
        return false;
    }

    @Override
    public synchronized List<IUserEntity> getAll() {
        this.entityManager.getTransaction().begin();
        TypedQuery<UserEntity> query = this.entityManager.createQuery("SELECT a FROM UserEntity a",
                UserEntity.class).setMaxResults(1000);

        List<UserEntity> result = query.getResultList();
        List<IUserEntity> wrappedusers =
            new ArrayList<>(result.size());

        for (UserEntity d : result) {
            wrappedusers.add(d);
        }
        this.entityManager.getTransaction().commit();
        return wrappedusers;
    }

}
