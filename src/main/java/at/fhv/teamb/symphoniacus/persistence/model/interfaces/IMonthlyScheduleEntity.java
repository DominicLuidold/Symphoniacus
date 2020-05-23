package at.fhv.teamb.symphoniacus.persistence.model.interfaces;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface IMonthlyScheduleEntity {
    Integer getMonthlyScheduleId();

    void setMonthlyScheduleId(Integer monthlyScheduleId);

    Integer getMonth();

    void setMonth(Integer month);

    Integer getYear();

    void setYear(Integer year);

    LocalDate getPublishDate();

    void setPublishDate(LocalDate publishDate);

    LocalDate getEndDateClassification();

    void setEndDateClassification(LocalDate endDateClassification);

    boolean getPublished();

    void setPublished(boolean published);

    LocalDate getEndWish();

    void setEndWish(LocalDate endWish);

    Set<ISectionMonthlyScheduleEntity> getSectionMonthlySchedule();

    void addSectionMonthlySchedule(ISectionMonthlyScheduleEntity sectionMonthlySchedule);

    void removeSectionMonthlySchedule(ISectionMonthlyScheduleEntity sectionMonthlySchedule);

    List<INegativeDateWishEntity> getNegativeDateWishes();

    void addNegativeDateWish(INegativeDateWishEntity negativeDateWishEntity);

    void removeNegativeDateWish(INegativeDateWishEntity negativeDateWishEntity);

    List<IWeeklyScheduleEntity> getWeeklySchedules();

    void addWeeklySchedule(IWeeklyScheduleEntity weeklySchedule);

    void removeWeeklySchedule(IWeeklyScheduleEntity weeklySchedule);
}
