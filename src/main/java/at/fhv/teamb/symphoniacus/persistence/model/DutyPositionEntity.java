package at.fhv.teamb.symphoniacus.persistence.model;

import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyPositionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IInstrumentationPositionEntity;
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
        cascade = {CascadeType.PERSIST, CascadeType.MERGE}
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

    public Integer getDutyPositionId() {
        return this.dutyPositionId;
    }

    public void setDutyPositionId(Integer dutyPositionId) {
        this.dutyPositionId = dutyPositionId;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public IInstrumentationPositionEntity getInstrumentationPosition() {
        return this.instrumentationPosition;
    }

    public void setInstrumentationPosition(IInstrumentationPositionEntity instrumentationPosition) {
        this.instrumentationPosition = instrumentationPosition;
    }

    public IDutyEntity getDuty() {
        return this.duty;
    }

    public void setDuty(IDutyEntity duty) {
        this.duty = duty;
    }

    public ISectionEntity getSection() {
        return this.section;
    }

    public void setSection(ISectionEntity section) {
        this.section = section;
    }

    public IMusicianEntity getMusician() {
        return this.musician;
    }

    public void setMusician(IMusicianEntity musician) {
        this.musician = musician;
    }
}
