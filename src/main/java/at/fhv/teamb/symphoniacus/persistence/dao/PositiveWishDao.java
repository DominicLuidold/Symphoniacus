package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.PositiveWishEntity;
import at.fhv.teamb.symphoniacus.persistence.model.WishRequestable;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IPositiveWishEntity;
import java.util.List;
import java.util.Optional;
import javax.persistence.TypedQuery;

public class PositiveWishDao extends BaseDao<IPositiveWishEntity> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IPositiveWishEntity> find(Integer key) {
        return this.find(PositiveWishEntity.class, key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IPositiveWishEntity> persist(IPositiveWishEntity elem) {
        return this.persist(PositiveWishEntity.class, elem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IPositiveWishEntity> update(IPositiveWishEntity elem) {
        return this.update(PositiveWishEntity.class, elem);
    }

    @Override
    public boolean remove(IPositiveWishEntity elem) {
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
