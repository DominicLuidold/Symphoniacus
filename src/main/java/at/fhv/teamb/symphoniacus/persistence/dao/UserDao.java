package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.AdministrativeAssistant;
import at.fhv.teamb.symphoniacus.persistence.model.Musician;
import at.fhv.teamb.symphoniacus.persistence.model.User;
import java.util.Optional;
import javax.persistence.TypedQuery;

/**
 * DAO for Users.
 *
 * @author Danijel Antonijevic
 * @author Valentin Goronjic
 */
public class UserDao extends BaseDao<User> {
    public AdministrativeAssistant getUserIsAdministrativeAssistant;

    @Override
    public Optional<User> find(Object key) {
        return Optional.empty();
    }

    @Override
    public Optional<User> persist(User elem) {
        return Optional.empty();
    }

    @Override
    public Optional<User> update(User elem) {
        return Optional.empty();
    }

    @Override
    public Boolean remove(User elem) {
        return false;
    }

    /**
     * Returns a {@link User} if provided shortcut and password match a database entry.
     *
     * @param userShortCut The shortcut to identify the user
     * @param password     The password to authenticate the user
     * @return A User matching provided credentials
     */
    public Optional<User> login(String userShortCut, String password) {
        this.createEntityManager();
        TypedQuery<User> query = this.entityManager.createQuery(
            "SELECT u FROM User u WHERE u.shortcut = :shortc AND u.password = :pwd",
            User.class
        );
        query.setParameter("shortc", userShortCut);
        query.setParameter("pwd", password);
        User result = query.getSingleResult();
        this.tearDown();

        return Optional.of(result);
    }

    public boolean isUserMusician(User currentUser) {
        this.createEntityManager();
        TypedQuery<Long> query = this.entityManager.createQuery(
            "SELECT COUNT(m) FROM Musician m WHERE m.userId = :uId",
            Long.class
        );
        query.setParameter("uId", currentUser.getUserId());
        Long result = query.getSingleResult();
        this.tearDown();

        if (result == 1) {
            return true;
        } else {
            return false;
        }
    }
}
