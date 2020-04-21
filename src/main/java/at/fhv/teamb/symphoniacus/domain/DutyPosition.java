package at.fhv.teamb.symphoniacus.domain;

import at.fhv.teamb.symphoniacus.persistence.model.DutyPositionEntity;
import java.util.Optional;

/**
 * Domain object for DutyPositions.
 *
 * @author Dominic Luidold
 */
public class DutyPosition {
    private final InstrumentationPosition instrumentationPosition;
    private DutyPositionEntity entity;
    private Musician musician;

    /**
     * Initializes the DutyPosition object based on provided {@link DutyPositionEntity}.
     *
     * @param entity The entity to use
     */
    public DutyPosition(DutyPositionEntity entity) {
        this.entity = entity;
        this.instrumentationPosition =
            new InstrumentationPosition(entity.getInstrumentationPosition());
        this.musician = new Musician(entity.getMusician());
    }

    /**
     * Returns the {@link Musician} assigned to this duty.
     *
     * @return Optional containing the musician, if any is assigned
     */
    public Optional<Musician> getAssignedMusician() {
        if (this.musician == null) {
            return Optional.empty();
        } else {
            return Optional.of(this.musician);
        }
    }

    public DutyPositionEntity getEntity() {
        return this.entity;
    }
}
