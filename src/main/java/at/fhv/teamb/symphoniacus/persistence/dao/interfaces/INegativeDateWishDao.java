package at.fhv.teamb.symphoniacus.persistence.dao.interfaces;

import at.fhv.teamb.symphoniacus.persistence.Dao;
import at.fhv.teamb.symphoniacus.persistence.model.WishRequestable;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.INegativeDateWishEntity;
import java.util.List;

public interface INegativeDateWishDao extends Dao<INegativeDateWishEntity> {

    /**
     * Finds all NegativeDateDateWishes for a given duty.
     *
     * @param duty duty
     * @return List of (Interface)WishRequestable
     */
    List<WishRequestable> getAllNegativeDateWishes(IDutyEntity duty);
}
