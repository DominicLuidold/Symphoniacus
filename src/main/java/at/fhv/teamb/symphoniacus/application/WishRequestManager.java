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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    private static final Logger LOG = LogManager.getLogger(WishRequestManager.class);
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

    private boolean checkSopOrMusicalPieceWish(
            IWishEntryEntity wishEntry,
            int wishMusicianId,
            int musicianId,
            IDutyEntity duty,
            IMusicalPieceEntity musicalPiece
    ) {
        LOG.debug("Checking SOP or MusicalPiece wish");
        if (wishMusicianId == musicianId) {
            LOG.debug("Wish {} has musician: {}", wishEntry.getWishEntryId(), musicianId);
            // This wish is for the given musician
            for (IMusicalPieceEntity mp : wishEntry.getMusicalPieces()) {
                // Wish for whole SOP
                boolean isSopRequest = isWishEntryDutySopSame(wishEntry, duty);
                if (isSopRequest) {
                    LOG.debug(
                        "WishEntry {} is for whole series {}",
                        wishEntry.getWishEntryId(),
                        duty.getSeriesOfPerformances().getDescription()
                    );
                    return true;
                }
                // Wish for this musical piece
                if (duty.getDutyId().equals(wishEntry.getDuty().getDutyId())
                        && mp.getMusicalPieceId().equals(musicalPiece.getMusicalPieceId())) {
                    LOG.debug(
                            "WishEntry {} is for Musical Piece {}",
                            wishEntry.getWishEntryId(),
                            mp.getName()
                    );
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if a WishRequest exists for the given User, Duty and Musical Piece.
     *
     * @param m            given Musician
     * @param musicalPiece given Musical Piece
     * @param duty         given Duty
     * @return boolean     If WishRequest exists for given Duty and Musical Piece
     */
    public boolean hasWishRequestForGivenDutyAndMusicalPiece(
            Musician m,
            MusicalPiece musicalPiece,
            Duty duty
    ) {
        boolean hasRequest = false;

        for (IWishEntryEntity wishEntry : this.wishEntries) {
            int wishMusicianId = -1;
            if (wishEntry.getPositiveWish() != null) {
                wishMusicianId = wishEntry
                        .getPositiveWish()
                        .getMusician()
                        .getMusicianId();
            } else if (wishEntry.getNegativeDutyWish() != null) {
                wishMusicianId = wishEntry
                        .getNegativeDutyWish()
                        .getMusician()
                        .getMusicianId();
            }
            hasRequest = checkSopOrMusicalPieceWish(
                    wishEntry,
                    wishMusicianId,
                    m.getEntity().getMusicianId(),
                    duty.getEntity(),
                    musicalPiece.getEntity()
            );
            if (hasRequest) {
                return true;
            }
        }

        LOG.debug(
                "Does Musician {} have a wish for Duty {} with MusicalPiece {}? {}",
                m.getFullName(),
                duty.getTitle(),
                musicalPiece.getEntity().getName(),
                hasRequest
        );
        //if a wishrequest is set for a whole series of performances
        // the wish should display on every duty of the sop
        return hasRequest;
    }

    private boolean isWishEntryDutySopSame(IWishEntryEntity wishEntry, IDutyEntity duty) {
        if (wishEntry.getSeriesOfPerformances() != null
                && wishEntry.getSeriesOfPerformances().getSeriesOfPerformancesId()
                .equals(
                    duty.getSeriesOfPerformances().getSeriesOfPerformancesId()
                )
        ) {
            return true;
        }
        return false;
    }
}
