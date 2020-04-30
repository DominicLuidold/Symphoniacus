package at.fhv.teamb.symphoniacus.persistence.model;

import java.util.LinkedList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "instrumentation")
public class InstrumentationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "instrumentationId")
    private Integer instrumentationId;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "musicalPieceId")
    private MusicalPieceEntity musicalPiece;

    @ManyToMany(mappedBy = "instrumentations")
    private List<SeriesOfPerformancesEntity> seriesOfPerformances = new LinkedList<>();

    @OneToMany(mappedBy = "instrumentation", orphanRemoval = true)
    private List<SectionInstrumentationEntity> sectionInstrumentations = new LinkedList<>();

    @OneToMany(mappedBy = "instrumentation", orphanRemoval = true)
    private List<InstrumentationPositionEntity> instrumentationPositions = new LinkedList<>();

    public Integer getInstrumentationId() {
        return this.instrumentationId;
    }

    public void setInstrumentationId(Integer instrumentationId) {
        this.instrumentationId = instrumentationId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MusicalPieceEntity getMusicalPiece() {
        return this.musicalPiece;
    }

    public void setMusicalPiece(MusicalPieceEntity musicalPiece) {
        this.musicalPiece = musicalPiece;
    }

    public List<SectionInstrumentationEntity> getSectionInstrumentations() {
        return this.sectionInstrumentations;
    }

    public void addSectionInstrumentation(
        SectionInstrumentationEntity sectionInstrumentation
    ) {
        this.sectionInstrumentations.add(sectionInstrumentation);
        sectionInstrumentation.setInstrumentation(this);
    }

    public void removeSectionInstrumentation(
        SectionInstrumentationEntity sectionInstrumentation
    ) {
        this.sectionInstrumentations.remove(sectionInstrumentation);
        sectionInstrumentation.setInstrumentation(null);
    }

    public List<InstrumentationPositionEntity> getInstrumentationPositions() {
        return this.instrumentationPositions;
    }

    public void addInstrumentationPosition(
        InstrumentationPositionEntity instrumentationPosition
    ) {
        this.instrumentationPositions.add(instrumentationPosition);
        instrumentationPosition.setInstrumentation(this);
    }

    public void removeInstrumentationPosition(
        InstrumentationPositionEntity instrumentationPosition
    ) {
        this.instrumentationPositions.remove(instrumentationPosition);
        instrumentationPosition.setInstrumentation(null);
    }

    public List<SeriesOfPerformancesEntity> getSeriesOfPerformances() {
        return seriesOfPerformances;
    }

    public void setSeriesOfPerformances(
        List<SeriesOfPerformancesEntity> seriesOfPerformances) {
        this.seriesOfPerformances = seriesOfPerformances;
    }

    public void addSeriesOfPerformance(SeriesOfPerformancesEntity series) {
        this.seriesOfPerformances.add(series);
        series.addInstrumentation(this);
    }

    public void removeSeriesOfPerformance(SeriesOfPerformancesEntity series) {
        this.seriesOfPerformances.remove(series);
        series.removeInstrumentation(this);
    }
}
