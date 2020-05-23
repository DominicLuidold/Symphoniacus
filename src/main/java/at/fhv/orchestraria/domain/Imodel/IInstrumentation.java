package at.fhv.orchestraria.domain.Imodel;

import java.util.Collection;

/**
 * only getters for read only access declared
 * @author Team C
 */

public interface IInstrumentation {
     int getInstrumentationId();
     String getName();
     IMusicalPiece getMusicalPiece();

     /**
      * @return Returns unmodifiable collection of instrumentation positions by instrumentation ID.
      */
     Collection<IInstrumentationPosition> getIInstrumentationPositions();

     /**
      * @return Returns unmodifiable collection of section instrumentations by instrumentation ID.
      */
     Collection<ISectionInstrumentation> getISectionInstrumentations();

     /**
      * @return Returns unmodifiable collection of series of performances instrumentations by instrumentation ID.
      */
     Collection<ISeriesOfPerformancesInstrumentation> getISeriesOfPerformancesInstrumentation();

}
