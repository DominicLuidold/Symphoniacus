package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.PositiveWishEntity;
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

    public List<PositiveWishEntity> getAllPositiveWishes(DutyEntity duty) {
        this.createEntityManager();

        TypedQuery<PositiveWishEntity> query =
            this.entityManager.createQuery("", PositiveWishEntity.class);

        return null;
    }
}
