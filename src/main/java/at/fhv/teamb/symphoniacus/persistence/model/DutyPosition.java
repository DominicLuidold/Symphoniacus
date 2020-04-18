package at.fhv.teamb.symphoniacus.persistence.model;

import java.util.HashSet;
import java.util.Set;
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
@Table(name = "dutyPosition")
public class DutyPosition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dutyPositionId")
    private Integer dutyPositionId;

    @Column(name = "description")
    private String description;

    @Column(name = "instrumentationPositionId")
    private Integer instrumentationPositionId;

    @Column(name = "dutyId", updatable = false, insertable = false)
    private Integer dutyId;

    @Column(name = "sectionId")
    private Integer sectionId;

    //Many-To-One Part for DUTY Table
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dutyId")
    private DutyEntity dutyEntity;

    public DutyEntity getDutyEntity() {
        return this.dutyEntity;
    }

    public void setDutyEntity(DutyEntity dutyEntity) {
        this.dutyEntity = dutyEntity;
    }

    //One-To-Many Part for DUTYPOSITION_MUSICIAN Table
    @OneToMany(mappedBy = "dutyPosition", orphanRemoval = true)
    private Set<DutyPositionMusician> dutyPositionMusicianSet = new HashSet<>();

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
