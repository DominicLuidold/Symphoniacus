package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.domain.Duty;
import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.AdministrativeAssistantEntity;
import at.fhv.teamb.symphoniacus.persistence.model.MusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.UserEntity;
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
public class UserDao extends BaseDao<UserEntity> {

    private static final Logger LOG = LogManager.getLogger(UserDao.class);

    /**
     * Finds a {@link UserEntity} by its key.
     *
     * @param key The key of the duty
     * @return The duty that is looked for
     */
    @Override
    public Optional<UserEntity> find(Integer key) {
        return this.find(UserEntity.class, key);
    }

    @Override
    public Optional<UserEntity> persist(UserEntity elem) {
        return Optional.empty();
    }

    @Override
    public Optional<UserEntity> update(UserEntity elem) {
        return Optional.empty();
    }

    @Override
    public Boolean remove(UserEntity elem) {
        return Boolean.FALSE;
    }

    /**
     * Returns a {@link UserEntity} if provided shortcut and password match a database entry.
     *
     * @param userShortCut The shortcut to identify the user
     * @param password     The password to authenticate the user
     * @return A user matching provided credentials
     */
    public Optional<UserEntity> login(String userShortCut, String password) {
        List<UserEntity> result = null;
        TypedQuery<UserEntity> query = entityManager.createQuery(
            "SELECT u FROM UserEntity u WHERE u.shortcut = :shortc AND u.password = :pwd",
            UserEntity.class
        );
        query.setParameter("shortc", userShortCut);
        query.setParameter("pwd", password);

        result = query.getResultList();
        if (!result.isEmpty()) {
            return Optional.of(result.get(0));
        }

        LOG.debug("No results for query login found");
        return Optional.empty();
    }

    /**
     * Checks whether the provided {@link UserEntity} is a {@link MusicianEntity}.
     *
     * @param currentUser The user to check
     * @return True if user is a musician, false otherwise
     */
    public boolean isUserMusician(UserEntity currentUser) {
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
     * Checks whether the provided {@link UserEntity} is a {@link AdministrativeAssistantEntity}.
     *
     * @param currentUser The user to check
     * @return True if user is a AdministrativeAssistant, false otherwise
     */
    public boolean isUserAdministrativeAssistant(UserEntity currentUser) {
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
