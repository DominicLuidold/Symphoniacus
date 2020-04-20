package at.fhv.teamb.symphoniacus.persistence.model;

import java.util.LinkedList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

    @Column(name = "musicalPieceId")
    private Integer musicalPieceId;

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

    public Integer getMusicalPieceId() {
        return this.musicalPieceId;
    }

    public void setMusicalPieceId(Integer musicalPieceId) {
        this.musicalPieceId = musicalPieceId;
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
}
