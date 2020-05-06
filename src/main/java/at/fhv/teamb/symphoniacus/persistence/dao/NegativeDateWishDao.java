package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.NegativeDateWishEntity;
import at.fhv.teamb.symphoniacus.persistence.model.WishRequestable;
import java.util.List;
import java.util.Optional;
import javax.persistence.TypedQuery;

public class NegativeDateWishDao extends BaseDao<NegativeDateWishEntity> {

    /**
     * Finds a {@link NegativeDateWishEntity} by its key.
     *
     * @param key The key of the duty
     * @return The duty that is looked for
     */
    @Override
    public Optional<NegativeDateWishEntity> find(Integer key) {
        return this.find(NegativeDateWishEntity.class, key);
    }

    @Override
    public Optional<NegativeDateWishEntity> persist(NegativeDateWishEntity elem) {
        return this.persist(NegativeDateWishEntity.class,elem);
    }

    @Override
    public Optional<NegativeDateWishEntity> update(NegativeDateWishEntity elem) {
        return Optional.empty();
    }

    @Override
    public boolean remove(NegativeDateWishEntity elem) {
        return false;
    }

    /**
     * Finds all NegativeDateDateWishes for a given duty.
     *
     * @param duty duty
     * @return List of (Interface)WishRequestable
     */
    public List<WishRequestable> getAllNegativeDateWishes(DutyEntity duty) {
        TypedQuery<WishRequestable> query = entityManager.createQuery(
            "SELECT nd FROM NegativeDateWishEntity nd "
                + "JOIN nd.monthlySchedules ms "
                + "JOIN ms.weeklySchedules ws "
                + "JOIN ws.duties d "
                + "WHERE d = :duty "
                + "AND nd.startDate <= :startDate AND nd.endDate >= :endDate",
            WishRequestable.class
        );

        query.setParameter("duty", duty);
        query.setParameter("startDate", duty.getStart().toLocalDate());
        query.setParameter("endDate", duty.getEnd().toLocalDate());

        return query.getResultList();
    }
}
