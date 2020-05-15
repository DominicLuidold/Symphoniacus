package at.fhv.teamb.symphoniacus.persistence.model.interfaces;

import at.fhv.teamb.symphoniacus.persistence.model.MusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.SeriesOfPerformancesEntity;
import at.fhv.teamb.symphoniacus.persistence.model.WishRequestable;
import java.time.LocalDate;

public interface IPositiveWishEntity {
    MusicianEntity getMusician();

    void setMusician(MusicianEntity musician);

    SeriesOfPerformancesEntity getSeriesOfPerformances();

    void setSeriesOfPerformances(
        SeriesOfPerformancesEntity seriesOfPerformances);

    Integer getPositiveWishId();

    void setPositiveWishId(Integer positiveWishId);

    String getDescription();

    void setDescription(String description);

    LocalDate getStartDate();

    LocalDate getEndDate();
}
