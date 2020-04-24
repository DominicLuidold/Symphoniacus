package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.PositiveWishEntity;
import at.fhv.teamb.symphoniacus.persistence.model.WishRequestable;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import javax.persistence.TypedQuery;

public class PositiveWishDao extends BaseDao<PositiveWishEntity> {
    @Override
    public Optional<PositiveWishEntity> find(Object key) {
        return Optional.empty();
    }

    @Override
    public Optional<PositiveWishEntity> persist(PositiveWishEntity elem) {
        return Optional.empty();
    }

    @Override
    public Optional<PositiveWishEntity> update(PositiveWishEntity elem) {
        return Optional.empty();
    }

    @Override
    public Boolean remove(PositiveWishEntity elem) {
        return null;
    }

    public List<WishRequestable> getAllPositiveWishes(DutyEntity duty) {
        this.createEntityManager();

        TypedQuery<WishRequestable> query =
            this.entityManager.createQuery(
                "SELECT pw FROM PositiveWishEntity pw "
                    + "JOIN pw.seriesOfPerformances sop "
                    + "JOIN sop.dutyEntities de "
                    + "WHERE de = :duty",
                WishRequestable.class);

        query.setParameter("duty",duty);

        List<WishRequestable> result = query.getResultList();
        return result;
    }
}
