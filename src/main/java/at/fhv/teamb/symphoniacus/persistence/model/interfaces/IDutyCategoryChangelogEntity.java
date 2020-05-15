package at.fhv.teamb.symphoniacus.persistence.model.interfaces;

import java.time.LocalDate;

public interface IDutyCategoryChangelogEntity {
    Integer getDutyCategoryChangelogId();

    void setDutyCategoryChangelogId(Integer dutyCategoryChangelogId);

    IDutyCategoryEntity getDutyCategory();

    void setDutyCategory(IDutyCategoryEntity dutyCategory);

    LocalDate getStartDate();

    void setStartDate(LocalDate startDate);

    Integer getPoints();

    void setPoints(Integer points);
}
