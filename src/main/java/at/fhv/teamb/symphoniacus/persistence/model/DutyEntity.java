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
    @JoinColumn(name = "dutyCategoryId")
    private DutyCategory dutyCategory;

    @Column(name = "description")
    private String description;

    @Column(name = "timeOfDay")
    private String timeOfDay;

    @Column(name = "end")
    private LocalDateTime end;

    @Column(name = "start")
    private LocalDateTime start;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seriesOfPerformancesId")
    private SeriesOfPerformances seriesOfPerformances;


    //One-To-Many Part for DUTYPOSITION Table
    @OneToMany(mappedBy = "duty", orphanRemoval = true)
    private List<DutyPositionEntity> dutyPositions = new LinkedList<>();

    //Many-To-One Part for WEEKLYSCHEDULE Table
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "weeklyScheduleId")
    private WeeklySchedule weeklySchedule;

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

    public WeeklySchedule getWeeklySchedule() {
        return this.weeklySchedule;
    }

    public void setWeeklySchedule(WeeklySchedule weeklySchedule) {
        this.weeklySchedule = weeklySchedule;
    }

    public Integer getDutyId() {
        return this.dutyId;
    }

    public void setDutyId(Integer dutyId) {
        this.dutyId = dutyId;
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

    public void addSectionMonthlySchedule(SectionMonthlySchedule sms) {
        this.sectionMonthlySchedules.add(sms);
        sms.getDuties().add(this);
    }

    public void removeSectionMonthlySchedule(SectionMonthlySchedule sms) {
        this.sectionMonthlySchedules.remove(sms);
        sms.getDuties().remove(this);
    }

    public Set<SectionMonthlySchedule> getSectionMonthlySchedules() {
        return this.sectionMonthlySchedules;
    }

    public void setSectionMonthlySchedules(
        Set<SectionMonthlySchedule> sectionMonthlySchedules) {
        this.sectionMonthlySchedules = sectionMonthlySchedules;
    }

    public DutyCategory getDutyCategory() {
        return dutyCategory;
    }

    public void setDutyCategory(DutyCategory dutyCategory) {
        this.dutyCategory = dutyCategory;
    }

    public SeriesOfPerformances getSeriesOfPerformances() {
        return seriesOfPerformances;
    }

    public void setSeriesOfPerformances(
        SeriesOfPerformances seriesOfPerformances) {
        this.seriesOfPerformances = seriesOfPerformances;
    }
}
