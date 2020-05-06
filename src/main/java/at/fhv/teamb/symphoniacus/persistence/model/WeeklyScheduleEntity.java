package at.fhv.teamb.symphoniacus.persistence.model;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
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
public class WeeklyScheduleEntity {
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
    private boolean isConfirmed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monthlyScheduleId")
    private MonthlyScheduleEntity monthlySchedule;

    @OneToMany(mappedBy = "weeklySchedule", orphanRemoval = true)
    private List<DutyEntity> duties = new LinkedList<>();

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

    public boolean getIsConfirmed() {
        return this.isConfirmed;
    }

    public void setIsConfirmed(boolean isConfirmed) {
        this.isConfirmed = isConfirmed;
    }

    public MonthlyScheduleEntity getMonthlySchedule() {
        return this.monthlySchedule;
    }

    public void setMonthlySchedule(
        MonthlyScheduleEntity monthlySchedule) {
        this.monthlySchedule = monthlySchedule;
    }

    public List<DutyEntity> getDuties() {
        return this.duties;
    }

    public void addDuty(DutyEntity duty) {
        this.duties.add(duty);
        duty.setWeeklySchedule(this);
    }

    public void removeDuty(DutyEntity duty) {
        this.duties.remove(duty);
        duty.setWeeklySchedule(null);
    }

    public boolean getConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }

    public void setDuties(List<DutyEntity> duties) {
        this.duties = duties;
    }
}
