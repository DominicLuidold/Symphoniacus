package at.fhv.teamb.symphoniacus.domain;

import at.fhv.teamb.symphoniacus.domain.type.WishRequestType;
import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.MusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.NegativeDateWishEntity;
import at.fhv.teamb.symphoniacus.persistence.model.NegativeDutyWishEntity;
import at.fhv.teamb.symphoniacus.persistence.model.PositiveWishEntity;
import at.fhv.teamb.symphoniacus.persistence.model.WishRequestable;
import java.util.Optional;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Domain Controller responsible for displaying the state of a WishRequest.
 *
 * @author Danijel Antonijevic
 * @author Nino Heinzle
 */
public class WishRequest {
    private static final Logger LOG = LogManager.getLogger(WishRequest.class);
    private WishRequestType wishRequestType;
    private boolean isPositive;
    private WishRequestable wishRequestEntity;
    private DutyEntity duty;

    /**
     * Private constructor -> factory pattern.
     *
     * @param wishType type of the wish
     * @param wishRequest request itself
     * @param isPositive boolean if request is a positive
     * @param duty the wish relating duty
     */
    private WishRequest(WishRequestType wishType, WishRequestable wishRequest, boolean isPositive,
                        DutyEntity duty) {
        this.wishRequestType = wishType;
        this.wishRequestEntity = wishRequest;
        this.isPositive = isPositive;
        this.duty = duty;
    }

    public WishRequestType getWishRequestType() {
        return this.wishRequestType;
    }

    public WishRequestable getWishRequestEntity() {
        return this.wishRequestEntity;
    }

    public boolean isWishPositive() {
        return this.isPositive;
    }

    /**
     * Finds the first matching wish of a musician of a given Set of wishes.
     *
     * @param musician musician
     * @param wishRequests Set of wishRequests
     * @param duty duty of relating wishRequests
     * @return WishRequest or optional.empty if no wish was found
     */
    public static Optional<WishRequest> getWishRequestToMusician(MusicianEntity musician,
                                                                 Set<WishRequestable> wishRequests,
                                                                 DutyEntity duty) {
        for (WishRequestable wish : wishRequests) {
            if (wish.getMusician().getMusicianId().equals(musician.getMusicianId())) {
                WishRequestType tempType = null;
                boolean isPositive = true;
                if (wish instanceof PositiveWishEntity) {
                    tempType = WishRequestType.POSITIVEWISH;
                    isPositive = true;
                } else if (wish instanceof NegativeDutyWishEntity) {
                    tempType = WishRequestType.NEGATIVEDUTYWISH;
                    isPositive = false;
                } else if (wish instanceof NegativeDateWishEntity) {
                    tempType = WishRequestType.NEGATIVEDATEWISH;
                    isPositive = false;
                }
                // Validation für zukünfigte Erweiterbarkeit der Wunscharten
                if (tempType != null) {
                    return Optional.of(new WishRequest(tempType, wish, isPositive,duty));
                } else {
                    LOG.error("A WishRequestType wasn't checked in getWishRequestToMusician");
                    return Optional.empty();
                }
            }
        }
        // Returns empty when there was no wish by this musician inside the wish Set
        LOG.error("Musician has no wish inside the given wishes");
        return Optional.empty();
    }
}
