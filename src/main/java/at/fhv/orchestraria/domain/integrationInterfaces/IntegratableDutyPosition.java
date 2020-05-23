package at.fhv.orchestraria.domain.integrationInterfaces;

import at.fhv.orchestraria.domain.Imodel.IInstrumentationPosition;

public interface IntegratableDutyPosition {
    IntegratableDuty getDuty();

    IntegratableMusician getMusician();

    IntegratableInstrumentationPosition getInstrumentationPosition();
}
