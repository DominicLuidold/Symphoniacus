package at.fhv.orchestraria.domain.Imodel;

import at.fhv.orchestraria.domain.model.InstrumentationEntityC;
import at.fhv.orchestraria.domain.model.SeriesOfPerformancesEntityC;

public interface ISeriesOfPerformancesInstrumentation {

    int getSeriesOfPerformancesInstrumentationId();
    SeriesOfPerformancesEntityC getSeriesOfPerformances();
    InstrumentationEntityC getInstrumentation();


}
