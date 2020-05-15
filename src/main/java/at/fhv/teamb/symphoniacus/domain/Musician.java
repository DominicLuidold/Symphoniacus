package at.fhv.teamb.symphoniacus.domain;

import at.fhv.teamb.symphoniacus.domain.exception.PointsNotCalculatedException;
import at.fhv.teamb.symphoniacus.persistence.model.DutyPositionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.MusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.IUserEntity;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Domain object for Musician.
 *
 * @author Dominic Luidold
 * @author Valentin Goronjic
 */
public class Musician {
    private static final Logger LOG = LogManager.getLogger(Musician.class);

    private final MusicianEntity entity;
    private final boolean isExternal;
    private final IUserEntity userEntity;
    private Points balancePoints;
    private Points debitPoints;
    private Points gainedPoints;
    private WishRequest wishRequest;
    private Section section;

    public Musician(MusicianEntity entity) {
        this(entity, null, null, null);
    }

    /**
     * Initializes the Musician object based on provided {@link MusicianEntity} and
     * {@link Points} object.
     *
     * @param entity        The entity to use
     * @param balancePoints The balancePoints of the musician
     */
    public Musician(
        MusicianEntity entity,
        Points balancePoints,
        Points debitPoints,
        Points gainedPoints
    ) {
        this.entity = entity;
        this.userEntity = entity.getUser();
        this.balancePoints = balancePoints;
        this.debitPoints = debitPoints;
        this.gainedPoints = gainedPoints;

        this.isExternal = userEntity.getFirstName().equals("Extern");
        this.section = new Section(entity.getSection());
    }

    /**
     * Returns the full name of the {@link Musician} consisting of a {@link IUserEntity}'s first
     * name and last name.
     *
     * @return Full name of a musician
     */
    public String getFullName() {
        return this.userEntity.getFirstName() + " " + this.userEntity.getLastName();
    }

    public String getShortcut() {
        return this.userEntity.getShortcut();
    }

    public boolean isExternal() {
        return this.isExternal;
    }

    public List<DutyPositionEntity> getAssignedDutyPositions() {
        return this.entity.getDutyPositions();
    }

    /**
     * Returns the {@link Points} object containing the desired point type.
     *
     * @return A Point object
     * @throws PointsNotCalculatedException if points have not been calculated
     */
    public Points getBalancePoints() throws PointsNotCalculatedException {
        if (this.balancePoints == null) {
            throw new PointsNotCalculatedException("Balance Points have not been calculated");
        } else {
            return this.balancePoints;
        }
    }

    public void setBalancePoints(Points balancePoints) {
        this.balancePoints = balancePoints;
    }

    /**
     * Gets the debit points if calculated previously.
     *
     * @return Balance Points for a month
     * @throws PointsNotCalculatedException If points not previously calculated
     */
    public Points getDebitPoints() throws PointsNotCalculatedException {
        if (this.debitPoints == null) {
            throw new PointsNotCalculatedException("Debit Points have not been calculated");
        } else {
            return this.debitPoints;
        }
    }

    public void setDebitPoints(Points debitPoints) {
        this.debitPoints = debitPoints;
    }

    /**
     * Gets the gained points if calculated previously.
     *
     * @return Gained Points for a month
     * @throws PointsNotCalculatedException If points not previously calculated
     */
    public Points getGainedPoints() throws PointsNotCalculatedException {
        if (this.gainedPoints == null) {
            throw new PointsNotCalculatedException("Gained Points have not been calculated");
        } else {
            return this.gainedPoints;
        }
    }

    public void setGainedPoints(Points gainedPoints) {
        this.gainedPoints = gainedPoints;
    }

    /**
     * Returns a {@link WishRequest} object that contains any wish requests a musician has.
     *
     * @return Optional of WishRequest, if the musician has a wish request
     */
    public Optional<WishRequest> getWishRequest() {
        return Optional.ofNullable(this.wishRequest);
    }

    public void setWishRequest(WishRequest wishRequest) {
        this.wishRequest = wishRequest;
    }

    public MusicianEntity getEntity() {
        return this.entity;
    }

    public Section getSection() {
        return section;
    }

    @Override
    public boolean equals(Object obj) {
        // Return true if object is compared with itself
        if (obj == this) {
            return true;
        }

        // Check if object is an instance of Musician or not
        if (!(obj instanceof Musician)) {
            return false;
        }

        // Typecast obj to Musician to compare data members
        Musician m = (Musician) obj;

        // Compare data members and return accordingly
        return this.entity.getMusicianId().equals(m.getEntity().getMusicianId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.userEntity.getUserId(), this.entity.getMusicianId());
    }
}
