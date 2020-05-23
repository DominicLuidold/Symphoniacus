package at.fhv.teamb.symphoniacus.domain.adapter;

import at.fhv.orchestraria.domain.integrationInterfaces.IntegratableDuty;
import at.fhv.orchestraria.domain.integrationInterfaces.IntegratableDutyPosition;
import at.fhv.orchestraria.domain.integrationInterfaces.IntegratableInstrumentationPosition;
import at.fhv.orchestraria.domain.integrationInterfaces.IntegratableMusician;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyPositionEntity;

public class DutyPositionAdapter implements IntegratableDutyPosition {
    private final IDutyPositionEntity dutyPosition;

    public DutyPositionAdapter(IDutyPositionEntity dutyPosition) {
        this.dutyPosition = dutyPosition;
    }

    @Override
    public IntegratableDuty getDuty() {
        return new DutyAdapter(this.dutyPosition.getDuty());
    }

    @Override
    public IntegratableMusician getMusician() {
        // For whatever reason, Team C is not consistently using Optionals. They said we
        // should return null here. Really dirty solution.. :(
        if (this.dutyPosition.getMusician() != null) {
            return new MusicianAdapter(this.dutyPosition.getMusician());
        } else {
            return null;
        }
    }

    @Override
    public IntegratableInstrumentationPosition getInstrumentationPosition() {
        return new InstrumentationPositionAdapter(
            this.dutyPosition.getInstrumentationPosition()
        );
    }
}
