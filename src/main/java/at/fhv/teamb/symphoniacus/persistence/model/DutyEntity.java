package at.fhv.teamb.symphoniacus.persistence.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
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

    @ManyToOne(fetch = FetchType.LAZY)
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

    @OneToMany(mappedBy = "duty", orphanRemoval = true)
    private List<DutyPositionEntity> dutyPositions = new LinkedList<>();

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
    private Set<SectionMonthlySchedule> sectionMonthlySchedules = new HashSet<>();

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

    public List<DutyPositionEntity> getDutyPositions() {
        return this.dutyPositions;
    }

    public void setDutyPositions(List<DutyPositionEntity> dutyPositions) {
        this.dutyPositions = dutyPositions;
    }

    public void addDutyPosition(DutyPositionEntity dutyPosition) {
        this.dutyPositions.add(dutyPosition);
        dutyPosition.setDuty(this);
    }

    public void addAllDutyPositions(List<DutyPositionEntity> dutyPositions) {
        this.dutyPositions.addAll(dutyPositions);
    }

    public DutyCategoryEntity getDutyCategory() {
        return dutyCategory;
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

    public Set<SectionMonthlySchedule> getSectionMonthlySchedules() {
        return this.sectionMonthlySchedules;
    }

    public void addSectionMonthlySchedule(SectionMonthlySchedule sms) {
        this.sectionMonthlySchedules.add(sms);
        sms.getDuties().add(this);
    }

    public void removeSectionMonthlySchedule(SectionMonthlySchedule sms) {
        this.sectionMonthlySchedules.remove(sms);
        sms.getDuties().remove(this);
    }

    public void setSectionMonthlySchedules(
        Set<SectionMonthlySchedule> sectionMonthlySchedules) {
        this.sectionMonthlySchedules = sectionMonthlySchedules;
    }
}
