package at.fhv.teamb.symphoniacus.domain.adapter;

import at.fhv.orchestraria.domain.Imodel.ISeriesOfPerformancesInstrumentation;
import at.fhv.orchestraria.domain.integrationInterfaces.IntegratableSeriesOfPerformances;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISeriesOfPerformancesEntity;
import java.util.Collection;

public class SeriesOfPerformancesAdapter implements IntegratableSeriesOfPerformances {
    private final ISeriesOfPerformancesEntity seriesOfPerformances;

    public SeriesOfPerformancesAdapter(ISeriesOfPerformancesEntity seriesOfPerformances) {
        this.seriesOfPerformances = seriesOfPerformances;
    }

    @Override
    public boolean isTour() {
        return this.seriesOfPerformances.getIsTour();
    }

    @Override
    public Collection<ISeriesOfPerformancesInstrumentation>
        getISeriesOfPerformancesInstrumentations() {
        // Only used in use cases that are not part of the integration
        return null;
    }
}
