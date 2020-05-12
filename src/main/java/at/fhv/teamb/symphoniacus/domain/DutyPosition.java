package at.fhv.teamb.symphoniacus.domain;

import at.fhv.teamb.symphoniacus.persistence.model.DutyPositionEntity;
import java.util.Optional;

/**
 * Domain object for DutyPositions.
 *
 * @author Dominic Luidold
 */
public class DutyPosition {
    private DutyPositionEntity entity;
    private Musician musician;

    /**
     * Initializes the DutyPosition object based on provided {@link DutyPositionEntity}.
     *
     * @param entity The entity to use
     */
    public DutyPosition(DutyPositionEntity entity) {
        this.entity = entity;
        if (this.entity.getMusician() != null) {
            this.musician = new Musician(this.entity.getMusician());
        }
    }

    /**
     * Returns the {@link Musician} assigned to this duty.
     *
     * @return Optional containing the musician, if any is assigned
     */
    public Optional<Musician> getAssignedMusician() {
        if (this.musician == null && this.getEntity().getMusician() != null) {
            this.musician = new Musician(this.entity.getMusician());
        }
        return Optional.ofNullable(this.musician);
    }

    public DutyPositionEntity getEntity() {
        return this.entity;
    }

    public Musician getMusician() {
        return musician;
    }

    public void setMusician(Musician musician) {
        this.musician = musician;
    }
}
