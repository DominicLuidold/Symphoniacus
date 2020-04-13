package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.Musician;
import at.fhv.teamb.symphoniacus.persistence.model.MusicianRole;
import at.fhv.teamb.symphoniacus.persistence.model.Section;
import at.fhv.teamb.symphoniacus.persistence.model.User;
import java.util.List;
import java.util.Optional;
import javax.persistence.TypedQuery;

/**
 * DAO for Musicians.
 *
 * @author Valentin Goronjic
 */
public class MusicianDao extends BaseDao<User> {

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
     * Returns a List of all {@link MusicianRole}s assigned to the musician.
     *
     * @param musician The musician to use
     * @return A List of roles
     */
    public List<MusicianRole> getAllMusicianRoles(Musician musician) {
        this.createEntityManager();
        TypedQuery<MusicianRole> query = this.entityManager.createQuery(
            "SELECT mr FROM Musician m "
                + "INNER JOIN m.musicianRoles mr "
                + "WHERE m.musicianId = :mId",
            MusicianRole.class
        );
        query.setParameter("mId", musician.getMusicianId());
        List<MusicianRole> resultList = query.getResultList();
        this.tearDown();

        return resultList;
    }

    /**
     * Returns a List of all {@link Section}s accessible to the musician.
     *
     * @param musician The musician to use
     * @return A list of Sections
     */
    public List<Section> getSectionsAccessibleToUser(Musician musician) {
        this.createEntityManager();
        TypedQuery<Section> query = this.entityManager.createQuery(
            "SELECT s FROM Section s WHERE s.sectionId = :secId",
            Section.class
        );
        query.setParameter("secId", musician.getSectionId());
        List<Section> resultList = query.getResultList();
        this.tearDown();

        return resultList;
    }
}
