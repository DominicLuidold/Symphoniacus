package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.NegativeDutyWishEntity;
import at.fhv.teamb.symphoniacus.persistence.model.WishRequestable;
import java.util.List;
import java.util.Optional;
import javax.persistence.TypedQuery;

public class NegativeDutyWishDao extends BaseDao<NegativeDutyWishEntity> {

    /**
     * Finds a {@link NegativeDutyWishEntity} by its key.
     *
     * @param key The key of the duty
     * @return The duty that is looked for
     */
    @Override
    public Optional<NegativeDutyWishEntity> find(Integer key) {
        return this.find(NegativeDutyWishEntity.class, key);
    }

    @Override
    public Optional<NegativeDutyWishEntity> persist(NegativeDutyWishEntity elem) {
        return Optional.empty();
    }

    @Override
    public Optional<NegativeDutyWishEntity> update(NegativeDutyWishEntity elem) {
        return Optional.empty();
    }

    @Override
    public Boolean remove(NegativeDutyWishEntity elem) {
        return null;
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
