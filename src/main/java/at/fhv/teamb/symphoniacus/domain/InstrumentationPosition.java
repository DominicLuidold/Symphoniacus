package at.fhv.teamb.symphoniacus.domain;

import at.fhv.teamb.symphoniacus.persistence.model.InstrumentationPositionEntity;

/**
 * Domain object for InstrumentationPosition.
 *
 * @author Dominic Luidold
 */
public class InstrumentationPosition {
    private InstrumentationPositionEntity entity;

    public InstrumentationPosition(InstrumentationPositionEntity entity) {
        this.entity = entity;
    }

    public InstrumentationPositionEntity getEntity() {
        return this.entity;
    }
}
