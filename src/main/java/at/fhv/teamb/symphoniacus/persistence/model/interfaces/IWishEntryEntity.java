package at.fhv.teamb.symphoniacus.persistence.model.interfaces;


import at.fhv.orchestraria.domain.Imodel.IMusicalPiece;
import java.util.List;

public interface IWishEntryEntity {
    Integer getWishEntryId();

    void setWishEntryId(Integer id);

    ISeriesOfPerformancesEntity getSeriesOfPerformances();

    void setSeriesOfPerformances(ISeriesOfPerformancesEntity series);

    IDutyEntity getDuty();

    void setDuty(IDutyEntity duty);

    IPositiveWishEntity getPositiveWish();

    void setPositiveWish(IPositiveWishEntity positiveWish);

    INegativeDutyWishEntity getNegativeDutyWish();

    void setNegativeDutyWish(INegativeDutyWishEntity negativeDutyWish);

    void addMusicalPieces(IMusicalPieceEntity musicalPiece);

    void removeMusicalPiece(IMusicalPieceEntity musicalPiece);

    List<IMusicalPieceEntity> getMusicalPieces();

    void setMusicalPieces(List<IMusicalPieceEntity> entityList);
}
