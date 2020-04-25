package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.MusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.NegativeDutyWishEntity;
import at.fhv.teamb.symphoniacus.persistence.model.UserEntity;
import java.util.Optional;
import javax.persistence.TypedQuery;

/**
 * DAO for Users.
 *
 * @author Danijel Antonijevic
 * @author Valentin Goronjic
 */
public class UserDao extends BaseDao<UserEntity> {

    /**
     * Finds a duty by its key.
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
        return false;
    }

    /**
     * Returns a {@link UserEntity} if provided shortcut and password match a database entry.
     *
     * @param userShortCut The shortcut to identify the user
     * @param password     The password to authenticate the user
     * @return A user matching provided credentials
     */
    public Optional<UserEntity> login(String userShortCut, String password) {
        this.createEntityManager();
        TypedQuery<UserEntity> query = this.entityManager.createQuery(
            "SELECT u FROM UserEntity u WHERE u.shortcut = :shortc AND u.password = :pwd",
            UserEntity.class
        );
        query.setParameter("shortc", userShortCut);
        query.setParameter("pwd", password);
        UserEntity result = query.getSingleResult();
        this.tearDown();

        return Optional.of(result);
    }

    /**
     * Checks whether the provided {@link UserEntity} is a {@link MusicianEntity}.
     *
     * @param currentUser The user to check
     * @return True if user is a musician, false otherwise
     */
    public boolean isUserMusician(UserEntity currentUser) {
        this.createEntityManager();
        TypedQuery<Long> query = this.entityManager.createQuery(
            "SELECT COUNT(m) FROM MusicianEntity m WHERE m.user = :user",
            Long.class
        );
        query.setParameter("user", currentUser);
        Long result = query.getSingleResult();
        this.tearDown();

        return result == 1;
    }
}
