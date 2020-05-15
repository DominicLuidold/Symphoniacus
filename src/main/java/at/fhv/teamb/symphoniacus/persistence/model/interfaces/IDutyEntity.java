package at.fhv.teamb.symphoniacus.persistence.model.interfaces;

import java.time.LocalDateTime;
import java.util.Set;

public interface IDutyEntity {
    Integer getDutyId();

    void setDutyId(Integer dutyId);

    IWeeklyScheduleEntity getWeeklySchedule();

    void setWeeklySchedule(IWeeklyScheduleEntity weeklySchedule);

    IDutyCategoryEntity getDutyCategory();

    void setDutyCategory(IDutyCategoryEntity dutyCategory);

    String getDescription();

    void setDescription(String description);

    String getTimeOfDay();

    void setTimeOfDay(String timeOfDay);

    LocalDateTime getStart();

    void setStart(LocalDateTime start);

    LocalDateTime getEnd();

    void setEnd(LocalDateTime end);

    ISeriesOfPerformancesEntity getSeriesOfPerformances();

    void setSeriesOfPerformances(ISeriesOfPerformancesEntity seriesOfPerformances);

    Set<IDutyPositionEntity> getDutyPositions();

    void addDutyPosition(IDutyPositionEntity dutyPositionEntity);

    void removeDutyPosition(IDutyPositionEntity dutyPositionEntity);

    Set<ISectionMonthlyScheduleEntity> getSectionMonthlySchedules();

    void addSectionMonthlySchedule(ISectionMonthlyScheduleEntity sms);

    void removeSectionMonthlySchedule(ISectionMonthlyScheduleEntity sms);

    void setSectionMonthlySchedules(Set<ISectionMonthlyScheduleEntity> sectionMonthlySchedules);
}
