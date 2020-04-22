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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "sectionInstrumentation")
public class SectionInstrumentationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sectionInstrumentationId")
    private Integer sectionInstrumentationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instrumentationId")
    private InstrumentationEntity instrumentation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sectionId")
    private SectionEntity section;

    @Column(name = "predefinedSectionInstrumentation")
    private String predefinedSectionInstrumentation;

    @OneToMany(mappedBy = "sectionInstrumentation", orphanRemoval = true)
    private List<InstrumentationPositionEntity> instrumentationPositions = new LinkedList<>();

    public Integer getSectionInstrumentationId() {
        return this.sectionInstrumentationId;
    }

    public void setSectionInstrumentationId(Integer sectionInstrumentationId) {
        this.sectionInstrumentationId = sectionInstrumentationId;
    }

    public InstrumentationEntity getInstrumentation() {
        return instrumentation;
    }

    public void setInstrumentation(
        InstrumentationEntity instrumentation) {
        this.instrumentation = instrumentation;
    }

    public SectionEntity getSection() {
        return section;
    }

    public void setSection(SectionEntity section) {
        this.section = section;
    }

    public String getPredefinedSectionInstrumentation() {
        return this.predefinedSectionInstrumentation;
    }

    public void setPredefinedSectionInstrumentation(String predefinedSectionInstrumentation) {
        this.predefinedSectionInstrumentation = predefinedSectionInstrumentation;
    }

    public List<InstrumentationPositionEntity> getInstrumentationPositions() {
        return this.instrumentationPositions;
    }

    public void addInstrumentationPosition(InstrumentationPositionEntity instrumentationPosition) {
        this.instrumentationPositions.add(instrumentationPosition);
        instrumentationPosition.setSectionInstrumentation(this);
    }

    public void removeInstrumentationPosition(
        InstrumentationPositionEntity instrumentationPosition
    ) {
        this.instrumentationPositions.remove(instrumentationPosition);
        instrumentationPosition.setSectionInstrumentation(null);
    }
}
