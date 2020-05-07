package at.fhv.teamb.symphoniacus.persistence.model;

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
public class DutyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dutyId")
    private Integer dutyId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "weeklyScheduleId")
    private WeeklyScheduleEntity weeklySchedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dutyCategoryId")
    private DutyCategoryEntity dutyCategory;

    @Column(name = "description")
    private String description;

    @Column(name = "timeOfDay")
    private String timeOfDay;

    @Column(name = "start")
    private LocalDateTime start;

    @Column(name = "end")
    private LocalDateTime end;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seriesOfPerformancesId")
    private SeriesOfPerformancesEntity seriesOfPerformances;

    @OneToMany(
        cascade = {CascadeType.PERSIST, CascadeType.MERGE},
        mappedBy = "duty",
        orphanRemoval = true
    )
    private Set<DutyPositionEntity> dutyPositions = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "duty_sectionMonthlySchedule",
        joinColumns = {
            @JoinColumn(name = "dutyId")
        },
        inverseJoinColumns = {
            @JoinColumn(name = "sectionMonthlyScheduleId")
        }
    )
    private Set<SectionMonthlyScheduleEntity> sectionMonthlySchedules = new HashSet<>();

    public Integer getDutyId() {
        return this.dutyId;
    }

    public void setDutyId(Integer dutyId) {
        this.dutyId = dutyId;
    }

    public WeeklyScheduleEntity getWeeklySchedule() {
        return this.weeklySchedule;
    }

    public void setWeeklySchedule(WeeklyScheduleEntity weeklySchedule) {
        this.weeklySchedule = weeklySchedule;
    }

    public DutyCategoryEntity getDutyCategory() {
        return this.dutyCategory;
    }

    public void setDutyCategory(DutyCategoryEntity dutyCategory) {
        this.dutyCategory = dutyCategory;
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

    public LocalDateTime getStart() {
        return this.start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return this.end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public SeriesOfPerformancesEntity getSeriesOfPerformances() {
        return seriesOfPerformances;
    }

    public void setSeriesOfPerformances(
        SeriesOfPerformancesEntity seriesOfPerformances
    ) {
        this.seriesOfPerformances = seriesOfPerformances;
    }

    public Set<DutyPositionEntity> getDutyPositions() {
        return this.dutyPositions;
    }

    public void addDutyPosition(DutyPositionEntity dutyPositionEntity) {
        this.dutyPositions.add(dutyPositionEntity);
        dutyPositionEntity.setDuty(this);
    }

    public void removeDutyPosition(DutyPositionEntity dutyPositionEntity) {
        this.dutyPositions.remove(dutyPositionEntity);
        dutyPositionEntity.setDuty(null);
    }

    public Set<SectionMonthlyScheduleEntity> getSectionMonthlySchedules() {
        return this.sectionMonthlySchedules;
    }

    public void addSectionMonthlySchedule(SectionMonthlyScheduleEntity sms) {
        this.sectionMonthlySchedules.add(sms);
        sms.getDuties().add(this);
    }

    public void removeSectionMonthlySchedule(SectionMonthlyScheduleEntity sms) {
        this.sectionMonthlySchedules.remove(sms);
        sms.getDuties().remove(this);
    }

    public void setSectionMonthlySchedules(
        Set<SectionMonthlyScheduleEntity> sectionMonthlySchedules) {
        this.sectionMonthlySchedules = sectionMonthlySchedules;
    }
}
