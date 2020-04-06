package at.fhv.teamb.symphoniacus.persistence.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "duty")
public class Duty {
    @Id
    @Column(name = "dutyId")
    private Integer dutyId;

    @Column(name = "weeklyScheduleId")
    private Integer weeklyScheduleId;

    @Column(name = "dutyCategoryId")
    private Integer dutyCategoryId;

    @Column(name = "sectionMonthlyScheduleId")
    private Integer sectionMonthlyScheduleId;

    @Column(name = "description")
    private String description;

    @Column(name = "timeOfDay")
    private String timeOfDay;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "endTime")
    private LocalTime endTime;

    @Column(name = "startTime")
    private LocalTime startTime;

    @Column(name = "seriesOfPerformancesId")
    private Integer seriesOfPerformancesId;


    public Integer getDutyId() {
        return this.dutyId;
    }

    public void setDutyId(Integer dutyId) {
        this.dutyId = dutyId;
    }

    public Integer getWeeklyScheduleId() {
        return this.weeklyScheduleId;
    }

    public void setWeeklyScheduleId(Integer weeklyScheduleId) {
        this.weeklyScheduleId = weeklyScheduleId;
    }

    public Integer getDutyCategoryId() {
        return this.dutyCategoryId;
    }

    public void setDutyCategoryId(Integer dutyCategoryId) {
        this.dutyCategoryId = dutyCategoryId;
    }

    public Integer getSectionMonthlyScheduleId() {
        return this.sectionMonthlyScheduleId;
    }

    public void setSectionMonthlyScheduleId(Integer sectionMonthlyScheduleId) {
        this.sectionMonthlyScheduleId = sectionMonthlyScheduleId;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTimeOfDay() {
        return this.timeOfDay;
    }

    public void setTimeOfDay(String timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getEndTime() {
        return this.endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public LocalTime getStartTime() {
        return this.startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public Integer getSeriesOfPerformancesId() {
        return this.seriesOfPerformancesId;
    }

    public void setSeriesOfPerformancesId(Integer seriesOfPerformancesId) {
        this.seriesOfPerformancesId = seriesOfPerformancesId;
    }
}
