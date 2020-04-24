package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.NegativeDutyWishEntity;
import at.fhv.teamb.symphoniacus.persistence.model.WishRequestable;
import java.util.List;
import java.util.Optional;
import javax.persistence.TypedQuery;

public class NegativeDutyWishDao extends BaseDao<NegativeDutyWishEntity> {
    @Override
    public Optional<NegativeDutyWishEntity> find(Object key) {
        return Optional.empty();
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

    public List<WishRequestable> getAllNegativeDutyWishes(DutyEntity duty) {
        this.createEntityManager();

        TypedQuery<WishRequestable> query =
            this.entityManager.createQuery(
                "SELECT nw FROM NegativeDutyWishEntity nw "
                    + "JOIN nw.seriesOfPerformances sop "
                    + "JOIN sop.dutyEntities de "
                    + "WHERE de = :duty",
                WishRequestable.class);

        query.setParameter("duty",duty);

        List<WishRequestable> result = query.getResultList();
        return result;
    }
}
