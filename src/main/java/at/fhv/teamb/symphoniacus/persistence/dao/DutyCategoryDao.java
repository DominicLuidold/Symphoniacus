package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.DutyCategoryEntity;
import java.util.Optional;
import javax.persistence.EntityGraph;

/**
 * DAO for DutyCategories.
 *
 * @author : Danijel Antonijevic
 **/
public class DutyCategoryDao extends BaseDao<DutyCategoryEntity> {
    @Override
    public Optional<DutyCategoryEntity> find(Object key) {
        this.createEntityManager();
        EntityGraph graph = this.entityManager.getEntityGraph(
            "dutyCategory-with-points"
        );

        DutyCategoryEntity d = entityManager.createQuery("SELECT d from DutyCategoryEntity d "
                + "WHERE d.dutyCategoryId = :id",
            DutyCategoryEntity.class)
            .setParameter("id", key)
            .setHint("javax.persistence.fetchgraph", graph)
            .getSingleResult();
        this.tearDown();
        return Optional.of(d);
    }

    @Override
    public Optional<DutyCategoryEntity> persist(DutyCategoryEntity elem) {
        return Optional.empty();
    }

    @Override
    public Optional<DutyCategoryEntity> update(DutyCategoryEntity elem) {
        return Optional.empty();
    }

    @Override
    public Boolean remove(DutyCategoryEntity elem) {
        return null;
    }
}
