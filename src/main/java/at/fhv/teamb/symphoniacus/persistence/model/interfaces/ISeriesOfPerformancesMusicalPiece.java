package at.fhv.teamb.symphoniacus.persistence.model.interfaces;

public interface ISeriesOfPerformancesMusicalPiece {
    IMusicalPieceEntity getMusicalPiece();

    void setMusicalPiece(IMusicalPieceEntity musicalPiece);

    Integer getSeriesOfPerformancesMusicalPieceId();

    void setSeriesOfPerformancesMusicalPieceId(Integer seriesOfPerformancesMusicalPieceId);

    Integer getMusicalPieceId();

    void setMusicalPieceId(Integer musicalPieceId);

    Integer getSeriesOfPerformancesId();

    void setSeriesOfPerformancesId(Integer seriesOfPerformancesId);
}
