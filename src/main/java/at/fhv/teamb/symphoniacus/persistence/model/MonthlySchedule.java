package at.fhv.teamb.symphoniacus.persistence.model;

import javax.persistence.*;

@Entity
@Table(name = "monthlySchedule")
public class MonthlySchedule {
    @Id
    @Column(name = "monthlyScheduleId")
    private Integer monthlyScheduleId;

    @Column(name = "month")
    private Integer month;

    @Column(name = "year")
    private Integer year;

    @Column(name = "publishDate")
    private java.sql.Date publishDate;

    @Column(name = "endDateClassification")
    private java.sql.Date endDateClassification;

    @Column(name = "isPublished")
    private null isPublished;

    @Column(name = "endWish")
    private java.sql.Date endWish;


    public Integer getMonthlyScheduleId() {
        return this.monthlyScheduleId;
    }

    public void setMonthlyScheduleId(Integer monthlyScheduleId) {
        this.monthlyScheduleId = monthlyScheduleId;
    }

    public Integer getMonth() {
        return this.month;
    }

    public void setMonth(Integer month) {
        this.month = month;
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

    public java.sql.Date getEndDateClassification() {
        return this.endDateClassification;
    }

    public void setEndDateClassification(java.sql.Date endDateClassification) {
        this.endDateClassification = endDateClassification;
    }

    public null getIsPublished() {
        return this.isPublished;
    }

    public void setIsPublished(null isPublished) {
        this.isPublished = isPublished;
    }

    public java.sql.Date getEndWish() {
        return this.endWish;
    }

    public void setEndWish(java.sql.Date endWish) {
        this.endWish = endWish;
    }
}
