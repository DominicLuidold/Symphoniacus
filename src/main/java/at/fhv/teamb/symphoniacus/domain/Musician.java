package at.fhv.teamb.symphoniacus.domain;

import at.fhv.teamb.symphoniacus.domain.exception.PointsNotCalculatedException;
import at.fhv.teamb.symphoniacus.persistence.model.DutyPositionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.MusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.UserEntity;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Domain object for Musician.
 *
 * @author Dominic Luidold
 */
public class Musician {
    private final MusicianEntity entity;
    private final boolean isExternal;
    private final UserEntity userEntity;
    private final Points points;
    private WishRequest wishRequest;

    public Musician(MusicianEntity entity) {
        this(entity, null);
    }

    /**
     * Initializes the Musician object based on provided {@link MusicianEntity} and
     * {@link Points} object.
     *
     * @param entity The entity to use
     * @param points The points of the musician
     */
    public Musician(MusicianEntity entity, Points points) {
        this.entity = entity;
        this.userEntity = entity.getUser();
        this.points = points;
        this.isExternal = userEntity.getFirstName().equals("Extern");
    }

    /**
     * Returns the full name of the {@link Musician} consisting of a {@link UserEntity}'s first
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
    public Points getPoints() throws PointsNotCalculatedException {
        if (this.points == null) {
            throw new PointsNotCalculatedException("Points have not been calculated");
        } else {
            return this.points;
        }
    }

    /**
     * Returns a {@link WishRequest} object that contains any wish requests a musician has.
     *
     * @return Optional of WishRequest, if the musician has a wish request
     */
    public Optional<WishRequest> getWishRequest() {
        if (this.wishRequest == null) {
            return Optional.empty();
        } else {
            return Optional.of(this.wishRequest);
        }
    }

    public void setWishRequest(WishRequest wishRequest) {
        this.wishRequest = wishRequest;
    }

    public MusicianEntity getEntity() {
        return this.entity;
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
