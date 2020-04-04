package at.fhv.teamb.symphoniacus.persistence.model;

import javax.persistence.*;

@Entity
@Table(name = "negativeDateWish")
public class NegativeDateWish {
    @Id
    @Column(name = "negativeDateId")
    private Integer negativeDateId;

    @Column(name = "musicanId")
    private Integer musicanId;

    @Column(name = "descripition")
    private String descripition;

    @Column(name = "startDate")
    private java.sql.Date startDate;

    @Column(name = "endDate")
    private java.sql.Date endDate;


    public Integer getNegativeDateId() {
        return this.negativeDateId;
    }

    public void setNegativeDateId(Integer negativeDateId) {
        this.negativeDateId = negativeDateId;
    }

    public Integer getMusicanId() {
        return this.musicanId;
    }

    public void setMusicanId(Integer musicanId) {
        this.musicanId = musicanId;
    }

    public String getDescripition() {
        return this.descripition;
    }

    public void setDescripition(String descripition) {
        this.descripition = descripition;
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
}
