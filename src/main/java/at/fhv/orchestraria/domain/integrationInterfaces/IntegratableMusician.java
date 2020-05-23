package at.fhv.orchestraria.domain.integrationInterfaces;

import at.fhv.orchestraria.domain.Imodel.IDuty;

import java.util.Collection;

public interface IntegratableMusician {
    Collection<IntegratableDutyPosition> getIntegratableDutyPositions();

    IntegratableSection getSection();

    IntegratableUser getUser();

}
