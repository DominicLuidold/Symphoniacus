package at.fhv.teamb.symphoniacus.application;

import at.fhv.teamb.symphoniacus.domain.Musician;
import at.fhv.teamb.symphoniacus.domain.WishRequest;
import at.fhv.teamb.symphoniacus.persistence.dao.NegativeDateWishDao;
import at.fhv.teamb.symphoniacus.persistence.dao.NegativeDutyWishDao;
import at.fhv.teamb.symphoniacus.persistence.dao.PositiveWishDao;
import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.WishRequestable;
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

    private final PositiveWishDao positiveWishDao;
    private final NegativeDutyWishDao negDutyWishDao;
    private final NegativeDateWishDao negDateWishDao;
    private Set<WishRequestable> allWishRequests;

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
    public void loadAllWishRequests(DutyEntity duty) {
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
    public Musician setMusicianWishRequest(Musician musician, DutyEntity duty) {

        // Beware! if allWish isn't loaded this method will load
        // the wishes individually from the DAO
        if (this.allWishRequests == null) {
            // TODO - lösung überlegen ist nur Alternative,
            //  falls man das laden vergisst, nicht zwingend Notwendig
        } else {
            Optional<WishRequest> wishRequest =
                WishRequest
                    .getWishRequestToMusician(musician.getEntity(), this.allWishRequests, duty);
            if (wishRequest.isPresent()) {
                musician.setWishRequest(wishRequest.get());
            } else {
                musician.setWishRequest(null);
            }
        }
        return musician;
    }
}
