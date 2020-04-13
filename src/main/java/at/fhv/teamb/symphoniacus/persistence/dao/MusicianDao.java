package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.Musician;
import at.fhv.teamb.symphoniacus.persistence.model.MusicianRole;
import at.fhv.teamb.symphoniacus.persistence.model.Section;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityGraph;
import javax.persistence.TypedQuery;

/**
 * DAO for Musicians.
 *
 * @author Valentin Goronjic
 */
public class MusicianDao extends BaseDao<Musician> {

    @Override
    public Optional<Musician> find(Object key) {
        this.createEntityManager();
        EntityGraph graph = this.entityManager.getEntityGraph(
            "musician-with-collections"
        );

        Musician m = entityManager.createQuery(
            "select m from Musician m where m.userId = :id",
            Musician.class
        )
            .setParameter("id", key)
            .setHint("javax.persistence.fetchgraph", graph)
            .getSingleResult();
        m.getMusicianRoles();
        m.getSection();
        this.tearDown();
        return Optional.of(m);
    }

    @Override
    public Optional<Musician> persist(Musician elem) {
        return Optional.empty();
    }

    @Override
    public Optional<Musician> update(Musician elem) {
        return Optional.empty();
    }

    @Override
    public Boolean remove(Musician elem) {
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
        query.setMaxResults(300);
        query.setParameter("secId", musician.getSection().getSectionId());
        List<Section> resultList = query.getResultList();
        this.tearDown();

        return resultList;
    }
}
