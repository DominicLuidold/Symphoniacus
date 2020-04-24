package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.NegativeDutyWishEntity;
import java.util.List;
import java.util.Optional;

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

    public List<NegativeDutyWishEntity> getAllNegativeDutyWishes(DutyEntity duty) {
        return null;
    }
}
