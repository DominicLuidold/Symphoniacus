package at.fhv.orchestraria.domain.model;

import at.fhv.orchestraria.domain.Imodel.ISeriesOfPerformancesInstrumentation;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "seriesOfPerformances_instrumentation", schema = "ni128610_1sql8")
public class SeriesOfPerformancesInstrumentationEntity implements ISeriesOfPerformancesInstrumentation, Serializable {
    private int seriesOfPerformancesInstrumentationId;
    private SeriesOfPerformancesEntity seriesOfPerformances;
    private InstrumentationEntity instrumentation;

    @Id
    @Column(name = "seriesOfPerformances_instrumentationId")
    public int getSeriesOfPerformancesInstrumentationId() {
        return seriesOfPerformancesInstrumentationId;
    }

    public void setSeriesOfPerformancesInstrumentationId(int seriesOfPerformancesInstrumentationId) {
        this.seriesOfPerformancesInstrumentationId = seriesOfPerformancesInstrumentationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeriesOfPerformancesInstrumentationEntity that = (SeriesOfPerformancesInstrumentationEntity) o;
        return seriesOfPerformancesInstrumentationId == that.seriesOfPerformancesInstrumentationId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(seriesOfPerformancesInstrumentationId);
    }

    @ManyToOne
    @JoinColumn(name = "seriesOfPerformancesId", referencedColumnName = "seriesOfPerformancesId", nullable = false)
    public SeriesOfPerformancesEntity getSeriesOfPerformances() {
        return seriesOfPerformances;
    }

    public void setSeriesOfPerformances(SeriesOfPerformancesEntity seriesOfPerformances) {
        this.seriesOfPerformances= seriesOfPerformances;
    }

    @ManyToOne
    @JoinColumn(name = "instrumentationId", referencedColumnName = "instrumentationId", nullable = false)
    public InstrumentationEntity getInstrumentation() {
        return instrumentation;
    }

    public void setInstrumentation(InstrumentationEntity instrumentationByInstrumentationId) {
        this.instrumentation = instrumentationByInstrumentationId;
    }
}
