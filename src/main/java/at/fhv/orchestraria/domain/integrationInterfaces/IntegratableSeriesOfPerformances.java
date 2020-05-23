package at.fhv.orchestraria.domain.integrationInterfaces;

import at.fhv.orchestraria.domain.Imodel.ISeriesOfPerformancesInstrumentation;

import java.util.Collection;

public interface IntegratableSeriesOfPerformances {
    boolean isTour();

    Collection<ISeriesOfPerformancesInstrumentation> getISeriesOfPerformancesInstrumentations();
}
