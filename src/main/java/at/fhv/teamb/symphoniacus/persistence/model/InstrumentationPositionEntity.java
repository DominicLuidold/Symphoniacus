package at.fhv.teamb.symphoniacus.persistence.model;

import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IInstrumentationPositionEntity;
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
@Table(name = "instrumentationPosition")
public class InstrumentationPositionEntity implements IInstrumentationPositionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "instrumentationPositionId")
    private Integer instrumentationPositionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sectionInstrumentationId")
    private SectionInstrumentationEntity sectionInstrumentation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instrumentationId")
    private InstrumentationEntity instrumentation;

    @Column(name = "positionDescription")
    private String positionDescription;

    @OneToMany(mappedBy = "instrumentationPosition", orphanRemoval = true)
    private List<DutyPositionEntity> dutyPositions = new LinkedList<>();

    public Integer getInstrumentationPositionId() {
        return this.instrumentationPositionId;
    }

    public void setInstrumentationPositionId(Integer instrumentationPositionId) {
        this.instrumentationPositionId = instrumentationPositionId;
    }

    public SectionInstrumentationEntity getSectionInstrumentation() {
        return this.sectionInstrumentation;
    }

    public void setSectionInstrumentation(
        SectionInstrumentationEntity sectionInstrumentation) {
        this.sectionInstrumentation = sectionInstrumentation;
    }

    public InstrumentationEntity getInstrumentation() {
        return this.instrumentation;
    }

    public void setInstrumentation(
        InstrumentationEntity instrumentation
    ) {
        this.instrumentation = instrumentation;
    }

    public String getPositionDescription() {
        return this.positionDescription;
    }

    public void setPositionDescription(String positionDescription) {
        this.positionDescription = positionDescription;
    }

    public List<DutyPositionEntity> getDutyPositions() {
        return this.dutyPositions;
    }

    public void addDutyPosition(DutyPositionEntity dutyPosition) {
        this.dutyPositions.add(dutyPosition);
        dutyPosition.setInstrumentationPosition(this);
    }

    public void removeDutyPosition(DutyPositionEntity dutyPosition) {
        this.dutyPositions.remove(dutyPosition);
        dutyPosition.setInstrumentationPosition(null);
    }
}
