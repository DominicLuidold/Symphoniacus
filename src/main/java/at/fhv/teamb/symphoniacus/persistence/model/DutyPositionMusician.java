package at.fhv.teamb.symphoniacus.persistence.model;

import javax.persistence.*;

@Entity
@Table(name = "dutyPosition_musican")
public class DutyPositionMusican {
    @Id
    @Column(name = "dutyPosition_musican")
    private Integer dutyPositionMusican;

    @Column(name = "dutyPositionId")
    private Integer dutyPositionId;

    @Column(name = "musicanId")
    private Integer musicanId;


    public Integer getDutyPositionMusican() {
        return this.dutyPositionMusican;
    }

    public void setDutyPositionMusican(Integer dutyPositionMusican) {
        this.dutyPositionMusican = dutyPositionMusican;
    }

    public Integer getDutyPositionId() {
        return this.dutyPositionId;
    }

    public void setDutyPositionId(Integer dutyPositionId) {
        this.dutyPositionId = dutyPositionId;
    }

    public Integer getMusicanId() {
        return this.musicanId;
    }

    public void setMusicanId(Integer musicanId) {
        this.musicanId = musicanId;
    }
}
