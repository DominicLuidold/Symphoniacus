package at.fhv.orchestraria.domain.model;

import at.fhv.orchestraria.domain.Imodel.ISeriesOfPerformancesInstrumentation;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "seriesOfPerformances_instrumentation", schema = "ni128610_1sql8")
public class SeriesOfPerformancesInstrumentationEntityC implements ISeriesOfPerformancesInstrumentation, Serializable {
    private int seriesOfPerformancesInstrumentationId;
    private SeriesOfPerformancesEntityC seriesOfPerformances;
    private InstrumentationEntityC instrumentation;

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
        SeriesOfPerformancesInstrumentationEntityC that = (SeriesOfPerformancesInstrumentationEntityC) o;
        return seriesOfPerformancesInstrumentationId == that.seriesOfPerformancesInstrumentationId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(seriesOfPerformancesInstrumentationId);
    }

    @ManyToOne
    @JoinColumn(name = "seriesOfPerformancesId", referencedColumnName = "seriesOfPerformancesId", nullable = false)
    public SeriesOfPerformancesEntityC getSeriesOfPerformances() {
        return seriesOfPerformances;
    }

    public void setSeriesOfPerformances(SeriesOfPerformancesEntityC seriesOfPerformances) {
        this.seriesOfPerformances= seriesOfPerformances;
    }

    @ManyToOne
    @JoinColumn(name = "instrumentationId", referencedColumnName = "instrumentationId", nullable = false)
    public InstrumentationEntityC getInstrumentation() {
        return instrumentation;
    }

    public void setInstrumentation(InstrumentationEntityC instrumentationByInstrumentationId) {
        this.instrumentation = instrumentationByInstrumentationId;
    }
}
