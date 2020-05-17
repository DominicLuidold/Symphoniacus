package at.fhv.teamb.symphoniacus.application;

import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.DutyPositionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.InstrumentationPositionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IInstrumentationEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IInstrumentationPositionEntity;
import java.util.Set;

public class DutyPositionManager {

    /**
     * Creates all {@link DutyPositionEntity} objects for a given {@link DutyEntity}
     * and Set of {@link InstrumentationPositionEntity} objects.
     *
     * @param instrumentations The instrumentations to use
     * @param duty             The duty to use
     */
    public void createDutyPositions(
        Set<IInstrumentationEntity> instrumentations,
        IDutyEntity duty
    ) {
        // Create duty position for each instrumentation position
        for (IInstrumentationEntity inst : instrumentations) {
            for (IInstrumentationPositionEntity instPosition : inst.getInstrumentationPositions()) {
                // Fill domain object
                DutyPositionEntity pos = new DutyPositionEntity();
                pos.setMusician(null);
                pos.setDescription(null);
                pos.setInstrumentationPosition(instPosition);
                pos.setSection(instPosition.getSectionInstrumentation().getSection());
                duty.addDutyPosition(pos);
            }
        }
    }
}
