package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.NegativeDutyWishEntity;
import at.fhv.teamb.symphoniacus.persistence.model.WishRequestable;
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
        return this.find(NegativeDutyWishEntity.class, key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<INegativeDutyWishEntity> persist(INegativeDutyWishEntity elem) {
        return this.persist(NegativeDutyWishEntity.class, elem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<INegativeDutyWishEntity> update(INegativeDutyWishEntity elem) {
        return this.update(NegativeDutyWishEntity.class, elem);
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
    public List<WishRequestable> getAllNegativeDutyWishes(DutyEntity duty) {
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
