package at.fhv.teamb.symphoniacus.persistence.model;

import javax.persistence.*;

@Entity
@Table(name = "dutyCategoryChangelog")
public class DutyCategoryChangelog {
    @Id
    @Column(name = "dutyCategoryChnagelogId")
    private Integer dutyCategoryChnagelogId;

    @Column(name = "dutyCategoryId")
    private Integer dutyCategoryId;

    @Column(name = "startDate")
    private java.sql.Date startDate;

    @Column(name = "points")
    private Integer points;


    public Integer getDutyCategoryChnagelogId() {
        return this.dutyCategoryChnagelogId;
    }

    public void setDutyCategoryChnagelogId(Integer dutyCategoryChnagelogId) {
        this.dutyCategoryChnagelogId = dutyCategoryChnagelogId;
    }

    public Integer getDutyCategoryId() {
        return this.dutyCategoryId;
    }

    public void setDutyCategoryId(Integer dutyCategoryId) {
        this.dutyCategoryId = dutyCategoryId;
    }

    public java.sql.Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(java.sql.Date startDate) {
        this.startDate = startDate;
    }

    public Integer getPoints() {
        return this.points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
}
