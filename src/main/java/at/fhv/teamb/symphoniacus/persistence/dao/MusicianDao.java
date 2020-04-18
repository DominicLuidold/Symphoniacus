package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.MusicianEntity;
import java.util.Optional;
import javax.persistence.EntityGraph;

/**
 * DAO for Musicians.
 *
 * @author Valentin Goronjic
 */
public class MusicianDao extends BaseDao<MusicianEntity> {

    @Override
    public Optional<MusicianEntity> find(Object key) {
        this.createEntityManager();
        EntityGraph graph = this.entityManager.getEntityGraph(
            "musician-with-collections"
        );

        MusicianEntity m = entityManager.createQuery(
            "select m from MusicianEntity m where m.userId = :id",
            MusicianEntity.class
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
