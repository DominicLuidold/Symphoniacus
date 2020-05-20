package at.fhv.teamb.symphoniacus.application.adapter;

import at.fhv.orchestraria.domain.integrationInterfaces.IntegratableInstrumentationPosition;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IInstrumentationPositionEntity;

public class InstrumentationPositionAdapter implements IntegratableInstrumentationPosition {
    private final IInstrumentationPositionEntity instrumentationPosition;

    public InstrumentationPositionAdapter(IInstrumentationPositionEntity instrumentationPosition) {
        this.instrumentationPosition = instrumentationPosition;
    }

    @Override
    public String getPositionDescription() {
        return this.instrumentationPosition.getPositionDescription();
    }
}
