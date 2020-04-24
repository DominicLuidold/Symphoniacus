package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.NegativeDateWishEntity;
import java.util.List;
import java.util.Optional;

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

    public List<NegativeDateWishEntity> getAllNegativeDateWishes(DutyEntity duty) {
        return null;
    }
}
