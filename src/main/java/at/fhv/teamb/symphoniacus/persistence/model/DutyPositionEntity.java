package at.fhv.teamb.symphoniacus.persistence.model;

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
public class DutyPositionEntity {
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
    private InstrumentationPositionEntity instrumentationPosition;

    @ManyToOne(
        fetch = FetchType.LAZY,
        cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    @JoinColumn(name = "dutyId")
    private DutyEntity duty;

    @ManyToOne(
        fetch = FetchType.LAZY,
        cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    @JoinColumn(name = "sectionId")
    private SectionEntity section;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "musicianId")
    private MusicianEntity musician;

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

    public InstrumentationPositionEntity getInstrumentationPosition() {
        return this.instrumentationPosition;
    }

    public void setInstrumentationPosition(InstrumentationPositionEntity instrumentationPosition) {
        this.instrumentationPosition = instrumentationPosition;
    }

    public DutyEntity getDuty() {
        return this.duty;
    }

    public void setDuty(DutyEntity duty) {
        this.duty = duty;
    }

    public SectionEntity getSection() {
        return this.section;
    }

    public void setSection(SectionEntity section) {
        this.section = section;
    }

    public MusicianEntity getMusician() {
        return this.musician;
    }

    public void setMusician(MusicianEntity musician) {
        this.musician = musician;
    }
}
