package at.fhv.teamb.symphoniacus.persistence.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "duty")
public class Duty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dutyId")
    private Integer dutyId;

    @Column(name = "weeklyScheduleId", insertable = false, updatable = false)
    private Integer weeklyScheduleId;

    @Column(name = "dutyCategoryId")
    private Integer dutyCategoryId;

    @Column(name = "sectionMonthlyScheduleId", insertable = false, updatable = false)
    private Integer sectionMonthlyScheduleId;

    @Column(name = "description")
    private String description;

    @Column(name = "timeOfDay")
    private String timeOfDay;

    @Column(name = "end")
    private LocalDateTime end;

    @Column(name = "start")
    private LocalDateTime start;

    @Column(name = "seriesOfPerformancesId")
    private Integer seriesOfPerformancesId;

    //One-To-Many Part for DUTYPOSITION Table
    @OneToMany(mappedBy = "duty", orphanRemoval = true)
    private Set<DutyPosition> dutyPositionSet = new HashSet<DutyPosition>();

    //Many-To-One Part for WEEKLYSCHEDULE Table
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "weeklyScheduleId")
    private WeeklySchedule weeklySchedule;

    //Many-To-One Part for SECTIONMONTHLYSCHEDULE Table
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sectionMonthlyScheduleId")
    private SectionMonthlySchedule sectionMonthlySchedule;

    public Set<DutyPosition> getDutyPositionSet() {
        return this.dutyPositionSet;
    }

    public void setDutyPositionSet(Set<DutyPosition> dutyPositionSet) {
        this.dutyPositionSet = dutyPositionSet;
    }

    public void addDutyPosition(DutyPosition dutyPosition) {
        this.dutyPositionSet.add(dutyPosition);
        dutyPosition.setDuty(this);
    }

    public WeeklySchedule getWeeklySchedule() {
        return this.weeklySchedule;
    }

    public void setWeeklySchedule(WeeklySchedule weeklySchedule) {
        this.weeklySchedule = weeklySchedule;
    }

    public SectionMonthlySchedule getSectionMonthlySchedule() {
        return this.sectionMonthlySchedule;
    }

    public void setSectionMonthlySchedule(SectionMonthlySchedule sectionMonthlySchedule) {
        this.sectionMonthlySchedule = sectionMonthlySchedule;
    }

    //Getters and Setters
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

    public LocalDateTime getEnd() {
        return this.end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public LocalDateTime getStart() {
        return this.start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public Integer getSeriesOfPerformancesId() {
        return this.seriesOfPerformancesId;
    }

    public void setSeriesOfPerformancesId(Integer seriesOfPerformancesId) {
        this.seriesOfPerformancesId = seriesOfPerformancesId;
    }
}
