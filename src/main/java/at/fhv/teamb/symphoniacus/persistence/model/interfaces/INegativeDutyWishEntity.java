package at.fhv.teamb.symphoniacus.persistence.model.interfaces;

import at.fhv.teamb.symphoniacus.persistence.model.MusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.SeriesOfPerformancesEntity;
import at.fhv.teamb.symphoniacus.persistence.model.WishRequestable;
import java.time.LocalDate;

public interface INegativeDutyWishEntity {
    Integer getNegativeDutyId();

    void setNegativeDutyId(Integer negativeDutyId);

    String getDescription();

    void setDescription(String description);

    MusicianEntity getMusician();

    void setMusician(MusicianEntity musician);

    SeriesOfPerformancesEntity getSeriesOfPerformances();

    void setSeriesOfPerformances(
        SeriesOfPerformancesEntity seriesOfPerformances);

    LocalDate getStartDate();

    LocalDate getEndDate();
}
