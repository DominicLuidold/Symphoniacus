package at.fhv.teamb.symphoniacus.application;

import at.fhv.teamb.symphoniacus.domain.Duty;
import at.fhv.teamb.symphoniacus.domain.MusicalPiece;
import at.fhv.teamb.symphoniacus.domain.Musician;
import at.fhv.teamb.symphoniacus.domain.WishRequest;
import at.fhv.teamb.symphoniacus.persistence.dao.NegativeDateWishDao;
import at.fhv.teamb.symphoniacus.persistence.dao.NegativeDutyWishDao;
import at.fhv.teamb.symphoniacus.persistence.dao.PositiveWishDao;
import at.fhv.teamb.symphoniacus.persistence.dao.interfaces.INegativeDateWishDao;
import at.fhv.teamb.symphoniacus.persistence.dao.interfaces.INegativeDutyWishDao;
import at.fhv.teamb.symphoniacus.persistence.dao.interfaces.IPositiveWishDao;
import at.fhv.teamb.symphoniacus.persistence.model.WishRequestable;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyEntity;
import at.fhv.teamb.symphoniacus.presentation.internal.MusicalPieceComboView;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

/**
 * UseCase Controller responsible for wishes of a musician.
 *
 * @author Danijel Antonijevic
 * @author Nino Heinzle
 */
public class WishRequestManager {
    private final IPositiveWishDao positiveWishDao;
    private final INegativeDutyWishDao negDutyWishDao;
    private final INegativeDateWishDao negDateWishDao;
    private Set<WishRequestable> allWishRequests;

    /**
     * Initializes the WishRequestManager.
     */
    public WishRequestManager() {
        this.positiveWishDao = new PositiveWishDao();
        this.negDutyWishDao = new NegativeDutyWishDao();
        this.negDateWishDao = new NegativeDateWishDao();
    }

    /**
     * Preloads all WishRequests that exist for a given duty.
     *
     * @param duty duty
     */
    public void loadAllWishRequests(IDutyEntity duty) {
        allWishRequests = new LinkedHashSet<>();
        this.allWishRequests.addAll(positiveWishDao.getAllPositiveWishes(duty));
        this.allWishRequests.addAll(negDateWishDao.getAllNegativeDateWishes(duty));
        this.allWishRequests.addAll(negDutyWishDao.getAllNegativeDutyWishes(duty));
    }

    /**
     * Sets the Attribute 'wishRequest' of the musician domain object.
     *
     * @param musician given musician domain object
     * @return the same musician
     */
    public Musician setMusicianWishRequest(Musician musician, IDutyEntity duty) {
        // Beware! if allWish isn't loaded this method will load
        // the wishes individually from the DAO
        if (this.allWishRequests == null) {
            // TODO - lösung überlegen ist nur Alternative,
            //  falls man das laden vergisst, nicht zwingend Notwendig
        } else {
            Optional<WishRequest> wishRequest =
                WishRequest
                    .getWishRequestToMusician(musician.getEntity(), this.allWishRequests);
            if (wishRequest.isPresent()) {
                musician.setWishRequest(wishRequest.get());
            } else {
                musician.setWishRequest(null);
            }
        }
        return musician;
    }

    /**
     *  Chechks if WishRequest exists for given User to given Duty and given Musical Piece.
     *
     * @param m given Musician
     * @param selectedItem given musical piece
     * @param duty given duty
     * @return boolean if WishRequest exists for given duty and musical piece
     */
    public boolean hasWishRequestForGivenDutyAndMusicalPiece(
        Musician m,
        MusicalPiece selectedItem,
        Duty duty
    ) {
        boolean hasRequest = false;

        boolean hasPositiveRequest = this.positiveWishDao
            .hasWishRequestForGivenDutyAndMusicalPiece(m.getEntity(),
                selectedItem.getEntity(),
                duty.getEntity());

        boolean hasNegativeRequest = this.negDutyWishDao
            .hasWishRequestForGivenDutyAndMusicalPiece(m.getEntity(),
                selectedItem.getEntity(),
                duty.getEntity());

        if (hasPositiveRequest) {
            hasRequest = true;
        } else if (hasNegativeRequest) {
            hasRequest = true;
        }
        return hasRequest;
    }
}
