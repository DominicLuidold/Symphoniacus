package at.fhv.teamb.symphoniacus.persistence.model.interfaces;

import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.MonthlyScheduleEntity;
import java.time.LocalDate;
import java.util.List;

public interface IWeeklyScheduleEntity {
    Integer getWeeklyScheduleId();

    void setWeeklyScheduleId(Integer weeklyScheduleId);

    LocalDate getStartDate();

    void setStartDate(LocalDate startDate);

    LocalDate getEndDate();

    void setEndDate(LocalDate endDate);

    Integer getYear();

    void setYear(Integer year);

    LocalDate getPublishDate();

    void setPublishDate(LocalDate publishDate);

    boolean getConfirmed();

    void setConfirmed(boolean confirmed);

    MonthlyScheduleEntity getMonthlySchedule();

    void setMonthlySchedule(MonthlyScheduleEntity monthlySchedule);

    List<DutyEntity> getDuties();

    void addDuty(DutyEntity duty);

    void removeDuty(DutyEntity duty);
}
