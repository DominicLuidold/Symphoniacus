package at.fhv.teamb.symphoniacus.application;

import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.DutyPositionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.InstrumentationEntity;
import at.fhv.teamb.symphoniacus.persistence.model.InstrumentationPositionEntity;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class DutyPositionManager {

    /**
     * Creates all {@link DutyPositionEntity} objects for a given {@link DutyEntity}
     * and Set of {@link InstrumentationPositionEntity} objects.
     *
     * @param instrumentations The instrumentations to use
     * @param duty             The duty to use
     * @return A List of duty position entities
     */
    public List<DutyPositionEntity> createDutyPositions(
        Set<InstrumentationEntity> instrumentations,
        DutyEntity duty
    ) {
        List<DutyPositionEntity> dutyPositions = new LinkedList<>();

        // Create duty position for each instrumentation position
        for (InstrumentationEntity inst : instrumentations) {
            for (InstrumentationPositionEntity instPosition : inst.getInstrumentationPositions()) {
                // Fill domain object
                DutyPositionEntity pos = new DutyPositionEntity();
                pos.setMusician(null);
                pos.setDescription(null);
                pos.setDuty(duty);
                pos.setInstrumentationPosition(instPosition);
                pos.setSection(instPosition.getSectionInstrumentation().getSection());
                dutyPositions.add(pos);
            }
        }

        duty.getDutyPositions().addAll(dutyPositions);
        return dutyPositions;
    }
}
