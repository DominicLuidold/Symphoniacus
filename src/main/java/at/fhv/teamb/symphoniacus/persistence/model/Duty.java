package at.fhv.teamb.symphoniacus.persistence.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "duty")
public class Duty {
    @Id
    @Column(name = "dutyId")
    private Integer _dutyId;

    @Column(name = "weeklyScheduleId")
    private Integer _weeklyScheduleId;

    @Column(name = "dutyCategoryId")
    private Integer _dutyCategoryId;

    @Column(name = "sectionMonthlyScheduleId")
    private Integer _sectionMonthlyScheduleId;

    @Column(name = "description")
    private String _description;

    @Column(name = "timeOfDay")
    private String _timeOfDay;

    @Column(name = "date")
    private LocalDate _date;

    @Column(name = "endTime")
    private LocalTime _endTime;

    @Column(name = "startTime")
    private LocalTime _startTime;

    @Column(name = "seriesOfPerfomancesId")
    private Integer _seriesOfPerfomancesId;


    public Integer getDutyId() {
        return _dutyId;
    }

    public void setDutyId(Integer dutyId) {
        _dutyId = dutyId;
    }

    public Integer getWeeklyScheduleId() {
        return _weeklyScheduleId;
    }

    public void setWeeklyScheduleId(Integer weeklyScheduleId) {
        _weeklyScheduleId = weeklyScheduleId;
    }

    public Integer getDutyCategoryId() {
        return _dutyCategoryId;
    }

    public void setDutyCategoryId(Integer dutyCategoryId) {
        _dutyCategoryId = dutyCategoryId;
    }

    public Integer getSectionMonthlyScheduleId() {
        return _sectionMonthlyScheduleId;
    }

    public void setSectionMonthlyScheduleId(Integer sectionMonthlyScheduleId) {
        _sectionMonthlyScheduleId = sectionMonthlyScheduleId;
    }

    public String getDescription() {
        return _description;
    }

    public void setDescription(String description) {
        _description = description;
    }

    public String getTimeOfDay() {
        return _timeOfDay;
    }

    public void setTimeOfDay(String timeOfDay) {
        _timeOfDay = timeOfDay;
    }

    public LocalDate getDate() {
        return _date;
    }

    public void setDate(LocalDate date) {
        _date = date;
    }

    public LocalTime getEndTime() {
        return _endTime;
    }

    public void setEndTime(LocalTime endTime) {
        _endTime = endTime;
    }

    public LocalTime getStartTime() {
        return _startTime;
    }

    public void setStartTime(LocalTime startTime) {
        _startTime = startTime;
    }

    public Integer getSeriesOfPerfomancesId() {
        return _seriesOfPerfomancesId;
    }

    public void setSeriesOfPerfomancesId(Integer seriesOfPerfomancesId) {
        _seriesOfPerfomancesId = seriesOfPerfomancesId;
    }
}
