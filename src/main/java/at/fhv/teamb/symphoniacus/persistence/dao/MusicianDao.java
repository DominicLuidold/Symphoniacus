package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.MusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.SectionEntity;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityGraph;
import javax.persistence.TypedQuery;

/**
 * DAO for Musicians.
 *
 * @author Valentin Goronjic
 * @author Theresa Gierer
 * @author Dominic Luidold
 */
public class MusicianDao extends BaseDao<MusicianEntity> {

    @Override
    public Optional<MusicianEntity> find(Object key) {
        this.createEntityManager();
        EntityGraph graph = this.entityManager.getEntityGraph(
            "musician-with-collections"
        );

        MusicianEntity m = entityManager.createQuery(
            "select m from MusicianEntity m where m.user = :user",
            MusicianEntity.class
        )
            .setParameter("user", key)
            .setHint("javax.persistence.fetchgraph", graph)
            .getSingleResult();
        m.getMusicianRoles();
        m.getSection();
        this.tearDown();
        return Optional.of(m);
    }

    /**
     * Finds all {@link MusicianEntity} objects based on provided {@link SectionEntity}.
     *
     * @param section The section to use
     * @return A List of musicians belonging to the section
     */
    public List<MusicianEntity> findAllWithSection(SectionEntity section) {
        this.createEntityManager();
        TypedQuery<MusicianEntity> query = this.entityManager.createQuery(
            "SELECT m FROM MusicianEntity m "
                + "LEFT JOIN FETCH m.dutyPositions "
                + "WHERE m.section = :section",
            MusicianEntity.class
        );
        query.setParameter("section", section);
        List<MusicianEntity> result = query.getResultList();
        this.tearDown();

        return result;
    }

    @Override
    public Optional<MusicianEntity> persist(MusicianEntity elem) {
        return Optional.empty();
    }

    @Override
    public Optional<MusicianEntity> update(MusicianEntity elem) {
        return Optional.empty();
    }

    @Override
    public Boolean remove(MusicianEntity elem) {
        return null;
    }
}
