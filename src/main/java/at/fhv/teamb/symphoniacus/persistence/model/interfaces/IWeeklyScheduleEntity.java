package at.fhv.teamb.symphoniacus.persistence.model.interfaces;

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

    IMonthlyScheduleEntity getMonthlySchedule();

    void setMonthlySchedule(IMonthlyScheduleEntity monthlySchedule);

    List<IDutyEntity> getDuties();

    void addDuty(IDutyEntity duty);

    void removeDuty(IDutyEntity duty);
}
