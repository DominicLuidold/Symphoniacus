package at.fhv.teamb.symphoniacus.persistence.model.interfaces;

import java.time.LocalDate;
import java.util.List;

public interface INegativeDutyWishEntity {
    Integer getNegativeDutyId();

    void setNegativeDutyId(Integer negativeDutyId);

    String getDescription();

    void setDescription(String description);

    IMusicianEntity getMusician();

    void setMusician(IMusicianEntity musician);

    ISeriesOfPerformancesEntity getSeriesOfPerformances();

    void setSeriesOfPerformances(ISeriesOfPerformancesEntity seriesOfPerformances);

    LocalDate getStartDate();

    LocalDate getEndDate();

    List<IWishEntryEntity> getWishEntries();

    void addWishEntry(IWishEntryEntity wishEntry);

    void removeWishEntry(IWishEntryEntity wishEntry);
}
