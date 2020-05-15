package at.fhv.teamb.symphoniacus.persistence.model;

import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IWeeklyScheduleEntity;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.CascadeType;
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
public class WeeklyScheduleEntity implements IWeeklyScheduleEntity {
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

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "monthlyScheduleId")
    private MonthlyScheduleEntity monthlySchedule;

    @OneToMany(mappedBy = "weeklySchedule", orphanRemoval = true)
    private List<DutyEntity> duties = new LinkedList<>();

    @Override
    public Integer getWeeklyScheduleId() {
        return this.weeklyScheduleId;
    }

    @Override
    public void setWeeklyScheduleId(Integer weeklyScheduleId) {
        this.weeklyScheduleId = weeklyScheduleId;
    }

    @Override
    public LocalDate getStartDate() {
        return this.startDate;
    }

    @Override
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    @Override
    public LocalDate getEndDate() {
        return this.endDate;
    }

    @Override
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public Integer getYear() {
        return this.year;
    }

    @Override
    public void setYear(Integer year) {
        this.year = year;
    }

    @Override
    public LocalDate getPublishDate() {
        return this.publishDate;
    }

    @Override
    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    @Override
    public boolean getConfirmed() {
        return isConfirmed;
    }

    @Override
    public void setConfirmed(boolean confirmed) {
        this.isConfirmed = confirmed;
    }

    @Override
    public MonthlyScheduleEntity getMonthlySchedule() {
        return monthlySchedule;
    }

    @Override
    public void setMonthlySchedule(MonthlyScheduleEntity monthlySchedule) {
        this.monthlySchedule = monthlySchedule;
    }

    @Override
    public List<DutyEntity> getDuties() {
        return this.duties;
    }

    @Override
    public void addDuty(DutyEntity duty) {
        this.duties.add(duty);
        duty.setWeeklySchedule(this);
    }

    @Override
    public void removeDuty(DutyEntity duty) {
        this.duties.remove(duty);
        duty.setWeeklySchedule(null);
    }
}
