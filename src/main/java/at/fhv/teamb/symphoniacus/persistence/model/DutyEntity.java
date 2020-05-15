package at.fhv.teamb.symphoniacus.persistence.model;

import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyCategoryEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyPositionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISectionMonthlyScheduleEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISeriesOfPerformancesEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IWeeklyScheduleEntity;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "duty")
public class DutyEntity implements IDutyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dutyId")
    private Integer dutyId;

    @ManyToOne(
        fetch = FetchType.LAZY,
        cascade = {CascadeType.PERSIST, CascadeType.MERGE},
        targetEntity = WeeklyScheduleEntity.class
    )
    @JoinColumn(name = "weeklyScheduleId")
    private IWeeklyScheduleEntity weeklySchedule;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = DutyCategoryEntity.class)
    @JoinColumn(name = "dutyCategoryId")
    private IDutyCategoryEntity dutyCategory;

    @Column(name = "description")
    private String description;

    @Column(name = "timeOfDay")
    private String timeOfDay;

    @Column(name = "start")
    private LocalDateTime start;

    @Column(name = "end")
    private LocalDateTime end;

    @ManyToOne(
        fetch = FetchType.LAZY,
        cascade = {CascadeType.PERSIST, CascadeType.MERGE},
        targetEntity = SeriesOfPerformancesEntity.class
    )
    @JoinColumn(name = "seriesOfPerformancesId")
    private ISeriesOfPerformancesEntity seriesOfPerformances;

    @OneToMany(
        fetch = FetchType.LAZY,
        cascade = {CascadeType.PERSIST, CascadeType.MERGE},
        mappedBy = "duty",
        orphanRemoval = true,
        targetEntity = DutyPositionEntity.class
    )
    private Set<IDutyPositionEntity> dutyPositions = new HashSet<>();

    @ManyToMany(targetEntity = SectionMonthlyScheduleEntity.class)
    @JoinTable(
        name = "duty_sectionMonthlySchedule",
        joinColumns = {
            @JoinColumn(name = "dutyId")
        },
        inverseJoinColumns = {
            @JoinColumn(name = "sectionMonthlyScheduleId")
        }
    )
    private Set<ISectionMonthlyScheduleEntity> sectionMonthlySchedules = new HashSet<>();

    @Override
    public Integer getDutyId() {
        return this.dutyId;
    }

    @Override
    public void setDutyId(Integer dutyId) {
        this.dutyId = dutyId;
    }

    @Override
    public IWeeklyScheduleEntity getWeeklySchedule() {
        return this.weeklySchedule;
    }

    @Override
    public void setWeeklySchedule(IWeeklyScheduleEntity weeklySchedule) {
        this.weeklySchedule = weeklySchedule;
    }

    @Override
    public IDutyCategoryEntity getDutyCategory() {
        return this.dutyCategory;
    }

    @Override
    public void setDutyCategory(IDutyCategoryEntity dutyCategory) {
        this.dutyCategory = dutyCategory;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getTimeOfDay() {
        return this.timeOfDay;
    }

    @Override
    public void setTimeOfDay(String timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    @Override
    public LocalDateTime getStart() {
        return this.start;
    }

    @Override
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    @Override
    public LocalDateTime getEnd() {
        return this.end;
    }

    @Override
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    @Override
    public ISeriesOfPerformancesEntity getSeriesOfPerformances() {
        return seriesOfPerformances;
    }

    @Override
    public void setSeriesOfPerformances(ISeriesOfPerformancesEntity seriesOfPerformances) {
        this.seriesOfPerformances = seriesOfPerformances;
    }

    @Override
    public Set<IDutyPositionEntity> getDutyPositions() {
        return this.dutyPositions;
    }

    @Override
    public void addDutyPosition(IDutyPositionEntity dutyPositionEntity) {
        this.dutyPositions.add(dutyPositionEntity);
        dutyPositionEntity.setDuty(this);
    }

    @Override
    public void removeDutyPosition(IDutyPositionEntity dutyPositionEntity) {
        this.dutyPositions.remove(dutyPositionEntity);
        dutyPositionEntity.setDuty(null);
    }

    @Override
    public Set<ISectionMonthlyScheduleEntity> getSectionMonthlySchedules() {
        return this.sectionMonthlySchedules;
    }

    @Override
    public void addSectionMonthlySchedule(ISectionMonthlyScheduleEntity sms) {
        this.sectionMonthlySchedules.add(sms);
        sms.getDuties().add(this);
    }

    @Override
    public void removeSectionMonthlySchedule(ISectionMonthlyScheduleEntity sms) {
        this.sectionMonthlySchedules.remove(sms);
        sms.getDuties().remove(this);
    }

    @Override
    public void setSectionMonthlySchedules(
        Set<ISectionMonthlyScheduleEntity> sectionMonthlySchedules) {
        this.sectionMonthlySchedules = sectionMonthlySchedules;
    }
}
