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
        // Cruel way to make things work with Team C's code
        return " : " + this.instrumentationPosition.getPositionDescription();
    }
}
