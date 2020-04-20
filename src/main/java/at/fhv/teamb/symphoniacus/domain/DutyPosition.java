package at.fhv.teamb.symphoniacus.domain;

import at.fhv.teamb.symphoniacus.persistence.model.DutyPositionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.MusicianEntity;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Domain object for DutyPositions.
 *
 * @author Dominic Luidold
 */
public class DutyPosition {
    private final InstrumentationPosition instrumentationPosition;
    private DutyPositionEntity entity;
    private List<Musician> musicians;

    /**
     * Initializes the DutyPosition object based on provided {@link DutyPositionEntity}.
     *
     * @param entity The entity to use
     */
    public DutyPosition(DutyPositionEntity entity) {
        this.entity = entity;
        this.instrumentationPosition =
            new InstrumentationPosition(entity.getInstrumentationPosition());

        // Create unmodifiable list containing Musicians
        List<Musician> tempMusicians = new LinkedList<>();
        for (MusicianEntity musicianEntity : entity.getMusicians()) {
            tempMusicians.add(new Musician(musicianEntity));
        }
        this.musicians = Collections.unmodifiableList(tempMusicians);
    }

    /**
     * Returns the {@link Musician} assigned to this duty.
     *
     * @return Optional containing the musician, if any is assigned
     */
    public Optional<Musician> getMusician() {
        if (this.musicians.isEmpty() || this.musicians.get(0) == null) {
            return Optional.empty();
        } else {
            return Optional.of(this.musicians.get(0));
        }
    }

    public DutyPositionEntity getEntity() {
        return this.entity;
    }
}
