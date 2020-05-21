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
    private final IDutyPositionEntity entity;
    private final MusicalPiece musicalPiece;
    private int positionNumber;
    private String positionDescription;

    /**
     * Initializes the DutyPosition object based on provided {@link DutyPositionEntity}.
     *
     * @param entity The entity to use
     */
    public DutyPosition(IDutyPositionEntity entity) {
        this.entity = entity;
        this.musicalPiece = new MusicalPiece(
            entity.getInstrumentationPosition().getInstrumentation().getMusicalPiece()
        );
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

    public MusicalPiece getMusicalPiece() {
        return this.musicalPiece;
    }

    public int getPositionNumber() {
        return this.positionNumber;
    }

    public void setPositionNumber(int positionNumber) {
        this.positionNumber = positionNumber;
    }

    public String getPositionDescription() {
        return this.positionDescription;
    }

    public void setPositionDescription(String positionDescription) {
        this.positionDescription = positionDescription;
    }
}
