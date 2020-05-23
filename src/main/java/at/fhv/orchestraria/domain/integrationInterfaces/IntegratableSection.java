package at.fhv.orchestraria.domain.integrationInterfaces;

import at.fhv.orchestraria.domain.Imodel.IDutyPosition;

import java.util.Collection;

public interface IntegratableSection {
    Collection<IntegratableDutyPosition> getIntegratableDutyPositions();
}
