package at.fhv.teamb.symphoniacus.persistence.model.interfaces;

import java.time.LocalDate;

public interface IPositiveWishEntity {
    IMusicianEntity getMusician();

    void setMusician(IMusicianEntity musician);

    ISeriesOfPerformancesEntity getSeriesOfPerformances();

    void setSeriesOfPerformances(ISeriesOfPerformancesEntity seriesOfPerformances);

    Integer getPositiveWishId();

    void setPositiveWishId(Integer positiveWishId);

    String getDescription();

    void setDescription(String description);

    LocalDate getStartDate();

    LocalDate getEndDate();
}
