package at.fhv.teamb.symphoniacus.persistence.model;

import javax.persistence.*;

@Entity
@Table(name = "vacation")
public class Vacation {
    @Id
    @Column(name = "vacationId")
    private Integer vacationId;

    @Column(name = "musicanId")
    private Integer musicanId;

    @Column(name = "startDate")
    private java.sql.Date startDate;

    @Column(name = "endDate")
    private java.sql.Date endDate;

    @Column(name = "isConfirmed")
    private null isConfirmed;


    public Integer getVacationId() {
        return this.vacationId;
    }

    public void setVacationId(Integer vacationId) {
        this.vacationId = vacationId;
    }

    public Integer getMusicanId() {
        return this.musicanId;
    }

    public void setMusicanId(Integer musicanId) {
        this.musicanId = musicanId;
    }

    public java.sql.Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(java.sql.Date startDate) {
        this.startDate = startDate;
    }

    public java.sql.Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(java.sql.Date endDate) {
        this.endDate = endDate;
    }

    public null getIsConfirmed() {
        return this.isConfirmed;
    }

    public void setIsConfirmed(null isConfirmed) {
        this.isConfirmed = isConfirmed;
    }
}
