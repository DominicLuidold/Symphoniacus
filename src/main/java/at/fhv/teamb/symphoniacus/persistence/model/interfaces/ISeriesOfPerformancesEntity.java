package at.fhv.teamb.symphoniacus.persistence.model.interfaces;

import java.time.LocalDate;

public interface ISeriesOfPerformancesEntity {
    String getDescription();
    LocalDate getStartDate();
    LocalDate getEndDate();
    Integer getSeriesOfPerformancesId();

}
