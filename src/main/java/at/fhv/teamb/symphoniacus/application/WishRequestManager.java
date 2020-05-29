package at.fhv.teamb.symphoniacus.application;

import at.fhv.teamb.symphoniacus.domain.Duty;
import at.fhv.teamb.symphoniacus.domain.MusicalPiece;
import at.fhv.teamb.symphoniacus.domain.Musician;
import at.fhv.teamb.symphoniacus.domain.WishRequest;
import at.fhv.teamb.symphoniacus.persistence.dao.NegativeDateWishDao;
import at.fhv.teamb.symphoniacus.persistence.dao.NegativeDutyWishDao;
import at.fhv.teamb.symphoniacus.persistence.dao.PositiveWishDao;
import at.fhv.teamb.symphoniacus.persistence.dao.WishEntryDao;
import at.fhv.teamb.symphoniacus.persistence.dao.interfaces.INegativeDateWishDao;
import at.fhv.teamb.symphoniacus.persistence.dao.interfaces.INegativeDutyWishDao;
import at.fhv.teamb.symphoniacus.persistence.dao.interfaces.IPositiveWishDao;
import at.fhv.teamb.symphoniacus.persistence.dao.interfaces.IWishEntryDao;
import at.fhv.teamb.symphoniacus.persistence.model.WishRequestable;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicalPieceEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IWishEntryEntity;
import java.util.LinkedHashSet;
import java.util.List;
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
    private final IWishEntryDao wishEntryDao;
    private Set<WishRequestable> allWishRequests;
    private List<IWishEntryEntity> wishEntries;


    /**
     * Initializes the WishRequestManager.
     */
    public WishRequestManager() {
        this.positiveWishDao = new PositiveWishDao();
        this.negDutyWishDao = new NegativeDutyWishDao();
        this.negDateWishDao = new NegativeDateWishDao();
        this.wishEntryDao = new WishEntryDao();
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

    public void loadAllWishEntriesForDuty(Duty duty) {
        this.wishEntries = this.wishEntryDao.loadAllWishEntriesForGivenDuty();
    }

    /**
     * Chechks if WishRequest exists for given User to given Duty and given Musical Piece.
     *
     * @param m            given Musician
     * @param musicalPiece given musical piece
     * @param duty         given duty
     * @return boolean if WishRequest exists for given duty and musical piece
     */
    public boolean hasWishRequestForGivenDutyAndMusicalPiece(
        Musician m,
        MusicalPiece musicalPiece,
        Duty duty
    ) {

        boolean hasRequest = false;

        boolean hasPositiveRequest = false;
        boolean hasNegativeRequest = false;
        boolean hasSeriesRequest = false;

        for (IWishEntryEntity wishEntry : this.wishEntries) {
            if (wishEntry.getPositiveWish() != null) {
                if (wishEntry.getPositiveWish().getMusician().getMusicianId()
                    .equals(m.getEntity().getMusicianId())) {
                    for (IMusicalPieceEntity mp : wishEntry.getMusicalPieces()) {
                        hasSeriesRequest = isWishEntryDutySopSame(wishEntry, duty.getEntity());
                        if (duty.getEntity().getDutyId().equals(wishEntry.getDuty().getDutyId())
                            && mp.getMusicalPieceId()
                            .equals(musicalPiece.getEntity().getMusicalPieceId())) {
                            hasPositiveRequest = true;
                        }
                    }
                }
            }
        }

        for (IWishEntryEntity wishEntry : this.wishEntries) {
            if (wishEntry.getNegativeDutyWish() != null) {
                if (wishEntry.getNegativeDutyWish().getMusician().getMusicianId()
                    .equals(m.getEntity().getMusicianId())) {
                    for (IMusicalPieceEntity mp : wishEntry.getMusicalPieces()) {
                        hasSeriesRequest = isWishEntryDutySopSame(wishEntry, duty.getEntity());
                        if (duty.getEntity().getDutyId().equals(wishEntry.getDuty().getDutyId())
                            && mp.getMusicalPieceId()
                            .equals(musicalPiece.getEntity().getMusicalPieceId())) {
                            hasNegativeRequest = true;
                        }
                    }
                }
            }
        }

        if (hasPositiveRequest) {
            hasRequest = true;
        } else if (hasNegativeRequest) {
            hasRequest = true;
        } else if (hasSeriesRequest) {
            hasRequest = true;
        }

        //if a wishrequest is set for a whole series of performances
        // the wish should display on every duty of the sop

        return hasRequest;
    }

    private boolean isWishEntryDutySopSame(IWishEntryEntity wishEntry, IDutyEntity duty) {
        if (wishEntry.getSeriesOfPerformances() != null
            && wishEntry.getSeriesOfPerformances().getSeriesOfPerformancesId().equals(
                duty.getSeriesOfPerformances()
                    .getSeriesOfPerformancesId())) {
            return true;
        }
        return false;
    }
}
