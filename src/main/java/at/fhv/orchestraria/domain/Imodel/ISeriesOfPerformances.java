package at.fhv.orchestraria.domain.Imodel;

import java.time.LocalDate;
import java.util.Collection;

/**
 * only getters for read only access declared
 * @author Team C
 */

public interface ISeriesOfPerformances {
     int getSeriesOfPerformancesId();
     String getDescription();
     LocalDate getStartDate();
     LocalDate getEndDate();
     boolean isTour();

     /**
      * @return Returns unmodifiable collection of duties by series of performances ID.
      */
     Collection<IDuty> getIDutiesBySeriesOfPerformancesId();

     /**
      * @return Returns unmodifiable collection of negative duty wishes by series of performances ID.
      */
     Collection<INegativeDutyWish> getINegativeDutyWishes();

     /**
      * @return Returns unmodifiable collection of positive wishes by series of performance ID.
      */
     Collection<IPositiveWish> getIPositiveWishes();

     /**
      * @return Returns unmodifiable collection of musical pieces belonging to a series of performance by series of performance ID
      */
     Collection<ISeriesOfPerformancesMusicalPiece> getISeriesOfPerformancesMusicalPieces();

     /**
      * @return Returns unmodifiable collection of instrumentations belonging to a series of performance by series of performance ID
      */
     Collection<ISeriesOfPerformancesInstrumentation> getISeriesOfPerformancesInstrumentations();

     /**
      * Iterates all duties of the series of performance and calculates the total amount of points of the specified series of performance
      * @return Returns the total amount of points of the series of performance
      */
     int getTotalAmountOfSeriesOfPerformancePoints();
}
