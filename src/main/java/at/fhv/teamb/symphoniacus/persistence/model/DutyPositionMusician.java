package at.fhv.teamb.symphoniacus.persistence.model;

import javax.persistence.*;


@Entity
@Table(name = "dutyPosition_musician")
public class DutyPositionMusician {
    @Id
    @Column(name = "dutyPosition_musician")
    private Integer dutyPositionMusician;

    @Column(name = "dutyPositionId")
    private Integer dutyPositionId;

    @Column(name = "musicianId")
    private Integer musicianId;


    //Many-To-One Part for DUTYPosition Table
    @ManyToOne(fetch = FetchType.LAZY)
    private DutyPosition dutyPosition;

    public DutyPosition getDutyPosition() {
        return dutyPosition;
    }
    public void setDutyPosition(DutyPosition dutyPosition) {
        this.dutyPosition = dutyPosition;
    }


    //Getters and Setters
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
