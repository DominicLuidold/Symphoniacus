package at.fhv.teamb.symphoniacus.persistence.model;

import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IInstrumentationEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IInstrumentationPositionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicalPieceEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISectionInstrumentationEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISeriesOfPerformancesEntity;
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
public class InstrumentationEntity implements IInstrumentationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "instrumentationId")
    private Integer instrumentationId;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = MusicalPieceEntity.class)
    @JoinColumn(name = "musicalPieceId")
    private IMusicalPieceEntity musicalPiece;

    @ManyToMany(mappedBy = "instrumentations", targetEntity = SeriesOfPerformancesEntity.class)
    private List<ISeriesOfPerformancesEntity> seriesOfPerformances = new LinkedList<>();

    @OneToMany(mappedBy = "instrumentation", fetch = FetchType.LAZY, targetEntity = SectionInstrumentationEntity.class)
    private List<ISectionInstrumentationEntity> sectionInstrumentations = new LinkedList<>();

    @OneToMany(mappedBy = "instrumentation", orphanRemoval = true, targetEntity = InstrumentationPositionEntity.class)
    private List<IInstrumentationPositionEntity> instrumentationPositions = new LinkedList<>();

    @Override
    public Integer getInstrumentationId() {
        return this.instrumentationId;
    }

    @Override
    public void setInstrumentationId(int instrumentationId) {
        this.instrumentationId = instrumentationId;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public IMusicalPieceEntity getMusicalPiece() {
        return this.musicalPiece;
    }

    @Override
    public void setMusicalPiece(IMusicalPieceEntity musicalPiece) {
        this.musicalPiece = musicalPiece;
    }

    @Override
    public List<ISectionInstrumentationEntity> getSectionInstrumentations() {
        return this.sectionInstrumentations;
    }

    @Override
    public void addSectionInstrumentation(
        ISectionInstrumentationEntity sectionInstrumentation
    ) {
        this.sectionInstrumentations.add(sectionInstrumentation);
        sectionInstrumentation.setInstrumentation(this);
    }

    @Override
    public void removeSectionInstrumentation(
        ISectionInstrumentationEntity sectionInstrumentation
    ) {
        this.sectionInstrumentations.remove(sectionInstrumentation);
        sectionInstrumentation.setInstrumentation(null);
    }

    @Override
    public List<IInstrumentationPositionEntity> getInstrumentationPositions() { //TODO: find out how the fuck this works
        return this.instrumentationPositions;
    }

    @Override
    public void addInstrumentationPosition(
        IInstrumentationPositionEntity instrumentationPosition
    ) {
        this.instrumentationPositions.add(instrumentationPosition);
        instrumentationPosition.setInstrumentation(this);
    }

    @Override
    public void removeInstrumentationPosition(
        IInstrumentationPositionEntity instrumentationPosition
    ) {
        this.instrumentationPositions.remove(instrumentationPosition);
        instrumentationPosition.setInstrumentation(null);
    }

    @Override
    public List<ISeriesOfPerformancesEntity> getSeriesOfPerformances() {
        return this.seriesOfPerformances;
    }

    @Override
    public void setSeriesOfPerformances(
        List<ISeriesOfPerformancesEntity> seriesOfPerformances) {
        this.seriesOfPerformances = seriesOfPerformances;
    }

    @Override
    public void addSeriesOfPerformance(ISeriesOfPerformancesEntity series) {
        this.seriesOfPerformances.add(series);
        series.addInstrumentation(this);
    }

    @Override
    public void removeSeriesOfPerformance(ISeriesOfPerformancesEntity series) {
        this.seriesOfPerformances.remove(series);
        series.removeInstrumentation(this);
    }
}
