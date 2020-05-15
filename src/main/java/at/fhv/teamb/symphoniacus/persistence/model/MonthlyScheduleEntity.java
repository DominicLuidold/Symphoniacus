package at.fhv.teamb.symphoniacus.persistence.model;

import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMonthlyScheduleEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.INegativeDateWishEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISectionMonthlyScheduleEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IWeeklyScheduleEntity;
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
    private List<INegativeDateWishEntity> negativeDateWishes = new LinkedList<>();

    @OneToMany(mappedBy = "monthlySchedule")
    private List<IWeeklyScheduleEntity> weeklySchedules = new LinkedList<>();

    @Override
    public Integer getMonthlyScheduleId() {
        return this.monthlyScheduleId;
    }

    @Override
    public void setMonthlyScheduleId(Integer monthlyScheduleId) {
        this.monthlyScheduleId = monthlyScheduleId;
    }

    @Override
    public Integer getMonth() {
        return this.month;
    }

    @Override
    public void setMonth(Integer month) {
        this.month = month;
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
    public LocalDate getEndDateClassification() {
        return this.endDateClassification;
    }

    @Override
    public void setEndDateClassification(LocalDate endDateClassification) {
        this.endDateClassification = endDateClassification;
    }

    @Override
    public boolean getPublished() {
        return this.isPublished;
    }

    @Override
    public void setPublished(boolean published) {
        this.isPublished = published;
    }

    @Override
    public LocalDate getEndWish() {
        return this.endWish;
    }

    @Override
    public void setEndWish(LocalDate endWish) {
        this.endWish = endWish;
    }

    @Override
    public Set<ISectionMonthlyScheduleEntity> getSectionMonthlySchedule() {
        return this.sectionMonthlySchedules;
    }

    @Override
    public void addSectionMonthlySchedule(ISectionMonthlyScheduleEntity sectionMonthlySchedule) {
        this.sectionMonthlySchedules.add(sectionMonthlySchedule);
        sectionMonthlySchedule.setMonthlySchedule(this);
    }

    @Override
    public void removeSectionMonthlySchedule(ISectionMonthlyScheduleEntity sectionMonthlySchedule) {
        this.sectionMonthlySchedules.remove(sectionMonthlySchedule);
        sectionMonthlySchedule.setMonthlySchedule(null);
    }

    @Override
    public List<INegativeDateWishEntity> getNegativeDateWishes() {
        return this.negativeDateWishes;
    }

    @Override
    public void addNegativeDateWish(INegativeDateWishEntity negativeDateWishEntity) {
        this.negativeDateWishes.add(negativeDateWishEntity);
        negativeDateWishEntity.addMonthlySchedule(this);
    }

    @Override
    public void removeNegativeDateWish(INegativeDateWishEntity negativeDateWishEntity) {
        this.negativeDateWishes.add(negativeDateWishEntity);
        negativeDateWishEntity.removeMonthlySchedule(this);
    }

    @Override
    public List<IWeeklyScheduleEntity> getWeeklySchedules() {
        return this.weeklySchedules;
    }

    @Override
    public void addWeeklySchedule(IWeeklyScheduleEntity weeklySchedule) {
        this.weeklySchedules.add(weeklySchedule);
        weeklySchedule.setMonthlySchedule(this);
    }

    @Override
    public void removeWeeklySchedule(IWeeklyScheduleEntity weeklySchedule) {
        this.weeklySchedules.add(weeklySchedule);
        weeklySchedule.setMonthlySchedule(null);
    }
}
