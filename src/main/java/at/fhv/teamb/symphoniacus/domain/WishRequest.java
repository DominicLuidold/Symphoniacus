package at.fhv.teamb.symphoniacus.domain;

import at.fhv.teamb.symphoniacus.domain.type.WishRequestType;
import at.fhv.teamb.symphoniacus.persistence.model.NegativeDateWishEntity;
import at.fhv.teamb.symphoniacus.persistence.model.NegativeDutyWishEntity;
import at.fhv.teamb.symphoniacus.persistence.model.PositiveWishEntity;
import at.fhv.teamb.symphoniacus.persistence.model.WishRequestable;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicianEntity;
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
    private final boolean isPositive;
    private final WishRequestable wishRequestEntity;
    private final WishRequestType wishRequestType;

    /**
     * Private constructor -> factory pattern.
     *
     * @param isPositive  boolean if request is a positive
     * @param wishRequest request itself
     * @param wishType    type of the wish
     */
    private WishRequest(
        boolean isPositive,
        WishRequestType wishType,
        WishRequestable wishRequest
    ) {
        this.isPositive = isPositive;
        this.wishRequestEntity = wishRequest;
        this.wishRequestType = wishType;
    }

    /**
     * Finds the first matching wish of a musician of a given Set of wishes.
     *
     * @param musician     musician
     * @param wishRequests Set of wishRequests
     * @return WishRequest or optional.empty if no wish was found
     */
    public static Optional<WishRequest> getWishRequestToMusician(
        IMusicianEntity musician,
        Set<WishRequestable> wishRequests
    ) {
        for (WishRequestable wish : wishRequests) {
            if (wish.getMusician().getMusicianId().equals(musician.getMusicianId())) {
                WishRequestType tempType = null;
                boolean isPositive = true;
                if (wish instanceof PositiveWishEntity) {
                    tempType = WishRequestType.POSITIVE_WISH;
                } else if (wish instanceof NegativeDutyWishEntity) {
                    tempType = WishRequestType.NEGATIVE_DUTY_WISH;
                    isPositive = false;
                } else if (wish instanceof NegativeDateWishEntity) {
                    tempType = WishRequestType.NEGATIVE_DATE_WISH;
                    isPositive = false;
                }
                // Validation für zukünfigte Erweiterbarkeit der Wunscharten
                if (tempType != null) {
                    return Optional.of(new WishRequest(isPositive, tempType, wish));
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

    public WishRequestType getWishRequestType() {
        return this.wishRequestType;
    }

    public WishRequestable getWishRequestEntity() {
        return this.wishRequestEntity;
    }

    public boolean isWishPositive() {
        return this.isPositive;
    }
}
