package at.fhv.teamb.symphoniacus.persistence.model;

import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyPositionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IInstrumentationPositionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISectionEntity;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "dutyPosition")
public class DutyPositionEntity implements IDutyPositionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dutyPositionId")
    private Integer dutyPositionId;

    @Column(name = "description")
    private String description;

    @ManyToOne(
        fetch = FetchType.LAZY,
        cascade = {CascadeType.PERSIST, CascadeType.MERGE},
        targetEntity = InstrumentationPositionEntity.class
    )
    @JoinColumn(name = "instrumentationPositionId")
    private IInstrumentationPositionEntity instrumentationPosition;

    @ManyToOne(
        fetch = FetchType.LAZY,
        cascade = {CascadeType.PERSIST, CascadeType.MERGE},
        targetEntity = DutyEntity.class
    )
    @JoinColumn(name = "dutyId")
    private IDutyEntity duty;

    @ManyToOne(
        fetch = FetchType.LAZY,
        cascade = {CascadeType.PERSIST, CascadeType.MERGE},
        targetEntity = SectionEntity.class
    )
    @JoinColumn(name = "sectionId")
    private ISectionEntity section;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = MusicianEntity.class)
    @JoinColumn(name = "musicianId")
    private IMusicianEntity musician;

    @Override
    public Integer getDutyPositionId() {
        return this.dutyPositionId;
    }

    @Override
    public void setDutyPositionId(Integer dutyPositionId) {
        this.dutyPositionId = dutyPositionId;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public IInstrumentationPositionEntity getInstrumentationPosition() {
        return this.instrumentationPosition;
    }

    @Override
    public void setInstrumentationPosition(IInstrumentationPositionEntity instrumentationPosition) {
        this.instrumentationPosition = instrumentationPosition;
    }

    @Override
    public IDutyEntity getDuty() {
        return this.duty;
    }

    @Override
    public void setDuty(IDutyEntity duty) {
        this.duty = duty;
    }

    @Override
    public ISectionEntity getSection() {
        return this.section;
    }

    @Override
    public void setSection(ISectionEntity section) {
        this.section = section;
    }

    @Override
    public IMusicianEntity getMusician() {
        return this.musician;
    }

    @Override
    public void setMusician(IMusicianEntity musician) {
        this.musician = musician;
    }
}
