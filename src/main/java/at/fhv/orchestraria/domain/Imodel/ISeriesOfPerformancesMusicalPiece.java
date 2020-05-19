package at.fhv.orchestraria.domain.Imodel;

/**
 * only getters for read only access declared
 * @author Team C
 */

public interface ISeriesOfPerformancesMusicalPiece {
     int getSeriesOfPerformancesMusicalPieceId();
     IMusicalPiece getMusicalPiece();
     ISeriesOfPerformances getSeriesOfPerformances();
}
