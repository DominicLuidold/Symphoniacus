package at.fhv.orchestraria.domain.Imodel;

import at.fhv.orchestraria.domain.model.InstrumentationEntity;
import at.fhv.orchestraria.domain.model.SeriesOfPerformancesEntity;

public interface ISeriesOfPerformancesInstrumentation {

    int getSeriesOfPerformancesInstrumentationId();
    SeriesOfPerformancesEntity getSeriesOfPerformances();
    InstrumentationEntity getInstrumentation();


}
