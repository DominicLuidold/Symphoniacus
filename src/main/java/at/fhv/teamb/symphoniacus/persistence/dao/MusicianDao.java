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
 * DAO for Musicians.
 *
 * @author : Valentin Goronjic
 */
public class MusicianDao extends BaseDao<User> {
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
     * returns all musicians rolls of the musician.
     * from the user table matches the userId from the musician table.
     *
     * @param musician The musician for which the roles should be loaded
     **/
    public List<MusicianRole> getAllMusicianRoles(Musician musician) {
        createEntityManager();
        TypedQuery<MusicianRole> query = this.entityManager.createQuery(
            "SELECT mr FROM Musician m "
                + "INNER JOIN m.musicianRoles mr "
                + "WHERE m.musicianId = :mId",
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
     *
     * @param musician The musician for which the sections should be loaded
     **/
    public List<Section> getSectionsAccessibleToUser(Musician musician) {
        createEntityManager();
        TypedQuery<Section> query = this.entityManager.createQuery(
            "SELECT s FROM Section s WHERE s.sectionId = :secId",
            Section.class
        );
        query.setMaxResults(300);
        query.setParameter("secId", musician.getSectionId());
        List<Section> resultList = query.getResultList();
        tearDown();
        return resultList;
    }
}
