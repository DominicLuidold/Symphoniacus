package at.fhv.orchestraria.domain.Imodel;

/**
 * only getters for read only access declared
 * @author Team C
 */

public interface IPositiveWish {
     int getPositiveWishId();
     String getDescription();
     ISeriesOfPerformances getSeriesOfPerformances();
     IMusician getMusician();
}
