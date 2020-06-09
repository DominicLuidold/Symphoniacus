package at.fhv.teamb.symphoniacus.persistence.model.interfaces;

import java.time.LocalDate;
import java.util.List;

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

    List<IWishEntryEntity> getWishEntries();

    void setWishEntries(
        List<IWishEntryEntity> wishEntries);

    void addWishEntry(IWishEntryEntity wishEntry);

    void removeWishEntry(IWishEntryEntity wishEntry);
}
