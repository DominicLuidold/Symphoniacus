package at.fhv.teamb.symphoniacus.persistence.model.interfaces;

import at.fhv.teamb.symphoniacus.persistence.model.MonthlyScheduleEntity;
import at.fhv.teamb.symphoniacus.persistence.model.MusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.WishRequestable;
import java.time.LocalDate;
import java.util.List;

public interface INegativeDateWishEntity {
    Integer getNegativeDateId();

    void addMonthlySchedule(MonthlyScheduleEntity monthlyScheduleEntity);

    void removeMonthlySchedule(MonthlyScheduleEntity monthlyScheduleEntity);

    void setNegativeDateId(Integer negativeDateId);

    String getDescription();

    void setDescription(String description);

    LocalDate getStartDate();

    void setStartDate(LocalDate startDate);

    LocalDate getEndDate();

    void setEndDate(LocalDate endDate);

    List<MonthlyScheduleEntity> getMonthlySchedules();

    void setMonthlySchedules(
        List<MonthlyScheduleEntity> monthlySchedules);

    MusicianEntity getMusician();

    void setMusician(MusicianEntity musician);
}
