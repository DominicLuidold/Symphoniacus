package at.fhv.teamb.symphoniacus.persistence.model;

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
@Table(name = "dutyPosition_musician")
public class DutyPositionMusician {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dutyPosition_musician")
    private Integer dutyPositionMusician;

    @Column(name = "dutyPositionId", insertable = false, updatable = false)
    private Integer dutyPositionId;

    @Column(name = "musicianId")
    private Integer musicianId;

    //Many-To-One Part for DUTYPosition Table
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dutyPositionId")
    private DutyPositionEntity dutyPosition;

    public DutyPositionEntity getDutyPosition() {
        return this.dutyPosition;
    }

    public void setDutyPosition(DutyPositionEntity dutyPosition) {
        this.dutyPosition = dutyPosition;
    }

    public Integer getDutyPositionMusician() {
        return this.dutyPositionMusician;
    }

    public void setDutyPositionMusician(Integer dutyPositionMusician) {
        this.dutyPositionMusician = dutyPositionMusician;
    }

    public Integer getDutyPositionId() {
        return this.dutyPositionId;
    }

    public void setDutyPositionId(Integer dutyPositionId) {
        this.dutyPositionId = dutyPositionId;
    }

    public Integer getMusicianId() {
        return this.musicianId;
    }

    public void setMusicianId(Integer musicianId) {
        this.musicianId = musicianId;
    }
}
