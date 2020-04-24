package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.domain.WishRequest;
import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.NegativeDateWishEntity;
import at.fhv.teamb.symphoniacus.persistence.model.WishRequestable;
import java.util.List;
import java.util.Optional;
import javax.persistence.TypedQuery;

public class NegativeDateWishDao extends BaseDao<NegativeDateWishEntity> {
    @Override
    public Optional<NegativeDateWishEntity> find(Object key) {
        return Optional.empty();
    }

    @Override
    public Optional<NegativeDateWishEntity> persist(NegativeDateWishEntity elem) {
        return Optional.empty();
    }

    @Override
    public Optional<NegativeDateWishEntity> update(NegativeDateWishEntity elem) {
        return Optional.empty();
    }

    @Override
    public Boolean remove(NegativeDateWishEntity elem) {
        return null;
    }

    public List<WishRequestable> getAllNegativeDateWishes(DutyEntity duty) {
        this.createEntityManager();
        TypedQuery<WishRequestable> query = this.entityManager.createQuery(""
            + "SELECT nd FROM NegativeDateWishEntity nd "
            + "JOIN nd.monthlySchedules ms "
            + "JOIN ms.weeklySchedules ws "
            + "JOIN ws.duties d "
            + "WHERE d = :duty "
            + "AND nd.startDate <= :startDate AND nd.endDate >= :endDate", WishRequestable.class);
        query.setParameter("duty", duty);
        query.setParameter("startDate", duty.getStart().toLocalDate());
        query.setParameter("endDate", duty.getEnd().toLocalDate());

        List<WishRequestable> result = query.getResultList();
        return result;
    }
}
