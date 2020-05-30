package at.fhv.teamb.symphoniacus.persistence.dao.interfaces;

import at.fhv.teamb.symphoniacus.persistence.Dao;
import at.fhv.teamb.symphoniacus.persistence.model.WishRequestable;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicalPieceEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IPositiveWishEntity;

import java.util.List;

public interface IPositiveWishDao extends Dao<IPositiveWishEntity> {

    /**
     * Finds all PositiveWishes for a given duty.
     *
     * @param duty duty
     * @return List of (Interface)WishRequestable
     */
    List<WishRequestable> getAllPositiveWishes(IDutyEntity duty);

    boolean hasWishRequestForGivenDutyAndMusicalPiece(IMusicianEntity entity,
                                                      IMusicalPieceEntity entity1,
                                                      IDutyEntity entity2);

    /**
     * Finds all PositiveDutyWishes for a given duty and musician.
     *
     * @param duty given duty
     * @param musician given musician
     * @return List of all positive Duty wishes
     */
    List<IPositiveWishEntity> getAllPositiveWishesForMusician(
            IDutyEntity duty,
            IMusicianEntity musician
    );
}
