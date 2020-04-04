package at.fhv.teamb.symphoniacus.persistence.model;

import javax.persistence.*;

@Entity
@Table(name = "weeklySchedule")
public class WeeklySchedule {
    @Id
    @Column(name = "weeklyscheduleId")
    private Integer weeklyscheduleId;

    @Column(name = "startDate")
    private java.sql.Date startDate;

    @Column(name = "endDate")
    private java.sql.Date endDate;

    @Column(name = "year")
    private Integer year;

    @Column(name = "publishDate")
    private java.sql.Date publishDate;

    @Column(name = "confirmed")
    private null confirmed;

    @Column(name = "monthlyScheduleId")
    private Integer monthlyScheduleId;


    public Integer getWeeklyscheduleId() {
        return this.weeklyscheduleId;
    }

    public void setWeeklyscheduleId(Integer weeklyscheduleId) {
        this.weeklyscheduleId = weeklyscheduleId;
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

    public Integer getYear() {
        return this.year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public java.sql.Date getPublishDate() {
        return this.publishDate;
    }

    public void setPublishDate(java.sql.Date publishDate) {
        this.publishDate = publishDate;
    }

    public null getConfirmed() {
        return this.confirmed;
    }

    public void setConfirmed(null confirmed) {
        this.confirmed = confirmed;
    }

    public Integer getMonthlyScheduleId() {
        return this.monthlyScheduleId;
    }

    public void setMonthlyScheduleId(Integer monthlyScheduleId) {
        this.monthlyScheduleId = monthlyScheduleId;
    }
}
