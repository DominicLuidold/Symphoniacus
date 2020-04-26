package at.fhv.teamb.symphoniacus.domain.type;

import at.fhv.teamb.symphoniacus.domain.WishRequest;

/**
 * The WishRequestType enum indicates which type a {@link WishRequest} has.
 *
 * @author Nino Heinzle
 * @author Dominic Luidold
 */
public enum WishRequestType {
    POSITIVE_WISH("Positive Wish"),
    NEGATIVE_DUTY_WISH("Negative Duty Wish"),
    NEGATIVE_DATE_WISH("Negative Date Wish");

    private final String description;

    WishRequestType(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return this.description;
    }
}
