package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.AdministrativeAssistant;
import at.fhv.teamb.symphoniacus.persistence.model.Musician;
import at.fhv.teamb.symphoniacus.persistence.model.MusicianRole;
import at.fhv.teamb.symphoniacus.persistence.model.Section;
import at.fhv.teamb.symphoniacus.persistence.model.User;
import java.util.List;
import java.util.Optional;
import javax.persistence.TypedQuery;


/**
 * DAO for Users.
 *
 * @author : Danijel Antonijevic
 * @author : Valentin Goronjic
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
        return null;
    }

    /**
     * returns a user if the shortcut and password matches an entry in the database.
     *
     * @author : Danijel Antonijevic
     **/
    public User login(String userShortCut, String password) {
        createEntityManager();
        TypedQuery<User> query = this.entityManager.createQuery(
            "SELECT u FROM User u WHERE u.shortcut = :shortc AND u.password = :pwd",
            User.class
        );
        query.setMaxResults(300);
        query.setParameter("shortc", userShortCut);
        query.setParameter("pwd", password);
        User result = query.getSingleResult();
        tearDown();
        return result;
    }

    /**
     * returns the linked musician if the userid
     * from the user table matches the userId from the musician table.
     *
     * @param currentUser A musician when currentUser is a Musician
     * @created : 11.04.20, Sa.
     **/
    public Musician getUserIsMusician(User currentUser) {
        createEntityManager();
        TypedQuery<Musician> query = this.entityManager.createQuery(
            "SELECT m FROM Musician m WHERE m.userId = :uId",
            Musician.class
        );
        query.setMaxResults(300);
        query.setParameter("uId", currentUser.getUserId());
        Musician result = query.getSingleResult();
        tearDown();
        return result;
    }

}
