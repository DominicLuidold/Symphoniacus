package at.fhv.orchestraria.domain.Imodel;

/**
 * only getters for read only access declared
 * @author Team C
 */

public interface INegativeDutyWish {
     int getNegativeDutyId();
     String getDescription();
     IMusician getMusician();
     ISeriesOfPerformances getSeriesOfPerformances();
}
