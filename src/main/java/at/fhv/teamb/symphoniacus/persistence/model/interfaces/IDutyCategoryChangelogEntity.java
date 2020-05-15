package at.fhv.teamb.symphoniacus.persistence.model.interfaces;


import at.fhv.teamb.symphoniacus.persistence.model.DutyCategoryEntity;
import java.time.LocalDate;

public interface IDutyCategoryChangelogEntity {
    void setDutyCategory(IDutyCategoryEntity dutyCategory);
    void setPoints(Integer points);
    void setStartDate(LocalDate startDate);
}
