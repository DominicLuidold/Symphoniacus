package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.WishRequestable;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.INegativeDutyWishEntity;
import java.util.List;
import java.util.Optional;
import javax.persistence.TypedQuery;

public class NegativeDutyWishDao extends BaseDao<INegativeDutyWishEntity> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<INegativeDutyWishEntity> find(Integer key) {
        return this.find(INegativeDutyWishEntity.class, key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<INegativeDutyWishEntity> persist(INegativeDutyWishEntity elem) {
        return this.persist(INegativeDutyWishEntity.class, elem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<INegativeDutyWishEntity> update(INegativeDutyWishEntity elem) {
        return this.update(INegativeDutyWishEntity.class, elem);
    }

    @Override
    public boolean remove(INegativeDutyWishEntity elem) {
        return false;
    }

    /**
     * Finds all NegativeDutyWishes for a given duty.
     *
     * @param duty duty
     * @return List of (Interface)WishRequestable
     */
    public List<WishRequestable> getAllNegativeDutyWishes(IDutyEntity duty) {
        TypedQuery<WishRequestable> query = entityManager.createQuery(
            "SELECT nw FROM NegativeDutyWishEntity nw "
                + "JOIN nw.seriesOfPerformances sop "
                + "JOIN sop.dutyEntities de "
                + "WHERE de = :duty",
            WishRequestable.class
        );

        query.setParameter("duty", duty);

        return query.getResultList();
    }
}
