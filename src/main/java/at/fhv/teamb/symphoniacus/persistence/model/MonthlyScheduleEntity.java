package at.fhv.teamb.symphoniacus.persistence.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "monthlySchedule")
public class MonthlyScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private Boolean isPublished;

    @Column(name = "endWish")
    private LocalDate endWish;

    //One-To-Many Part for SECTIONMONTHLYSCHEDULE Table
    @OneToMany(mappedBy = "monthlySchedule", orphanRemoval = true)
    private Set<SectionMonthlyScheduleEntity> sectionMonthlyScheduleSet = new HashSet<>();

    //monthlySchedules -> attribute bei many to many
    @ManyToMany(mappedBy = "monthlySchedules")
    private List<NegativeDateWishEntity> negativeDateWishes = new LinkedList<>();

    @OneToMany(mappedBy = "monthlySchedule")
    private List<WeeklyScheduleEntity> weeklySchedules = new LinkedList<>();

    public void addNegativeDateWish(NegativeDateWishEntity negativeDateWishEntity) {
        this.negativeDateWishes.add(negativeDateWishEntity);
        negativeDateWishEntity.getMonthlySchedules().add(this);
    }

    public void removeNegativeDateWish(NegativeDateWishEntity negativeDateWishEntity) {
        this.negativeDateWishes.remove(negativeDateWishEntity);
        negativeDateWishEntity.getMonthlySchedules().remove(this);
    }

    public void addWeeklySchedules(WeeklyScheduleEntity weeklyScheduleEntity) {
        this.weeklySchedules.add(weeklyScheduleEntity);
        weeklyScheduleEntity.setMonthlySchedule(this);
    }

    public void removeWeeklySchedules(WeeklyScheduleEntity weeklyScheduleEntity) {
        this.weeklySchedules.remove(weeklyScheduleEntity);
        weeklyScheduleEntity.setMonthlySchedule(null);
    }

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

    public Boolean getPublished() {
        return isPublished;
    }

    public void setPublished(Boolean published) {
        isPublished = published;
    }

    public List<NegativeDateWishEntity> getNegativeDateWishes() {
        return negativeDateWishes;
    }

    public void setNegativeDateWishes(
        List<NegativeDateWishEntity> negativeDateWishes) {
        this.negativeDateWishes = negativeDateWishes;
    }

    public List<WeeklyScheduleEntity> getWeeklySchedules() {
        return weeklySchedules;
    }

    public void setWeeklySchedules(
        List<WeeklyScheduleEntity> weeklySchedules) {
        this.weeklySchedules = weeklySchedules;
    }

    public Set<SectionMonthlyScheduleEntity> getSectionMonthlyScheduleSet() {
        return this.sectionMonthlyScheduleSet;
    }

    public void setMonthlyScheduleSet(Set<SectionMonthlyScheduleEntity> scheduleSet) {
        this.sectionMonthlyScheduleSet = scheduleSet;
    }

    public void addMonthlySchedule(SectionMonthlyScheduleEntity schedule) {
        this.sectionMonthlyScheduleSet.add(schedule);
    }
}
