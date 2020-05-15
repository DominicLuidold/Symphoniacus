package at.fhv.teamb.symphoniacus.persistence.model.interfaces;

import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface ISeriesOfPerformancesEntity {
    void addPositiveWish(IPositiveWishEntity positiveWishEntity);

    void removePositiveWish(IPositiveWishEntity positiveWishEntity);

    void addNegativeDutyWish(INegativeDutyWishEntity negativeDutyWishEntity);

    void removeNegativeDutyWish(INegativeDutyWishEntity negativeDutyWishEntity);

    boolean getTour();

    void setTour(boolean tour);

    Set<DutyEntity> getDutyEntities();

    void setDutyEntities(
        Set<DutyEntity> dutyEntities);

    List<IPositiveWishEntity> getPositiveWishes();

    void setPositiveWishes(
        List<IPositiveWishEntity> positiveWishes);

    Integer getSeriesOfPerformancesId();

    void setSeriesOfPerformancesId(Integer seriesOfPerformancesId);

    String getDescription();

    void setDescription(String description);

    LocalDate getStartDate();

    void setStartDate(LocalDate startDate);

    LocalDate getEndDate();

    void setEndDate(LocalDate endDate);

    boolean getIsTour();

    void setIsTour(boolean isTour);

    void addDuty(DutyEntity dutyEntity);

    void removeDuty(DutyEntity dutyEntity);

    List<INegativeDutyWishEntity> getNegativeDutyWishes();

    void setNegativeDutyWishes(
        List<INegativeDutyWishEntity> negativeDutyWishes);

    Set<IInstrumentationEntity> getInstrumentations();

    void setInstrumentations(
        Set<IInstrumentationEntity> instrumentations);

    void addInstrumentation(IInstrumentationEntity instrumentationEntity);

    void removeInstrumentation(IInstrumentationEntity instrumentationEntity);

    Set<IMusicalPieceEntity> getMusicalPieces();

    void setMusicalPieces(
        Set<IMusicalPieceEntity> musicalPieces);

    void addMusicalPiece(IMusicalPieceEntity musicalPiece);

    void removeMusicalPiece(IMusicalPieceEntity musicalPiece);
}
