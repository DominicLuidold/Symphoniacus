package at.fhv.teamb.symphoniacus.presentation.internal;

import at.fhv.teamb.symphoniacus.domain.Musician;
import at.fhv.teamb.symphoniacus.domain.Points;
import at.fhv.teamb.symphoniacus.domain.exception.PointsNotCalculatedException;
import at.fhv.teamb.symphoniacus.presentation.DutyScheduleController;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * TableModel for Musician.
 *
 * @author Valentin Goronjic
 * @author Tobias Moser
 */
public class MusicianTableModel implements TableModel {

    private static final Logger LOG = LogManager.getLogger(MusicianTableModel.class);
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
    public Integer getBalancePoints() {
        try {
            return this.musician.getBalancePoints().getValue();
        } catch (PointsNotCalculatedException e) {
            LOG.error(e);
            return -1;
        }
    }

    public Integer getDebitPoints() {
        try {
            return this.musician.getDebitPoints().getValue();
        } catch (PointsNotCalculatedException e) {
            LOG.error(e);
            return -1;
        }
    }

    public Integer getGainedPoints() {
        try {
            return this.musician.getGainedPoints().getValue();
        } catch (PointsNotCalculatedException e) {
            LOG.error(e);
            return -1;
        }
    }

    public String getPointsSummary() {
        if (this.musician != null) {
            Points p = null;
            try {
                p = this.musician.getBalancePoints();
                return String.valueOf(p.getValue());
            } catch (PointsNotCalculatedException e) {
                LOG.error(e);
            }
        }
        return String.valueOf(-1);
    }
    public boolean getSchedule() {
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
     * Returns whether this wish is positive or not.
     *
     * @return true when wish is positive
     */
    public boolean isWishPositive() {
        if (this.musician.getWishRequest().isEmpty()) {
            return false;
        } else {
            return this.musician.getWishRequest().get().isWishPositive();
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
