package at.fhv.teamb.symphoniacus.persistence.model;

import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMonthlyScheduleEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISectionMonthlyScheduleEntity;
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
public class MonthlyScheduleEntity implements IMonthlyScheduleEntity {
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
    private boolean isPublished;

    @Column(name = "endWish")
    private LocalDate endWish;

    @OneToMany(mappedBy = "monthlySchedule", orphanRemoval = true, targetEntity = SectionMonthlyScheduleEntity.class)
    private Set<ISectionMonthlyScheduleEntity> sectionMonthlySchedules = new HashSet<>();

    @ManyToMany(mappedBy = "monthlySchedules")
    private List<NegativeDateWishEntity> negativeDateWishes = new LinkedList<>();

    @OneToMany(mappedBy = "monthlySchedule")
    private List<WeeklyScheduleEntity> weeklySchedules = new LinkedList<>();

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

    public boolean getPublished() {
        return this.isPublished;
    }

    public void setPublished(boolean published) {
        this.isPublished = published;
    }

    public LocalDate getEndWish() {
        return this.endWish;
    }

    public void setEndWish(LocalDate endWish) {
        this.endWish = endWish;
    }

    public Set<ISectionMonthlyScheduleEntity> getSectionMonthlySchedule() {
        return this.sectionMonthlySchedules;
    }

    public void addSectionMonthlySchedule(ISectionMonthlyScheduleEntity sectionMonthlySchedule) {
        this.sectionMonthlySchedules.add(sectionMonthlySchedule);
        sectionMonthlySchedule.setMonthlySchedule(this);
    }

    public void removeSectionMonthlySchedule(ISectionMonthlyScheduleEntity sectionMonthlySchedule) {
        this.sectionMonthlySchedules.remove(sectionMonthlySchedule);
        sectionMonthlySchedule.setMonthlySchedule(null);
    }

    public List<NegativeDateWishEntity> getNegativeDateWishes() {
        return this.negativeDateWishes;
    }

    public void addNegativeDateWish(NegativeDateWishEntity negativeDateWishEntity) {
        this.negativeDateWishes.add(negativeDateWishEntity);
        negativeDateWishEntity.addMonthlySchedule(this);
    }

    public void removeNegativeDateWish(NegativeDateWishEntity negativeDateWishEntity) {
        this.negativeDateWishes.add(negativeDateWishEntity);
        negativeDateWishEntity.removeMonthlySchedule(this);
    }

    public List<WeeklyScheduleEntity> getWeeklySchedules() {
        return this.weeklySchedules;
    }

    public void addWeeklySchedule(WeeklyScheduleEntity weeklySchedule) {
        this.weeklySchedules.add(weeklySchedule);
        weeklySchedule.setMonthlySchedule(this);
    }

    public void removeWeeklySchedule(WeeklyScheduleEntity weeklySchedule) {
        this.weeklySchedules.add(weeklySchedule);
        weeklySchedule.setMonthlySchedule(null);
    }
}
