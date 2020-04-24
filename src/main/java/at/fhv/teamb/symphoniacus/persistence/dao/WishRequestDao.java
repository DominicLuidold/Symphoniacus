package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.NegativeDateWishEntity;
import at.fhv.teamb.symphoniacus.persistence.model.NegativeDutyWishEntity;
import at.fhv.teamb.symphoniacus.persistence.model.PositiveWishEntity;
import java.util.List;

public class WishRequestDao {

    public List<PositiveWishEntity> getAllPositiveWishes(DutyEntity duty) {
        return null;
    }

    public List<NegativeDutyWishEntity> getAllNegativeDutyWishes(DutyEntity duty) {
        return null;
    }

    public List<NegativeDateWishEntity> getAllNegativeDateWishes(DutyEntity duty) {
        return null;
    }
}
