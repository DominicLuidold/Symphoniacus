package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.PositiveWishEntity;
import at.fhv.teamb.symphoniacus.persistence.model.WishRequestable;
import java.util.List;
import java.util.Optional;
import javax.persistence.TypedQuery;

public class PositiveWishDao extends BaseDao<PositiveWishEntity> {

    /**
     * Finds a {@link PositiveWishEntity} by its key.
     *
     * @param key The key of the duty
     * @return The duty that is looked for
     */
    @Override
    public Optional<PositiveWishEntity> find(Integer key) {
        return this.find(PositiveWishEntity.class, key);
    }

    @Override
    public Optional<PositiveWishEntity> persist(PositiveWishEntity elem) {
        return this.persist(PositiveWishEntity.class,elem);
    }

    @Override
    public Optional<PositiveWishEntity> update(PositiveWishEntity elem) {
        return Optional.empty();
    }

    @Override
    public boolean remove(PositiveWishEntity elem) {
        return false;
    }

    /**
     * Finds all PositiveWishes for a given duty.
     *
     * @param duty duty
     * @return List of (Interface)WishRequestable
     */
    public List<WishRequestable> getAllPositiveWishes(DutyEntity duty) {
        TypedQuery<WishRequestable> query = entityManager.createQuery(
            "SELECT pw FROM PositiveWishEntity pw "
                + "JOIN pw.seriesOfPerformances sop "
                + "JOIN sop.dutyEntities de "
                + "WHERE de = :duty",
            WishRequestable.class
        );

        query.setParameter("duty", duty);

        return query.getResultList();
    }
}
