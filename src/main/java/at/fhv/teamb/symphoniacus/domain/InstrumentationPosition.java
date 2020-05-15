package at.fhv.teamb.symphoniacus.domain;

import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IInstrumentationPositionEntity;

/**
 * Domain object for InstrumentationPosition.
 *
 * @author Dominic Luidold
 */
public class InstrumentationPosition {
    private IInstrumentationPositionEntity entity;

    public InstrumentationPosition(IInstrumentationPositionEntity entity) {
        this.entity = entity;
    }

    public IInstrumentationPositionEntity getEntity() {
        return this.entity;
    }
}
