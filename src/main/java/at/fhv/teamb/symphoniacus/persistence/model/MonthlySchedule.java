package at.fhv.teamb.symphoniacus.persistence.model;

import at.fhv.teamb.symphoniacus.persistence.converters.BooleanConverter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "monthlySchedule")
public class MonthlySchedule {
    @Id
    @Column(name = "monthlyScheduleId")
    private Integer monthlyScheduleId;

    @Column(name = "month")
    private Integer month;

    @Column(name = "year")
    private Integer year;

    @Column(name = "publishDate")
    private LocalDate publishDate;

    @Column(name = "endDateClassification")
    private LocalDate endDateClassification;

    @Column(name = "isPublished")
    @Convert(converter = BooleanConverter.class)
    private Boolean isPublished;

    @Column(name = "endWish")
    private LocalDate endWish;


    //One-To-Many Part for SECTIONMONTHLYSCHEDULE Table
    @OneToMany(mappedBy = "monthlySchedule", orphanRemoval = true)
    @JoinColumn(name = "monthlyScheduleId")
    private Set<SectionMonthlySchedule> sectionMonthlyScheduleSet = new HashSet<SectionMonthlySchedule>();

    public Set<SectionMonthlySchedule> getSectionMonthlyScheduleSet() {
        return sectionMonthlyScheduleSet;
    }
    public void setSectionMonthlyScheduleSet(Set<SectionMonthlySchedule> sectionMonthlyScheduleSet) {
        this.sectionMonthlyScheduleSet = sectionMonthlyScheduleSet;
    }

    public void addSectionMonthlySchedule(SectionMonthlySchedule sectionMonthlySchedule) {
        sectionMonthlyScheduleSet.add(sectionMonthlySchedule);
        sectionMonthlySchedule.setMonthlySchedule(this);
    }


    //One-To-Many Part for WEEKLYSCHEDULE Table
    @OneToMany(mappedBy = "monthlySchedule", orphanRemoval = true)
    @JoinColumn(name = "monthlyScheduleId")

    private Set<WeeklySchedule> weeklyScheduleSet = new HashSet<WeeklySchedule>();
    public Set<WeeklySchedule> getWeeklyScheduleSet() {
        return weeklyScheduleSet;
    }
    public void setWeeklyScheduleSet(Set<WeeklySchedule> weeklyScheduleSet) {
        this.weeklyScheduleSet = weeklyScheduleSet;
    }

    public void addWeeklySchedule(WeeklySchedule weeklySchedule) {
        weeklyScheduleSet.add(weeklySchedule);
        weeklySchedule.setMonthlySchedule(this);
    }


    //Getters and Setters
    public Integer getMonthlyScheduleId() {
        return this.monthlyScheduleId;
    }

    public void setMonthlyScheduleId(Integer monthlyScheduleId) {
        this.monthlyScheduleId = monthlyScheduleId;
    }

    public Integer getMonth() {
        return this.month;
    }

    public void setMonth(Integer month) {
        this.month = month;
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

    public LocalDate getEndDateClassification() {
        return this.endDateClassification;
    }

    public void setEndDateClassification(LocalDate endDateClassification) {
        this.endDateClassification = endDateClassification;
    }

    public Boolean getIsPublished() {
        return this.isPublished;
    }

    public void setIsPublished(Boolean isPublished) {
        this.isPublished = isPublished;
    }

    public LocalDate getEndWish() {
        return this.endWish;
    }

    public void setEndWish(LocalDate endWish) {
        this.endWish = endWish;
    }
}
