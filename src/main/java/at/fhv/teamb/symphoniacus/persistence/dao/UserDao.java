package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.AdministrativeAssistantEntity;
import at.fhv.teamb.symphoniacus.persistence.model.MusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IUserEntity;
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
public class UserDao extends BaseDao<IUserEntity> {
    private static final Logger LOG = LogManager.getLogger(UserDao.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IUserEntity> find(Integer key) {
        return this.find(IUserEntity.class, key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IUserEntity> persist(IUserEntity elem) {
        return this.persist(IUserEntity.class, elem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IUserEntity> update(IUserEntity elem) {
        return this.update(IUserEntity.class, elem);
    }

    @Override
    public boolean remove(IUserEntity elem) {
        return false;
    }

    /**
     * Returns a {@link IUserEntity} if provided shortcut and password match a database entry.
     *
     * @param userShortCut The shortcut to identify the user
     * @return A user matching provided credentials
     */
    public Optional<IUserEntity> loadUser(String userShortCut) {
        List<IUserEntity> result = null;
        TypedQuery<IUserEntity> query = entityManager.createQuery(
            "SELECT u FROM IUserEntity u WHERE u.shortcut = :shortc",
            IUserEntity.class
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
     * Checks whether the given userShortcut + password hash are valid.
     * @param userShortCut current user's shortcut
     * @param inputPasswordHash current user's password hash
     * @return true if credentials are correct
     */
    public boolean isLoginCorrect(String userShortCut, String inputPasswordHash) {
        TypedQuery<Long> query = entityManager.createQuery(
            "SELECT COUNT(u) FROM IUserEntity u "
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
     * Checks whether the provided {@link IUserEntity} is a {@link MusicianEntity}.
     *
     * @param currentUser The user to check
     * @return True if user is a musician, false otherwise
     */
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
     * Checks whether the provided {@link IUserEntity} is a {@link AdministrativeAssistantEntity}.
     *
     * @param currentUser The user to check
     * @return True if user is a AdministrativeAssistant, false otherwise
     */
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
}
