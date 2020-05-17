package at.fhv.teamb.symphoniacus.persistence.model.interfaces;

import java.time.LocalDate;
import java.util.List;

public interface INegativeDateWishEntity {
    Integer getNegativeDateId();

    void addMonthlySchedule(IMonthlyScheduleEntity monthlyScheduleEntity);

    void removeMonthlySchedule(IMonthlyScheduleEntity monthlyScheduleEntity);

    void setNegativeDateId(Integer negativeDateId);

    String getDescription();

    void setDescription(String description);

    LocalDate getStartDate();

    void setStartDate(LocalDate startDate);

    LocalDate getEndDate();

    void setEndDate(LocalDate endDate);

    List<IMonthlyScheduleEntity> getMonthlySchedules();

    void setMonthlySchedules(List<IMonthlyScheduleEntity> monthlySchedules);

    IMusicianEntity getMusician();

    void setMusician(IMusicianEntity musician);
}
