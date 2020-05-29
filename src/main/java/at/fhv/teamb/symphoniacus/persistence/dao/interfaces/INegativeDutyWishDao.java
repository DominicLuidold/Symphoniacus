package at.fhv.teamb.symphoniacus.persistence.dao.interfaces;

import at.fhv.teamb.symphoniacus.persistence.Dao;
import at.fhv.teamb.symphoniacus.persistence.model.WishRequestable;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicalPieceEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.INegativeDutyWishEntity;
import java.util.List;

public interface INegativeDutyWishDao extends Dao<INegativeDutyWishEntity> {

    /**
     * Finds all NegativeDutyWishes for a given duty.
     *
     * @param duty duty
     * @return List of (Interface)WishRequestable
     */
    List<WishRequestable> getAllNegativeDutyWishes(IDutyEntity duty);

    boolean hasWishRequestForGivenDutyAndMusicalPiece(
        IMusicianEntity entity,
        IMusicalPieceEntity entity1,
        IDutyEntity entity2);
}
