package at.fhv.teamb.symphoniacus.domain;

import at.fhv.teamb.symphoniacus.persistence.model.DutyPositionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyPositionEntity;
import java.util.Optional;

/**
 * Domain object for DutyPositions.
 *
 * @author Dominic Luidold
 */
public class DutyPosition {
    private IDutyPositionEntity entity;

    /**
     * Initializes the DutyPosition object based on provided {@link DutyPositionEntity}.
     *
     * @param entity The entity to use
     */
    public DutyPosition(IDutyPositionEntity entity) {
        this.entity = entity;
    }

    /**
     * Returns the {@link Musician} assigned to this duty.
     *
     * @return Optional containing the musician, if any is assigned
     */
    public Optional<Musician> getAssignedMusician() {
        if (this.entity.getMusician() == null) {
            return Optional.empty();
        } else {
            return Optional.of(new Musician(this.entity.getMusician()));
        }
    }

    public IDutyPositionEntity getEntity() {
        return this.entity;
    }
}
