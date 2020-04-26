package at.fhv.teamb.symphoniacus.presentation.internal;

import at.fhv.teamb.symphoniacus.domain.Musician;
import at.fhv.teamb.symphoniacus.domain.exception.PointsNotCalculatedException;
import java.time.format.DateTimeFormatter;

/**
 * TableModel for Musician.
 *
 * @author Valentin Goronjic
 * @author Tobias Moser
 */
public class MusicianTableModel {
    private boolean schedule;
    private Musician musician;

    /**
     * Construct a new Musician Table Model.
     *
     * @param m The musician used for this table Model
     */
    public MusicianTableModel(Musician m) {
        this.musician = m;
    }

    public String getName() {
        return this.musician.getFullName();
    }

    /**
     * Gets the points from the Musician.
     *
     * @return value of Points of Musician.
     */
    public Integer getPoints() {
        try {
            return this.musician.getPoints().getValue();
        } catch (PointsNotCalculatedException e) {
            return -1;
        }
    }

    public Boolean getSchedule() {
        return this.schedule;
    }

    public Musician getMusician() {
        return this.musician;
    }

    public void setMusician(Musician musician) {
        this.musician = musician;
    }

    /**
     * Gets the wish type from the wish request.
     *
     * @return The wish type
     */
    public String getWishType() {
        if (this.musician.getWishRequest().isPresent()) {
            return this.musician.getWishRequest().get().getWishRequestType().toString();
        } else {
            return "Unknown wish type";
        }
    }

    /**
     * Gets the wish request description from the wish request.
     *
     * @return The wish description
     */
    public String getDetails() {
        if (this.musician.getWishRequest().isPresent()) {
            return this.musician.getWishRequest().get().getWishRequestEntity().getDescription();
        } else {
            return "";
        }
    }

    /**
     * Gets the date time range of the wish request.
     *
     * @return The date time range of the request
     */
    public String getDate() {
        if (this.musician.getWishRequest().isPresent()
            && this.musician.getWishRequest().get().getWishRequestEntity().getStartDate() != null
        ) {
            return DateTimeFormatter.ofPattern("dd.MM.yyyy").format(
                this.musician.getWishRequest().get().getWishRequestEntity().getStartDate()
            ) + " - " + DateTimeFormatter.ofPattern("dd.MM.yyyy").format(
                this.musician.getWishRequest().get().getWishRequestEntity().getEndDate()
            );
        } else {
            return "Not applicable";
        }
    }
}
