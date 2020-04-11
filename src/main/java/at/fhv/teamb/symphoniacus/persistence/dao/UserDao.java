package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.AdministrativeAssistant;
//
import at.fhv.teamb.symphoniacus.persistence.model.Musician;
import at.fhv.teamb.symphoniacus.persistence.model.MusicianRole;
import at.fhv.teamb.symphoniacus.persistence.model.Section;
import at.fhv.teamb.symphoniacus.persistence.model.User;
//
import java.util.List;
import java.util.Optional;
import javax.persistence.TypedQuery;


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
     * @author : Danijel Antonijevic
     * @created : 11.04.20, Sa.
     **/
    public User login(String userShortCut, String password) {
        createEntityManager();
        TypedQuery<User> query = this.entityManager.createQuery(
            "SELECT u FROM User u WHERE shortcut LIKE shortc AND password LIKE pwd",
            User.class
        );
        query.setMaxResults(300);
        query.setParameter("shortc", userShortCut);
        query.setParameter("pwd", password);
        List<User> resultList = query.getResultList();
        tearDown();
        return resultList.get(0);

    }

    /**
     * returns the linked musician if the userid
     * from the user table matches the userId from the musician table.
     * @author : Danijel Antonijevic
     * @created : 11.04.20, Sa.
     **/
    public Musician getUserIsMusician(User currentUser) {
        createEntityManager();
        TypedQuery<Musician> query = this.entityManager.createQuery(
            "SELECT m FROM Musician m WHERE userId LIKE uid",
            Musician.class
        );
        query.setMaxResults(300);
        query.setParameter("uid", currentUser.getUserId());
        List<Musician> resultList = query.getResultList();
        tearDown();
        return resultList.get(0);
    }

    /**
     * returns all musicians rolls of the musician.
     * from the user table matches the userId from the musician table.
     * @author : Danijel Antonijevic
     * @created : 11.04.20, Sa.
     **/
    public List<MusicianRole> getAllMusicianRoles(Musician musician) {
        createEntityManager();
        TypedQuery<MusicianRole> query = this.entityManager.createQuery(
            "SELECT MusicianRole.musicianRoleId, MusicianRole.description FROM Musician "
                + "JOIN musicianRole_musician " +
                "ON Musician.musicianId = musicianRole_musician.musicianId"
                + "JOIN musicianRole " +
                "ON musicianRole_musician.musicianRoleId = musicianRole.musicianRoleId",
            MusicianRole.class
        );
        query.setMaxResults(300);
        query.setParameter("mId", musician.getMusicianId());
        List<MusicianRole> resultList = query.getResultList();
        tearDown();
        return resultList;
    }

    /**
     * searches in the section table for the name
     * of the musician and returns the corresponding section.
     * from the user table matches the userId from the musician table.
     * @author : Danijel Antonijevic
     * @created : 11.04.20, Sa.
     **/
    public List<Section> getSectionsAccessibleToUser(Musician musician) {
        createEntityManager();
        TypedQuery<Section> query = this.entityManager.createQuery(
            "SELECT s FROM Section s WHERE sectionId LIKE secId",
            Section.class
        );
        query.setMaxResults(300);
        query.setParameter("secId", musician.getSectionId());
        List<Section> resultList = query.getResultList();
        tearDown();
        return resultList;
    }
}
