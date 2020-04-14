package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.Musician;
import java.util.Optional;
import javax.persistence.EntityGraph;

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
}
