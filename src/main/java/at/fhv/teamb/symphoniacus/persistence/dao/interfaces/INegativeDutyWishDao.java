package at.fhv.teamb.symphoniacus.persistence.dao.interfaces;

import at.fhv.teamb.symphoniacus.persistence.Dao;
import at.fhv.teamb.symphoniacus.persistence.model.WishRequestable;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicalPieceEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.INegativeDutyWishEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IWishEntryEntity;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

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

    /**
     * Finds all NegativeDutyWishes for a given duty and musician.
     *
     * @param duty given duty
     * @param musician given musician
     * @return List of all negative Duty wishes
     */
    List<INegativeDutyWishEntity> getAllNegativeDutyWishesForMusician(
            IDutyEntity duty,
            IMusicianEntity musician
    );

    /**
     * Finds all WishEntry for a given negativeDutyWish and duty.
     *
     * @param wish given negativeDutyWish
     * @param dutyEntity given Duty
     * @return WishEntry matching given negativeDutyWish
     */
    Optional<IWishEntryEntity> getWishEntryByNegativeDutyWish(
            INegativeDutyWishEntity wish,
            IDutyEntity dutyEntity);
}
