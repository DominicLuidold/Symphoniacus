package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.dao.interfaces.INegativeDateWishDao;
import at.fhv.teamb.symphoniacus.persistence.model.NegativeDateWishEntity;
import at.fhv.teamb.symphoniacus.persistence.model.WishRequestable;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.INegativeDateWishEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IUserEntity;

import java.util.List;
import java.util.Optional;
import javax.persistence.TypedQuery;

public class NegativeDateWishDao extends BaseDao<INegativeDateWishEntity>
    implements INegativeDateWishDao {

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
     * {@inheritDoc}
     */
    @Override
    public List<WishRequestable> getAllNegativeDateWishes(IDutyEntity duty) {
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

    /**
     * {@inheritDoc}
     */
    @Override
    public List<INegativeDateWishEntity> getAllNegativeDateWishesOfUser(IUserEntity user) {
        TypedQuery<INegativeDateWishEntity> query = entityManager.createQuery(
                "SELECT nd FROM NegativeDateWishEntity nd "
                        + "JOIN nd.musician mu "
                        + "JOIN mu.user usr "
                        + "WHERE usr = :user ",
                INegativeDateWishEntity.class
        );

        query.setParameter("user", user);

        return query.getResultList();
    }
}
