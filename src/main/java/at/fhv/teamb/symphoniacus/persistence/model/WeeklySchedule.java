package at.fhv.teamb.symphoniacus.persistence.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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

    @Column(name = "isConfirmed")
    private Boolean isConfirmed;

    @Column(name = "monthlyScheduleId", insertable = false, updatable = false)
    private Integer monthlyScheduleId;

    //Many-To-One Part for MONTHLYSCHEDULE Table
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monthlyScheduleId")
    private MonthlySchedule monthlySchedule;

    //One-To-Many Part for DUTY Table
    @OneToMany(mappedBy = "weeklySchedule", orphanRemoval = true)
    private Set<DutyEntity> dutyEntitySet = new HashSet<>();

    public MonthlySchedule getMonthlySchedule() {
        return this.monthlySchedule;
    }

    public void setMonthlySchedule(MonthlySchedule monthlySchedule) {
        this.monthlySchedule = monthlySchedule;
    }

    public Set<DutyEntity> getDutyEntitySet() {
        return this.dutyEntitySet;
    }

    public void setDutyEntitySet(Set<DutyEntity> dutyEntitySet) {
        this.dutyEntitySet = dutyEntitySet;
    }

    public void addDuty(DutyEntity dutyEntity) {
        this.dutyEntitySet.add(dutyEntity);
        dutyEntity.setWeeklySchedule(this);
    }

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

    public Boolean getIsConfirmed() {
        return this.isConfirmed;
    }

    public void setIsConfirmed(Boolean isConfirmed) {
        this.isConfirmed = isConfirmed;
    }

    public Integer getMonthlyScheduleId() {
        return this.monthlyScheduleId;
    }

    public void setMonthlyScheduleId(Integer monthlyScheduleId) {
        this.monthlyScheduleId = monthlyScheduleId;
    }
}
