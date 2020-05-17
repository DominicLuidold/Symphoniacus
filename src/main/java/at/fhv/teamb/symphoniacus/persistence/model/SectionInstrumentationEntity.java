package at.fhv.teamb.symphoniacus.persistence.model;

import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IInstrumentationEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IInstrumentationPositionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISectionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISectionInstrumentationEntity;
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
public class SectionInstrumentationEntity implements ISectionInstrumentationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sectionInstrumentationId")
    private Integer sectionInstrumentationId;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = InstrumentationEntity.class)
    @JoinColumn(name = "instrumentationId")
    private IInstrumentationEntity instrumentation;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = SectionEntity.class)
    @JoinColumn(name = "sectionId")
    private ISectionEntity section;

    @Column(name = "predefinedSectionInstrumentation")
    private String predefinedSectionInstrumentation;

    @OneToMany(
        mappedBy = "sectionInstrumentation",
        orphanRemoval = true,
        targetEntity = InstrumentationPositionEntity.class
    )
    private List<IInstrumentationPositionEntity> instrumentationPositions = new LinkedList<>();

    @Override
    public Integer getSectionInstrumentationId() {
        return this.sectionInstrumentationId;
    }

    @Override
    public void setSectionInstrumentationId(Integer sectionInstrumentationId) {
        this.sectionInstrumentationId = sectionInstrumentationId;
    }

    @Override
    public IInstrumentationEntity getInstrumentation() {
        return instrumentation;
    }

    @Override
    public void setInstrumentation(
        IInstrumentationEntity instrumentation) {
        this.instrumentation = instrumentation;
    }

    @Override
    public ISectionEntity getSection() {
        return section;
    }

    @Override
    public void setSection(ISectionEntity section) {
        this.section = section;
    }

    @Override
    public String getPredefinedSectionInstrumentation() {
        return this.predefinedSectionInstrumentation;
    }

    @Override
    public void setPredefinedSectionInstrumentation(String predefinedSectionInstrumentation) {
        this.predefinedSectionInstrumentation = predefinedSectionInstrumentation;
    }

    @Override
    public List<IInstrumentationPositionEntity> getInstrumentationPositions() {
        return this.instrumentationPositions;
    }

    @Override
    public void addInstrumentationPosition(IInstrumentationPositionEntity instrumentationPosition) {
        this.instrumentationPositions.add(instrumentationPosition);
        instrumentationPosition.setSectionInstrumentation(this);
    }

    @Override
    public void removeInstrumentationPosition(
        IInstrumentationPositionEntity instrumentationPosition
    ) {
        this.instrumentationPositions.remove(instrumentationPosition);
        instrumentationPosition.setSectionInstrumentation(null);
    }
}
