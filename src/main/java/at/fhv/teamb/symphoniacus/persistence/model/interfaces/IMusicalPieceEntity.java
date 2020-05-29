package at.fhv.teamb.symphoniacus.persistence.model.interfaces;

import at.fhv.teamb.symphoniacus.persistence.model.WishEntryEntity;
import java.util.List;
import java.util.Set;

public interface IMusicalPieceEntity {
    Integer getMusicalPieceId();

    void setMusicalPieceId(Integer musicalPieceId);

    String getName();

    void setName(String name);

    String getComposer();

    void setComposer(String composer);

    String getCategory();

    void setCategory(String category);

    Set<IInstrumentationEntity> getInstrumentations();

    void setInstrumentations(Set<IInstrumentationEntity> instrumentations);

    void addInstrumentation(IInstrumentationEntity instrumentation);

    void removeInstrumentation(IInstrumentationEntity instrumentation);

    List<ISeriesOfPerformancesEntity> getSeriesOfPerformances();

    void setSeriesOfPerformances(List<ISeriesOfPerformancesEntity> seriesOfPerformances);

    void addSeriesOfPerformance(ISeriesOfPerformancesEntity seriesOfPerformancesEntity);

    void removeSeriesOfPerformance(ISeriesOfPerformancesEntity seriesOfPerformancesEntity);

    List<IWishEntryEntity> getWishEntries();

    void setWishEntries(List<IWishEntryEntity> wishEntries);

    void addWishEntry(IWishEntryEntity wishEntryEntity);

    void removeWishEntry(IWishEntryEntity wishEntryEntity);
}
