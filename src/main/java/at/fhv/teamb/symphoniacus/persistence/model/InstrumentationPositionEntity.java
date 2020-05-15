package at.fhv.teamb.symphoniacus.persistence.model;

import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyPositionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IInstrumentationEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IInstrumentationPositionEntity;
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
@Table(name = "instrumentationPosition")
public class InstrumentationPositionEntity implements IInstrumentationPositionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "instrumentationPositionId")
    private int instrumentationPositionId;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = SectionInstrumentationEntity.class)
    @JoinColumn(name = "sectionInstrumentationId")
    private ISectionInstrumentationEntity sectionInstrumentation;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = InstrumentationEntity.class)
    @JoinColumn(name = "instrumentationId")
    private IInstrumentationEntity instrumentation;

    @Column(name = "positionDescription")
    private String positionDescription;

    @OneToMany(mappedBy = "instrumentationPosition", orphanRemoval = true, targetEntity = DutyPositionEntity.class)
    private List<IDutyPositionEntity> dutyPositions = new LinkedList<>();

    public Integer getInstrumentationPositionId() {
        return this.instrumentationPositionId;
    }

    public void setInstrumentationPositionId(int instrumentationPositionId) {
        this.instrumentationPositionId = instrumentationPositionId;
    }

    public ISectionInstrumentationEntity getSectionInstrumentation() {
        return this.sectionInstrumentation;
    }

    public void setSectionInstrumentation(
        ISectionInstrumentationEntity sectionInstrumentation) {
        this.sectionInstrumentation = sectionInstrumentation;
    }

    public IInstrumentationEntity getInstrumentation() {
        return this.instrumentation;
    }

    public void setInstrumentation(
        IInstrumentationEntity instrumentation
    ) {
        this.instrumentation = instrumentation;
    }

    public String getPositionDescription() {
        return this.positionDescription;
    }

    public void setPositionDescription(String positionDescription) {
        this.positionDescription = positionDescription;
    }

    public List<IDutyPositionEntity> getDutyPositions() {
        return this.dutyPositions;
    }

    public void addDutyPosition(IDutyPositionEntity dutyPosition) {
        this.dutyPositions.add(dutyPosition);
        dutyPosition.setInstrumentationPosition(this);
    }

    public void removeDutyPosition(IDutyPositionEntity dutyPosition) {
        this.dutyPositions.remove(dutyPosition);
        dutyPosition.setInstrumentationPosition(null);
    }
}
