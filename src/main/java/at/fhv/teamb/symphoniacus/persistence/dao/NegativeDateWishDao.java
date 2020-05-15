package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.NegativeDateWishEntity;
import at.fhv.teamb.symphoniacus.persistence.model.WishRequestable;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.INegativeDateWishEntity;
import java.util.List;
import java.util.Optional;
import javax.persistence.TypedQuery;

public class NegativeDateWishDao extends BaseDao<INegativeDateWishEntity> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<INegativeDateWishEntity> find(Integer key) {
        return this.find(NegativeDateWishEntity.class, key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<INegativeDateWishEntity> persist(INegativeDateWishEntity elem) {
        return this.persist(NegativeDateWishEntity.class, elem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<INegativeDateWishEntity> update(INegativeDateWishEntity elem) {
        return this.update(NegativeDateWishEntity.class, elem);
    }

    @Override
    public boolean remove(INegativeDateWishEntity elem) {
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
