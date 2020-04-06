package at.fhv.teamb.symphoniacus.persistence.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "dutyPosition")
public class DutyPosition {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "dutyPositionId")
    private Integer dutyPositionId;

    @Column(name = "description")
    private String description;

    @Column(name = "instrumentationPositionId")
    private Integer instrumentationPositionId;

    @Column(name = "dutyId")
    private Integer dutyId;

    @Column(name = "sectionId")
    private Integer sectionId;


    //Many-To-One Part for DUTY Table
    @ManyToOne(fetch = FetchType.LAZY)
    private Duty duty;

    public Duty getDuty() {
        return this.duty;
    }
    public void setDuty(Duty duty) {
        this.duty = duty;
    }


    //One-To-Many Part for DUTYPOSITION_MUSICIAN Table
    @OneToMany(mappedBy = "dutyPosition", orphanRemoval = true)
    @JoinColumn(name="dutyPositionId")
    private Set<DutyPositionMusician> dutyPositionMusicianSet = new HashSet<DutyPositionMusician>();

    public Set<DutyPositionMusician> getDutyPositionMusicianSet() {
        return this.dutyPositionMusicianSet;
    }
    public void setDutyPositionMusicianSet(Set<DutyPositionMusician> dutyPositionMusicianSet) {
        this.dutyPositionMusicianSet = dutyPositionMusicianSet;
    }

    public void addDutyPositionMusician(DutyPositionMusician dutyPositionMusician) {
        this.dutyPositionMusicianSet.add(dutyPositionMusician);
        dutyPositionMusician.setDutyPosition(this);
    }


    //Getters and Setters
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

    public Integer getInstrumentationPositionId() {
        return this.instrumentationPositionId;
    }

    public void setInstrumentationPositionId(Integer instrumentationPositionId) {
        this.instrumentationPositionId = instrumentationPositionId;
    }

    public Integer getDutyId() {
        return this.dutyId;
    }

    public void setDutyId(Integer dutyId) {
        this.dutyId = dutyId;
    }

    public Integer getSectionId() {
        return this.sectionId;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }
}
