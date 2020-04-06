package at.fhv.teamb.symphoniacus.persistence.model;

import at.fhv.teamb.symphoniacus.persistence.converter.BooleanConverter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "weeklySchedule")
public class WeeklySchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "weeklyScheduleId")
    private Integer weeklyScheduleId;

    @Column(name = "startDate")
    private LocalDate startDate;

    @Column(name = "endDate")
    private LocalDate endDate;

    @Column(name = "year")
    private Integer year;

    @Column(name = "publishDate")
    private LocalDate publishDate;

    @Column(name = "confirmed")
    @Convert(converter = BooleanConverter.class)
    private Boolean confirmed;

    @Column(name = "monthlyScheduleId", insertable = false, updatable = false)
    private Integer monthlyScheduleId;

    //Many-To-One Part for MONTHLYSCHEDULE Table
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monthlyScheduleId")
    private MonthlySchedule monthlySchedule;

    //One-To-Many Part for DUTY Table
    @OneToMany(mappedBy = "weeklySchedule", orphanRemoval = true)
    private Set<Duty> dutySet = new HashSet<Duty>();

    public MonthlySchedule getMonthlySchedule() {
        return this.monthlySchedule;
    }

    public void setMonthlySchedule(MonthlySchedule monthlySchedule) {
        this.monthlySchedule = monthlySchedule;
    }

    public Set<Duty> getDutySet() {
        return this.dutySet;
    }

    public void setDutySet(Set<Duty> dutySet) {
        this.dutySet = dutySet;
    }

    public void addDuty(Duty duty) {
        this.dutySet.add(duty);
        duty.setWeeklySchedule(this);
    }

    //Getters and Setters
    public Integer getWeeklyScheduleId() {
        return this.weeklyScheduleId;
    }

    public void setWeeklyScheduleId(Integer weeklyScheduleId) {
        this.weeklyScheduleId = weeklyScheduleId;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Integer getYear() {
        return this.year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public LocalDate getPublishDate() {
        return this.publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    public Boolean getConfirmed() {
        return this.confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public Integer getMonthlyScheduleId() {
        return this.monthlyScheduleId;
    }

    public void setMonthlyScheduleId(Integer monthlyScheduleId) {
        this.monthlyScheduleId = monthlyScheduleId;
    }
}
